package it.unicam.cs.mpgc.rpg130680.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonaggioTest {
    private static class PersonaggioDiProva extends Personaggio {
        PersonaggioDiProva(String nome, int puntiVitaMax){
            super(nome, puntiVitaMax);
        }
    }

    @Test
    void unPersonaggioNuovaHaLaVitaAlMassimo(){
        Personaggio p = new PersonaggioDiProva("IO", 100);
        assertEquals(100, p.getPuntiVita());
    }

    @Test
    void personaggioSubisceDanno(){
        Personaggio p = new PersonaggioDiProva("IO", 100);
        p.subisciDanno(30);
        assertEquals(70, p.getPuntiVita());
    }

    @Test
    void mortePersonaggio(){
        Personaggio p = new PersonaggioDiProva("IO", 100);
        p.subisciDanno(150);
        assertEquals(0, p.getPuntiVita());
        assertFalse(p.isVivo());
    }

    @Test
    void curaVita(){
        Personaggio p = new PersonaggioDiProva("IO", 100);
        p.subisciDanno(60);
        p.cura(30);
        assertEquals(70, p.getPuntiVita());
    }

    @Test
    void curaNonSuperaMassimo(){
        Personaggio p = new PersonaggioDiProva("IO", 100);
        p.subisciDanno(60);
        p.cura(300);
        assertEquals(100, p.getPuntiVita());
    }

    @Test
    void lanciaEccezione(){
        Personaggio p = new PersonaggioDiProva("IO", 100);
        assertThrows(IllegalArgumentException.class, () -> p.subisciDanno(-5));
    }

    @Test
    void curaNegativa(){
        Personaggio p = new PersonaggioDiProva("IO", 100);
        assertThrows(IllegalArgumentException.class, () -> p.cura(-5));
    }

    @Test
    void nomeVuoto(){
        assertThrows(IllegalArgumentException.class, () -> new PersonaggioDiProva("", 100));
    }

    @Test
    void vitaMaxZero(){
        assertThrows(IllegalArgumentException.class, () -> new PersonaggioDiProva("IO", 0));
    }







}
