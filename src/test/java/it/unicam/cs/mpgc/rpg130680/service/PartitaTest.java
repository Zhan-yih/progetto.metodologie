package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.model.map.Mappa;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Vector;

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
}
