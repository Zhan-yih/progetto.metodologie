package it.unicam.cs.mpgc.rpg130680.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MappaTest {

    @Test
    void correttoOrdine(){
        Stanza stanza1= new Stanza("ingresso", "stanza pubblica");
        Stanza stanza2= new Stanza("Armeria", "vuota");
        Mappa mappa= new Mappa(List.of(stanza1, stanza2));
        assertEquals(2,mappa.numeroStanze());
        assertEquals(stanza1,mappa.getStanza(0));
    }

    @Test
    void eccezioniConListeVuote(){
        assertThrows(IllegalArgumentException.class, ()-> new Mappa(List.of()));
        assertThrows(IllegalArgumentException.class, ()-> new Mappa(null));
    }

    @Test
    void indiceNonValido(){
        Stanza stanza= new Stanza("ingresso", "stanza pubblica");
        Mappa mappa= new Mappa(List.of(stanza));
        assertThrows(IllegalArgumentException.class, ()-> mappa.getStanza(-1));
        assertThrows(IllegalArgumentException.class, ()-> mappa.getStanza(1));
    }

    @Test
    void stanzeNemiciGiusti(){
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        Stanza stanza1= new Stanza("ingresso", "stanza pubblica");
        Stanza stanza2= new Stanza("Armeria", "con nemico");
        stanza2.inserisciNemico(nemico);
        Mappa mappa= new Mappa(List.of(stanza1, stanza2));
        assertEquals(1, mappa.stanzeNemici().size());
        assertEquals(stanza2,mappa.stanzeNemici().get(0));
    }
}
