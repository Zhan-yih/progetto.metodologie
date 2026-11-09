package it.unicam.cs.mpgc.rpg130680.model.item;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GranataFlashTest {
    @Test
    void nemicoStordito(){
        Soldato soldato= new Soldato("io");
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile",25,60,15));
        GranataFlash granata= new GranataFlash();
        granata.usa(soldato,nemico);
        assertTrue(nemico.isStordito());
    }

    @Test
    void eccezioneNemicoNull(){
        Soldato soldato= new Soldato("io");
        GranataFlash granata= new GranataFlash();
        assertThrows(IllegalArgumentException.class,()->{granata.usa(soldato,null);});
    }

    @Test
    void restituisceGranataFlash(){
        GranataFlash granata= new GranataFlash();
        assertEquals("Granata flash", granata.getNome());
    }

    @Test
    void granataFlashRichiedeNemicoUso(){
        assertTrue(new GranataFlash().richiedeNemico());
    }
}
