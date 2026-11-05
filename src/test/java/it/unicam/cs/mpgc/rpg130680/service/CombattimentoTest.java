package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.Arma;
import it.unicam.cs.mpgc.rpg130680.model.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.Soldato;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombattimentoTest {

    @Test
    void colpoASegno(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola", 15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola", 15,75,12));
        Combattimento c= new Combattimento(()->0);
        int danno= c.attaccaNemico(soldato,nemico);
        assertEquals(15,danno);
        assertEquals(85,nemico.getPuntiVita());
        assertEquals(11,arma.getMunizioni());
    }

    @Test
    void colpoMancato(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola", 15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola", 15,75,12));
        Combattimento c= new Combattimento(()->99);
        int danno= c.attaccaNemico(soldato,nemico);
        assertEquals(0,danno);
        assertEquals(100, nemico.getPuntiVita());
        assertEquals(11,arma.getMunizioni());
    }

    @Test
    void armaSenzaMunizioni(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola", 15,75,0);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola", 15,75,0));
        Combattimento c= new Combattimento(()->0);
        int danno= c.attaccaNemico(soldato,nemico);
        assertEquals(0,danno);
        assertEquals(100, nemico.getPuntiVita());
    }

    @Test
    void costruttoreNullo(){
        assertThrows(IllegalArgumentException.class, ()-> new Combattimento(null));
    }

    @Test
    void armaNonEquipaggiata(){
        Soldato soldato= new Soldato("io");
        Nemico nemico= new Nemico("avversario",100,new Arma("Pistola", 15,75,12));
        Combattimento c= new Combattimento(()->0);
        assertThrows(IllegalStateException.class, ()-> c.attaccaNemico(soldato,nemico));
    }

    @Test
    void colpoASegnoDiNemico(){
        Soldato soldato= new Soldato("io");
        Nemico nemico= new Nemico("avversario",100, new Arma("Fucile",10,60,15));
        Combattimento c= new Combattimento(()->0);
        int danno= c.attaccaSoldato(nemico,soldato);
        assertEquals(10,danno);
        assertEquals(90,soldato.getPuntiVita());
    }

    @Test
    void colpoMancatoDiNemico(){
        Soldato soldato= new Soldato("io");
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile", 10,60,15));
        Combattimento c= new Combattimento(()->99);
        int danno= c.attaccaSoldato(nemico,soldato);
        assertEquals(0,danno);
        assertEquals(100, soldato.getPuntiVita());
    }

    @Test
    void armaDiNemicoSenzaMunizioni(){
        Soldato soldato= new Soldato("io");
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile", 10,60,0));
        Combattimento c= new Combattimento(()->0);
        int danno= c.attaccaSoldato(nemico,soldato);
        assertEquals(100,soldato.getPuntiVita());
        assertEquals(0,danno);
    }

    @Test
    void turnoCompletoColpiASegno(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola", 15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile", 10,60,15));
        Combattimento c= new Combattimento(()->0);
        EsitoTurno esito= c.giocaTurno(soldato,nemico);
        assertEquals(15,esito.dannoInflitto());
        assertEquals(10,esito.dannoSubito());
        assertFalse(esito.nemicoSconfitto());
        assertFalse(esito.soldatoSconfitto());
    }

    @Test
    void turnoNemicoSconfittoNonRisponde(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola", 15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Nemico nemico= new Nemico("avversario",10,new Arma("Fucile", 10,60,15));
        Combattimento c= new Combattimento(()->0);
        EsitoTurno esito= c.giocaTurno(soldato,nemico);
        assertEquals(15,esito.dannoInflitto());
        assertEquals(0,esito.dannoSubito());
        assertEquals(100,soldato.getPuntiVita());
        assertTrue(esito.nemicoSconfitto());
        assertFalse(esito.soldatoSconfitto());
    }

    @Test
    void turnoSoldatoSconfitto(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola", 15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile", 200,60,15));
        Combattimento c= new Combattimento(()->0);
        EsitoTurno esito= c.giocaTurno(soldato,nemico);
        assertEquals(200,esito.dannoSubito());
        assertTrue(esito.soldatoSconfitto());
    }

    @Test
    void turnoColpiMancati(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola", 15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile", 10,60,15));
        Combattimento c= new Combattimento(()->99);
        EsitoTurno esito= c.giocaTurno(soldato,nemico);
        assertEquals(0,esito.dannoInflitto());
        assertEquals(0,esito.dannoSubito());
        assertFalse(esito.nemicoSconfitto());
        assertFalse(esito.soldatoSconfitto());
    }

}
