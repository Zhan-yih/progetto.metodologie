package it.unicam.cs.mpgc.rpg130680.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NemicoTest {

    @Test
    void nemicoNuovoVitaPienaArma(){
        Arma arma= new Arma("Pistola",15,75,12);
        Nemico n = new Nemico("Avversario", 100,arma);
        assertEquals(100,n.getPuntiVita());
        assertTrue(n.isVivo());
        assertEquals(arma, n.getArma());

    }

    @Test
    void eccezioneSenzaArma(){
        assertThrows(IllegalArgumentException.class, () -> new Nemico("Avversario",100,null));
    }

    @Test
    void morteNemico(){
        Nemico n = new Nemico("Avversario", 100,new Arma("Pistola",15,75,12));
        n.subisciDanno(500);
        assertFalse(n.isVivo());
    }
}
