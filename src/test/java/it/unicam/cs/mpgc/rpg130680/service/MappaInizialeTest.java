package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.map.Mappa;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MappaInizialeTest {

    @Test
    void laMappaHaCinqueStanze() {
        assertEquals(5, MappaIniziale.crea().numeroStanze());
    }

    @Test
    void laPrimaStanzaNonHaNemici() {
        assertTrue(MappaIniziale.crea().getStanza(0).getNemico().isEmpty());
    }

    @Test
    void laPrimaStanzaContieneUnArma() {
        assertFalse(MappaIniziale.crea().getStanza(0).getArmi().isEmpty());
    }

    @Test
    void esistonoStanzeConNemici() {
        assertFalse(MappaIniziale.crea().stanzeNemici().isEmpty());
    }

    @Test
    void esistonoOstaggiDaLiberare() {
        Mappa mappa = MappaIniziale.crea();
        assertTrue(mappa.getStanze().stream()
                .anyMatch(s -> !s.ostaggiDaLiberare().isEmpty()));
    }

    @Test
    void ogniChiamataCreaOggettiDiversi() {
        assertNotSame(MappaIniziale.crea().getStanza(0), MappaIniziale.crea().getStanza(0));
    }
}

