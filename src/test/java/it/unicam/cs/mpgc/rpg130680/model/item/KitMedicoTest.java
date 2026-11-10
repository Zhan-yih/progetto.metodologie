package it.unicam.cs.mpgc.rpg130680.model.item;

import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KitMedicoTest {

    @Test
    void kitCuraSoldato(){
        Soldato soldato= new Soldato("io");
        soldato.subisciDanno(50);
        KitMedico kit= new KitMedico(30);
        kit.usa(soldato,null);
        assertEquals(80,soldato.getPuntiVita());
    }

    @Test
    void curaLimitata(){
        Soldato soldato= new Soldato("io");
        soldato.subisciDanno(50);
        KitMedico kit= new KitMedico(60);
        kit.usa(soldato,null);
        assertEquals(100,soldato.getPuntiVita());
    }

    @Test
    void eccezioneQuantita(){
        assertThrows(IllegalArgumentException.class,()->new KitMedico(0));
        assertThrows(IllegalArgumentException.class,()->new KitMedico(-12));
    }

    @Test
    void restituisceKitMedico(){
        KitMedico kit= new KitMedico(30);
        assertEquals("Kit medico",kit.getNome());
    }

    @Test
    void soldatoNull(){
        KitMedico kit= new KitMedico(30);
        assertThrows(IllegalArgumentException.class,()->kit.usa(null,null));
    }

    @Test
    void noRichiedeNemico(){
        assertFalse(new KitMedico(30).richiedeNemico());
    }

    @Test
    void getQuantitaRestituisceLaQuantita() {
        assertEquals(30, new KitMedico(30).getQuantita());
    }
}
