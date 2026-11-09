package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
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

}

