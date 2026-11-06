package it.unicam.cs.mpgc.rpg130680.model.entity;

import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SoladatoTest {

    @Test
    void nuovoSoldatoVitaMaxNessunArma(){
        Soldato s = new Soldato("io");
        assertEquals(100,s.getPuntiVita());
        assertTrue(s.isVivo());
        assertTrue(s.getArmaEquipaggiata().isEmpty());
    }

    @Test
    void raccogliArmaAggiungeInventario(){
        Soldato s = new Soldato("io");
        Arma arma = new Arma("Pistola", 15,75,12);
        s.raccogliArma(arma);
        assertTrue(s.getArmi().contains(arma));
    }

    @Test
    void equipaggiaArmaAlSoldato(){
        Soldato s = new Soldato("io");
        Arma arma = new Arma("Pistola", 15,75,12);
        s.raccogliArma(arma);
        s.equipaggiaArma(arma);
        assertEquals(arma,s.getArmaEquipaggiata().get());
    }

    @Test
    void equipaggiaArmaNonRaccolta(){
        Soldato s = new Soldato("io");
        Arma arma = new Arma("Pistola", 15,75,12);
        assertThrows(IllegalArgumentException.class, () -> s.equipaggiaArma(arma));
    }
}
