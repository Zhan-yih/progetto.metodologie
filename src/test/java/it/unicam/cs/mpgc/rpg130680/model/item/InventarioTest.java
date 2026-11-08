package it.unicam.cs.mpgc.rpg130680.model.item;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class InventarioTest {

    @Test
    void inventarioNuovoVuoto(){
        Inventario inv = new Inventario();
        assertTrue(inv.isVuoto());
    }

    @Test
    void aggiungiArmaNelInventario(){
        Inventario inv = new Inventario();
        Arma a = new Arma("Pistola", 15,75,12);
        inv.aggiungiArma(a);
        assertTrue(inv.contiene(a));
        assertFalse(inv.isVuoto());
    }

    @Test
    void rimuoviArmaNelInventario(){
        Inventario inv = new Inventario();
        Arma a = new Arma("Pistola", 15,75,12);
        inv.aggiungiArma(a);
        assertTrue(inv.rimuoviArma(a));
        assertFalse(inv.contiene(a));
    }

    @Test
    void rimuoviArmaAssente(){
        Inventario inv = new Inventario();
        Arma a = new Arma("Pistola", 15,75,12);
        assertFalse(inv.rimuoviArma(a));
    }

    @Test
    void eccezioneArmaNull(){
        Inventario inv = new Inventario();
        assertThrows(IllegalArgumentException.class, () -> inv.aggiungiArma(null));
    }

    @Test
    void armiUtilizzabiliInventarioConMunizioni(){
        Inventario inv = new Inventario();
        Arma a = new Arma("Pistola", 15,75,12);
        Arma a2 = new Arma("Pistola scarica", 15,75,0);
        inv.aggiungiArma(a);
        inv.aggiungiArma(a2);
        List<Arma> armi = inv.armiUtilizzabili();
        assertEquals(1, armi.size());
        assertEquals(a, armi.get(0));
    }

    @Test
    void inventarioNuovoNonHaConsumabile(){
        Inventario inv = new Inventario();
        assertTrue(inv.getConsumabili().isEmpty());
    }

    @Test
    void contieneConsumabile(){
        Inventario inv = new Inventario();
        Consumabile con= new KitMedico(30);
        inv.aggiungiConsumabile(con);
        assertTrue(inv.getConsumabili().contains(con));
    }

    @Test
    void rimuoviConsumabile(){
        Inventario inv = new Inventario();
        Consumabile con= new KitMedico(30);
        inv.aggiungiConsumabile(con);
        assertFalse(inv.isVuoto());
        assertTrue(inv.rimuoviConsumabile(con));
        assertTrue(inv.isVuoto());
    }

    @Test
    void eccezioneAggiungiConsumabilenullo(){
        Inventario inv = new Inventario();
        assertThrows(IllegalArgumentException.class, () -> inv.aggiungiConsumabile(null));
    }

    @Test
    void inventarioNonVuotoUnConsumabile(){
        Inventario inv = new Inventario();
        Consumabile con = new KitMedico(30);
        inv.aggiungiConsumabile(con);
        assertFalse(inv.isVuoto());
    }

    @Test
    void rimuoviConsumabileAssente(){
        Inventario inv = new Inventario();
        assertFalse(inv.rimuoviConsumabile(new KitMedico(30)));
    }
}
