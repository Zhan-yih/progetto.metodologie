package it.unicam.cs.mpgc.rpg130680.model.entity;

import it.unicam.cs.mpgc.rpg130680.model.item.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SoldatoTest {

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

    @Test
    void contieneConsumabile(){
        Soldato s = new Soldato("io");
        Consumabile con= new KitMedico(30);
        s.raccogliConsumabile(con);
        assertTrue(s.getConsumabili().contains(con));
    }

    @Test
    void usaConsumabileFunziona(){
        Soldato s = new Soldato("io");
        Consumabile con= new KitMedico(30);
        s.subisciDanno(50);
        s.raccogliConsumabile(con);
        s.usaConsumabile(con,null);
        assertEquals(80, s.getPuntiVita());
        assertFalse(s.getConsumabili().contains(con));
    }

    @Test
    void eccezioneConsumabileNonRaccolto(){
        Soldato s = new Soldato("io");
        Consumabile con= new KitMedico(30);
        assertThrows(IllegalArgumentException.class, () -> s.usaConsumabile(con,null));
    }

    @Test
    void granataFlashConNemicoNulloRimaneInventario(){
        Soldato s = new Soldato("io");
        Consumabile flash= new GranataFlash();
        s.raccogliConsumabile(flash);
        assertThrows(IllegalArgumentException.class, ()-> s.usaConsumabile(flash,null));
        assertTrue(s.getConsumabili().contains(flash));
    }

    @Test
    void dopoScartaNonContieneConsumabile(){
        Soldato s = new Soldato("io");
        Consumabile con= new GranataFlash();
        s.raccogliConsumabile(con);
        s.scartaConsumabile(con);
        assertTrue(s.getConsumabili().isEmpty());
    }

    @Test
    void eccezioneScartareConsumabileNonRaccolto(){
        Soldato s = new Soldato("io");
        Consumabile con= new GranataFlash();
        assertThrows(IllegalArgumentException.class, ()-> s.scartaConsumabile(con));
    }

    @Test
    void eccezioneScartareConsumabileNullo(){
        Soldato s = new Soldato("io");
        assertThrows(IllegalArgumentException.class, ()-> s.scartaConsumabile(null));
    }
}
