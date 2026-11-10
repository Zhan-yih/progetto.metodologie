package it.unicam.cs.mpgc.rpg130680.model.map;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.item.KitMedico;
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

    @Test
    void stanzaNuovaNonHaConsumabili(){
        Stanza stanza = new Stanza("prova","prova");
        assertTrue(stanza.prendiConsumabili().isEmpty());
    }

    @Test
    void prendiConsumabiliRestituisceESvuotaLaStanza(){
        Stanza stanza = new Stanza("prova","prova");
        Consumabile consumabile = new KitMedico(30);
        stanza.aggiungiConsumabile(consumabile);
        List<Consumabile> prese= stanza.prendiConsumabili();
        assertTrue(prese.contains(consumabile));
        assertTrue(stanza.prendiConsumabili().isEmpty());
    }

    @Test
    void eccezioneAggiuntaConsumabileNullo(){
        Stanza stanza = new Stanza("prova","prova");
        assertThrows(IllegalArgumentException.class, () -> stanza.aggiungiConsumabile(null));
    }

    @Test
    void nuovaStanzaNonHaOstaggi(){
        Stanza stanza = new Stanza("prova","prova");
        assertTrue(stanza.getOstaggi().isEmpty());
    }

    @Test
    void aggiungiOstaggioContieneOstatggio(){
        Stanza stanza = new Stanza("prova","prova");
        Ostaggio ost= new Ostaggio("prova");
        stanza.aggiungiOstaggio(ost);
        assertTrue(stanza.getOstaggi().contains(ost));
    }

    @Test
    void eccezioneAggiuntaOstaggioNullo(){
        Stanza stanza = new Stanza("prova","prova");
        assertThrows(IllegalArgumentException.class, () -> stanza.aggiungiOstaggio(null));
    }

    @Test
    void ostaggiDaLiberareContieneSoloQuelliNonLiberati(){
        Stanza stanza = new Stanza("prova","prova");
        Ostaggio liberato= new Ostaggio("prova1");
        Ostaggio prigioniero= new Ostaggio("prova2");
        stanza.aggiungiOstaggio(liberato);
        stanza.aggiungiOstaggio(prigioniero);
        liberato.libera();
        List<Ostaggio> daLiberare= stanza.ostaggiDaLiberare();
        assertEquals(1, daLiberare.size());
        assertEquals(prigioniero, daLiberare.get(0));
    }

    @Test
    void getArmiNonSvuotaLaStanza() {
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        Arma arma = new Arma("Pistola", 15, 75, 12);
        stanza.aggiungiArma(arma);
        assertTrue(stanza.getArmi().contains(arma));
        assertTrue(stanza.getArmi().contains(arma));
    }

    @Test
    void getConsumabiliNonSvuotaLaStanza() {
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        Consumabile kit = new KitMedico(30);
        stanza.aggiungiConsumabile(kit);
        assertTrue(stanza.getConsumabili().contains(kit));
        assertTrue(stanza.getConsumabili().contains(kit));
    }

}
