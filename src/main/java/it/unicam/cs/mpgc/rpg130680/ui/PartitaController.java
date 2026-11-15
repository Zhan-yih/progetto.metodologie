package it.unicam.cs.mpgc.rpg130680.ui;

import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import it.unicam.cs.mpgc.rpg130680.persistence.SalvataggioRepository;
import it.unicam.cs.mpgc.rpg130680.service.Combattimento;
import it.unicam.cs.mpgc.rpg130680.service.EsitoTurno;
import it.unicam.cs.mpgc.rpg130680.service.Partita;

public class PartitaController {
    private Partita partita;
    private final SalvataggioRepository repository;
    private final Combattimento combattimento;

    public PartitaController(Partita partita, SalvataggioRepository repository, Combattimento combattimento) {
        if(partita == null || repository == null || combattimento == null){
            throw new IllegalArgumentException("Il parametro passata non valido");
        }
        this.partita = partita;
        this.repository = repository;
        this.combattimento = combattimento;
    }

    public Partita getPartita() {
        return partita;
    }

    public String entra(int indice){
        partita.entraInStanza(indice);
        Stanza stanza= partita.getStanzaCorrente().orElseThrow();
        return "Entri in " + stanza.getNome() + ": " + stanza.getDescrizione();
    }

    public String combatti(){
        EsitoTurno esito = partita.combatti();
        StringBuilder messaggio = new StringBuilder("Infliggi " + esito.dannoInflitto()
                + " danni al nemico. Il nemico ti infligge " + esito.dannoSubito() + " danni.");
        if (esito.nemicoSconfitto()) {
            messaggio.append(" Nemico sconfitto!");
        }
        if (esito.soldatoSconfitto()) {
            messaggio.append(" Sei stato sconfitto.");
        }
        return messaggio.toString();
    }

    public String completa(){
        Stanza stanza= partita.getStanzaCorrente()
                .orElseThrow(()-> new IllegalStateException("nessuna stanza corrente") );
        int oggetti= stanza.getArmi().size() + stanza.getConsumabili().size();
        int ostaggi= stanza.ostaggiDaLiberare().size();
        partita.completaStanza();
        return "Stanza completata raccolti " + oggetti + " oggetti, liberati " + ostaggi + " ostaggi";
    }

    public String spara(){
        int danno= partita.spara();
        StringBuilder messaggio= new StringBuilder(danno > 0
                ? "Colpito! Il nemico perde " + danno + " PV."
                : "Colpo mancato.");
        boolean nemicoPresente= partita.getStanzaCorrente()
                .flatMap(Stanza::getNemico)
                .isPresent();
        if(!nemicoPresente){
            messaggio.append(" Nemico sconfitto!");
        }
        return messaggio.toString();
    }

    public String turnoNemico(){
        int danno= partita.turnoNemico();
        StringBuilder messaggio= new StringBuilder(danno > 0
                ? "Il nemico ti colpisce: -" + danno + " PV."
                : "Il nemico ti manca.");
        if(!partita.getSoldato().isVivo()){
            messaggio.append(" Sei stato sconfitto.");
        }
        return messaggio.toString();
    }

    public String usa(Consumabile consumabile){
        partita.usaConsumabile(consumabile);
        return "Usi " + consumabile.getNome();
    }

    public String equipaggia(Arma arma){
        partita.getSoldato().equipaggiaArma(arma);
        return "Impugni " + arma.getNome();
    }

    public String sparaAlVuoto(){
        partita.sparaAlVuoto();
        return "Colpo a vuoto";
    }

    public void salva(){
        repository.salva(partita);
    }

    public void carica(){
        partita= repository.carica(combattimento);
    }
}

