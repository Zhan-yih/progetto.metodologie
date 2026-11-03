package it.unicam.cs.mpgc.rpg130680.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class StanzaTest {

    @Test
    void nuovaStanzaNonHaNemicoArmi(){
        Stanza stanza = new Stanza("prova","prova");
        assertTrue(stanza.prendiArmi().isEmpty());
        assertTrue(stanza.getNemico().isEmpty());
    }

    @Test
    void nomeVuoto (){
        assertThrows(IllegalArgumentException.class, () -> new Stanza(null,"prova"));
        assertThrows(IllegalArgumentException.class, () -> new Stanza("","prova"));
    }

    @Test
    void descrizioneVuoto (){
        assertThrows(IllegalArgumentException.class, () -> new Stanza("prova",null));
        assertThrows(IllegalArgumentException.class, () -> new Stanza("prova",""));
    }

    @Test
    void aggiungiSvuotaArma(){
        Stanza stanza = new Stanza("prova","prova");
        Arma arma = new Arma ("Pistola",15,75,12);
        stanza.aggiungiArma(arma);
        List<Arma> prese = stanza.prendiArmi();
        assertTrue(prese.contains(arma));
        assertTrue(stanza.prendiArmi().isEmpty());
    }

    @Test
    void eccezioneArmaVuota(){
        Stanza stanza = new Stanza("prova","prova");
        assertThrows(IllegalArgumentException.class, () -> stanza.aggiungiArma(null));
    }

    @Test
    void aggiungiSvuotaNemico(){
        Stanza stanza = new Stanza("prova","prova");
        Nemico nemico = new Nemico("avversario", 100,new Arma("Pistola",15,75,12));
        stanza.inserisciNemico(nemico);
        assertEquals(Optional.of(nemico),stanza.getNemico());
        stanza.rimuoviNemico();
        assertTrue(stanza.getNemico().isEmpty());

    }

    @Test
    void eccezioneNemicoVuota(){
        Stanza stanza = new Stanza("prova","prova");
        assertThrows(IllegalArgumentException.class, () -> stanza.inserisciNemico(null));
    }
}
