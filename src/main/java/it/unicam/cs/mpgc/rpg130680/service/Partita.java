package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.map.Mappa;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Partita {
    private final Soldato soldato;
    private final Mappa mappa;
    private final Combattimento combattimento;
    private final Set<Integer> stanzeEsplorate= new HashSet<>();
    private int stanzaCorrente= -1;

    public Partita(Soldato soldato, Mappa mappa, Combattimento combattimento){
        if(soldato == null || mappa == null || combattimento == null){
            throw new IllegalArgumentException("valore non valido");
        }
        this.soldato = soldato;
        this.mappa = mappa;
        this.combattimento = combattimento;
    }

    public Partita(Soldato soldato, Mappa mappa, Combattimento combattimento, Set<Integer> stanzeEsplorate, int stanzaCorrente){
        this(soldato, mappa, combattimento);
        if(stanzeEsplorate == null){
            throw new IllegalArgumentException("stanze esplorate nulle");
        }
        stanzeEsplorate.forEach(mappa::getStanza);
        if(stanzaCorrente != -1){
            mappa.getStanza(stanzaCorrente);
            if(stanzeEsplorate.contains(stanzaCorrente)){
                throw new IllegalArgumentException("stanza corrente già esplorata");
            }
        }
        this.stanzeEsplorate.addAll(stanzeEsplorate);
        this.stanzaCorrente= stanzaCorrente;

    }

    public StatoPartita getStato(){
        if(!soldato.isVivo()){
            return StatoPartita.SCONFITTA;
        }
        boolean tutteEsplorate= stanzeEsplorate.size() == mappa.numeroStanze();
        boolean nessunOstaggioRimasto= mappa.getStanze().stream()
                .allMatch(s-> s.ostaggiDaLiberare().isEmpty());
        if(tutteEsplorate && nessunOstaggioRimasto){
            return StatoPartita.VITTORIA;
        }
        return StatoPartita.IN_CORSO;
    }

    public void entraInStanza(int indice){
        if(getStato() != StatoPartita.IN_CORSO){
            throw new IllegalStateException("stato non è in corso");
        }
        //controlla che l'indice sia valido
        mappa.getStanza(indice);
        if(stanzeEsplorate.contains(indice)){
            throw new IllegalArgumentException("stanza già esplorata");
        }
        stanzaCorrente= indice;
    }

    public Optional<Stanza> getStanzaCorrente(){
        if(stanzaCorrente == -1){
            return Optional.empty();
        }
        return Optional.of(mappa.getStanza(stanzaCorrente));
    }

    public EsitoTurno combatti(){
        Stanza stanza= getStanzaCorrente()
                .orElseThrow(()-> new IllegalStateException("nessuna stanza corrente"));
        Nemico nemico= stanza.getNemico()
                .orElseThrow(()-> new IllegalStateException("non ci sono nemici"));
        EsitoTurno esito= combattimento.giocaTurno(soldato, nemico);
        if(esito.nemicoSconfitto()){
            stanza.rimuoviNemico();
        }
        return esito;
    }

    public void completaStanza(){
        Stanza stanza= getStanzaCorrente()
                .orElseThrow(()-> new IllegalStateException("nessuna stanza corrente"));
        if(stanza.getNemico().isPresent()){
            throw new IllegalStateException("nemico ancora presente");
        }
        stanza.prendiArmi().forEach(soldato::raccogliArma);
        stanza.prendiConsumabili().forEach(soldato::raccogliConsumabile);
        stanza.ostaggiDaLiberare().forEach(Ostaggio::libera);
        stanzeEsplorate.add(stanzaCorrente);
        stanzaCorrente= -1;
    }

    public void usaConsumabile(Consumabile c){
        if(c == null){
            throw new IllegalArgumentException("consumabile nullo");
        }
        Stanza stanza= getStanzaCorrente()
                .orElseThrow(()-> new IllegalStateException("nessuna stanza corrente"));
        Optional<Nemico> nemico= stanza.getNemico();
        if(c.richiedeNemico() && nemico.isEmpty()){
            soldato.scartaConsumabile(c);
            return;
        }
        soldato.usaConsumabile(c, nemico.orElse(null));
    }

    public Soldato getSoldato(){
        return soldato;
    }

    public Mappa getMappa(){
        return mappa;
    }

    public Set<Integer> getStanzeEsplorate(){
        return Set.copyOf(stanzeEsplorate);
    }

    public int getIndiceStanzaCorrente(){
        return stanzaCorrente;
    }
}
