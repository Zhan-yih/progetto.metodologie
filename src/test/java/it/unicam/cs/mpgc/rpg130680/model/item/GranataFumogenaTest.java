package it.unicam.cs.mpgc.rpg130680.model.item;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GranataFumogenaTest {

    @Test
    void dopoUsaNemicoAccecato(){
        Soldato soldato= new Soldato("io");
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola",15,75,12));
        GranataFumogena granata= new GranataFumogena();
        granata.usa(soldato,nemico);
        assertTrue(nemico.isAccecato());
    }

    @Test
    void eccezioneNemicoNullo(){
        Soldato soldato= new Soldato("io");
        GranataFumogena granata= new GranataFumogena();
        assertThrows(IllegalArgumentException.class,()-> granata.usa(soldato,null) );
    }

    @Test
    void restituisceGranataFumogena(){
        GranataFumogena granata= new GranataFumogena();
        assertEquals("Granata fumogena",granata.getNome());
    }

    @Test
    void granataFumogenaRichiedeNemicoUso(){
        assertTrue(new GranataFumogena().richiedeNemico());
    }
}
