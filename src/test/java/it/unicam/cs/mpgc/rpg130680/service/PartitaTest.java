package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.item.GranataFlash;
import it.unicam.cs.mpgc.rpg130680.model.item.KitMedico;
import it.unicam.cs.mpgc.rpg130680.model.map.Mappa;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static it.unicam.cs.mpgc.rpg130680.service.StatoPartita.*;
import static org.junit.jupiter.api.Assertions.*;

public class PartitaTest {

    @Test
    void partitaNuovaInCorso(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertEquals(IN_CORSO, partita.getStato());
    }

    @Test
    void soldatoMuoreSconfitta(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        soldato.subisciDanno(100);
        assertEquals(SCONFITTA, partita.getStato());
    }

    @Test
    void eccezioneParametriNulli(){
        assertThrows(IllegalArgumentException.class, ()-> new Partita(null, null, null));
    }

    @Test
    void soldatoNulloEccezione(){
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        assertThrows(IllegalArgumentException.class, ()-> new Partita(null, mappa, combattimento));
    }

    @Test
    void mappaNulloEccezione(){
        Soldato soldato= new Soldato("io");
        Combattimento combattimento= new Combattimento(()->0);
        assertThrows(IllegalArgumentException.class, ()-> new Partita(soldato, null, combattimento));
    }

    @Test
    void combattimentoNulloEccezione(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        assertThrows(IllegalArgumentException.class, ()-> new Partita(soldato, mappa, null));
    }

    @Test
    void partitaNuovaStanzaCorrenteVuoto(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertTrue(partita.getStanzaCorrente().isEmpty());
        partita.entraInStanza(0);
        assertEquals(Optional.of(stanza), partita.getStanzaCorrente());
    }

    @Test
    void eccezioneIndiciNonValido(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertThrows(IllegalArgumentException.class, ()-> partita.entraInStanza(-1));
        assertThrows(IllegalArgumentException.class, ()-> partita.entraInStanza(1));
    }

    @Test
    void eccezionePartitaFinita(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        soldato.subisciDanno(100);
        assertThrows(IllegalStateException.class, ()-> partita.entraInStanza(0));
    }

    @Test
    void nemicoNonSconfittoRestaNellaStanza(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(new Nemico("avversario",100,new Arma("Fucile",10,60,15)));
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        EsitoTurno esito= partita.combatti();
        assertFalse(esito.nemicoSconfitto());
        assertTrue(stanza.getNemico().isPresent());
    }

    @Test
    void nemicoSconfitto(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(new Nemico("avversario",10,new Arma("Fucile",10,60,15)));
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        EsitoTurno esito= partita.combatti();
        assertTrue(esito.nemicoSconfitto());
        assertTrue(stanza.getNemico().isEmpty());
    }

    @Test
    void eccezioneNonEntratoInStanza(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(new Nemico("avversario",100,new Arma("Fucile",10,60,15)));
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertThrows(IllegalStateException.class, ()-> partita.combatti());
    }

    @Test
    void eccezioneSenzaNemicoInStanza(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        assertThrows(IllegalStateException.class, ()-> partita.combatti());
    }

    @Test
    void completaStanzaRaccogliOggettiELasciaStanza(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        Consumabile kit= new KitMedico(30);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.aggiungiArma(arma);
        stanza.aggiungiConsumabile(kit);
        stanza.aggiungiOstaggio(new Ostaggio("prova"));
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.completaStanza();
        assertTrue(soldato.getArmi().contains(arma));
        assertTrue(soldato.getConsumabili().contains(kit));
        assertTrue(partita.getStanzaCorrente().isEmpty());
    }

    @Test
    void completaStanzaOstaggioLiberato(){
        Soldato soldato= new Soldato("io");
        Ostaggio ostaggio= new Ostaggio("prova");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.aggiungiOstaggio(ostaggio);
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.completaStanza();
        assertTrue(ostaggio.isLiberato());
    }

    @Test
    void eccezioneNemicoPresenteCompletaStanza(){
        Soldato soldato= new Soldato("io");
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile",10,60,15));
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(nemico);
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        assertThrows(IllegalStateException.class, ()-> partita.completaStanza());
    }

    @Test
    void stanzaCompletaVittoria(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.completaStanza();
        assertEquals(VITTORIA, partita.getStato());
    }

    @Test
    void eccezioneStanzaGiaEsplorata(){
        Soldato soldato= new Soldato("io");
        Stanza stanza1= new Stanza("ingresso", "Una porta blindata");
        Stanza stanza2= new Stanza("sala", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza1, stanza2));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.completaStanza();
        assertThrows(IllegalArgumentException.class, ()-> partita.entraInStanza(0));
    }

    @Test
    void kitMedicoSenzaIlNemiciCuraSoldato(){
        Soldato soldato= new Soldato("io");
        Consumabile kit= new KitMedico(30);
        soldato.subisciDanno(50);
        soldato.raccogliConsumabile(kit);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.usaConsumabile(kit);
        assertEquals(80,soldato.getPuntiVita());
        assertFalse(soldato.getConsumabili().contains(kit));
    }

    @Test
    void senzaNemicoGranataFlashScarta(){
        Soldato soldato= new Soldato("io");
        Consumabile granata= new GranataFlash();
        soldato.raccogliConsumabile(granata);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.usaConsumabile(granata);
        assertFalse(soldato.getConsumabili().contains(granata));
    }

    @Test
    void flashConNemico(){
        Soldato soldato= new Soldato("io");
        Consumabile granata= new GranataFlash();
        soldato.raccogliConsumabile(granata);
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile",10,60,15));
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(nemico);
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.usaConsumabile(granata);
        assertTrue(nemico.isStordito());
        assertFalse(soldato.getConsumabili().contains(granata));
    }

    @Test
    void senzaStanzaCorrenteEccezione(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertThrows(IllegalStateException.class, ()-> partita.usaConsumabile(new KitMedico(30)));
    }

    @Test
    void eccezioneConsumabileNullo(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        assertThrows(IllegalArgumentException.class, ()-> partita.usaConsumabile(null));
    }
}

