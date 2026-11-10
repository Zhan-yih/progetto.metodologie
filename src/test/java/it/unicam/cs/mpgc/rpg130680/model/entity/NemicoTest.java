package it.unicam.cs.mpgc.rpg130680.model.entity;

import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
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
        Nemico n = new Nemico("avversario", 100,new Arma("Pistola",15,75,12));
        n.subisciDanno(500);
        assertFalse(n.isVivo());
    }

    @Test
    void nemicoNuovoNonStordito(){
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        assertFalse(nemico.isStordito());
    }

    @Test
    void nemicoStordito(){
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        nemico.stordisci();
        assertTrue(nemico.isStordito());
    }

    @Test
    void nemicoRiprende(){
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        nemico.stordisci();
        nemico.riprendi();
        assertFalse(nemico.isStordito());
    }

    @Test
    void accecamentoDuraNumeroDiTurni(){
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        nemico.accieca(2);
        nemico.riduciAccecamento();
        assertTrue(nemico.isAccecato());
        nemico.riduciAccecamento();
        assertFalse(nemico.isAccecato());
    }

    @Test
    void nemiciNuovoNonAccecato(){
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        assertFalse(nemico.isAccecato());
    }

    @Test
    void nemicoAccecato(){
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        nemico.accieca(2);
        assertTrue(nemico.isAccecato());
    }

    @Test
    void eccezioneTurniNonValido(){
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        assertThrows(IllegalArgumentException.class, () -> nemico.accieca(0) );
        assertThrows(IllegalArgumentException.class, () -> nemico.accieca(-1));
    }

    @Test
    void accecamentoNonCambiaNemicoNormali(){
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        nemico.riduciAccecamento();
        assertFalse(nemico.isAccecato());
    }

    @Test
    void turniAccecamentoSiAggiornano() {
        Nemico nemico = new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15));
        assertEquals(0, nemico.getTurniAccecamento());
        nemico.accieca(2);
        assertEquals(2, nemico.getTurniAccecamento());
        nemico.riduciAccecamento();
        assertEquals(1, nemico.getTurniAccecamento());
    }
}
