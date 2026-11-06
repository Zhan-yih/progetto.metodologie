package it.unicam.cs.mpgc.rpg130680.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OstaggioTest {

    @Test
    void ostaggioNuovoNonLiberato(){
        Ostaggio ostaggio= new Ostaggio("ostaggio");
        assertEquals(50,ostaggio.getPuntiVita());
        assertFalse(ostaggio.isLiberato());
        assertTrue(ostaggio.isVivo());
    }

    @Test
    void ostaggioLiberato(){
        Ostaggio ostaggio= new Ostaggio("ostaggio");
        ostaggio.libera();
        assertTrue(ostaggio.isLiberato());
    }

    @Test
    void eccezioneSecondaLiberazione(){
        Ostaggio ostaggio= new Ostaggio("ostaggio");
        ostaggio.libera();
        assertThrows(IllegalStateException.class, ()->ostaggio.libera());
    }

    @Test
    void senzaNome(){
        assertThrows(IllegalArgumentException.class, ()->new Ostaggio(null));
        assertThrows(IllegalArgumentException.class, ()->new Ostaggio(""));
    }
}
