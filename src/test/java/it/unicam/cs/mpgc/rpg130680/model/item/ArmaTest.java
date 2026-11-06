package it.unicam.cs.mpgc.rpg130680.model.item;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArmaTest {
    @Test
    void iGetRestituisconoIValori(){
        Arma a =new Arma("Pistola",15,75,12);
        assertEquals("Pistola",a.getNome());
        assertEquals(15, a.getDanno());
        assertEquals(75, a.getPrecisione());
        assertEquals(12, a.getMunizioni());
    }

    @Test
    void consumaColpo(){
        Arma a =new Arma("Pistola",15,75,12);
        a.consumaColpo();
        assertEquals(11,a.getMunizioni());
    }

    @Test
    void consumaColpoEccezione(){
        Arma a =new Arma("Pistola",15,75,0);
        assertThrows(IllegalStateException.class,()->a.consumaColpo());
    }

    @Test
    void aggiungiMunizioni(){
        Arma a =new Arma("Pistola",15,75,12);
        a.aggiungiMunizioni(1);
        assertEquals(13, a.getMunizioni());
    }

    @Test
    void eccezioneDanno(){
        assertThrows(IllegalArgumentException.class,()-> new Arma("Pistola",-1,75,12));
    }

    @Test
    void eccezionePrecisione(){
        assertThrows(IllegalArgumentException.class,()-> new Arma("Pistola",1,150,0));
    }

    @Test
    void eccezioneMunizioni(){
        assertThrows(IllegalArgumentException.class,()-> new Arma("Pistola",1,75,-1));
    }

    @Test
    void eccezioneNome(){
        assertThrows(IllegalArgumentException.class,()-> new Arma("",1,75,1));
    }

}
