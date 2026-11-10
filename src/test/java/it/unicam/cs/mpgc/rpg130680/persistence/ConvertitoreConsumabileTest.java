package it.unicam.cs.mpgc.rpg130680.persistence;

import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.item.GranataFlash;
import it.unicam.cs.mpgc.rpg130680.model.item.GranataFumogena;
import it.unicam.cs.mpgc.rpg130680.model.item.KitMedico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConvertitoreConsumabileTest {

    @Test
    void tipoDiRiconoscePerKitMedico() {
        assertEquals("KIT_MEDICO", ConvertitoreConsumabile.tipoDi(new KitMedico(30)));
    }

    @Test
    void tipoDiRiconoscePerGranataFlash() {
        assertEquals("GRANATA_FLASH", ConvertitoreConsumabile.tipoDi(new GranataFlash()));
    }

    @Test
    void tipoDiRiconoscePerGranataFumogena() {
        assertEquals("GRANATA_FUMOGENA", ConvertitoreConsumabile.tipoDi(new GranataFumogena()));
    }

    @Test
    void tipoDiConsumabileSconosciutoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> ConvertitoreConsumabile.tipoDi(null));
    }

    @Test
    void creaKitMedicoConQuantita() {
        Consumabile c = ConvertitoreConsumabile.crea("KIT_MEDICO", 30);
        assertInstanceOf(KitMedico.class, c);
        assertEquals(30, ((KitMedico) c).getQuantita());
    }

    @Test
    void creaGranataFlash() {
        assertInstanceOf(GranataFlash.class, ConvertitoreConsumabile.crea("GRANATA_FLASH", 0));
    }

    @Test
    void creaGranataFumogena() {
        assertInstanceOf(GranataFumogena.class, ConvertitoreConsumabile.crea("GRANATA_FUMOGENA", 0));
    }

    @Test
    void creaConTipoSconosciutoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> ConvertitoreConsumabile.crea("PIPPO", 0));
    }

    @Test
    void creaConTipoNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> ConvertitoreConsumabile.crea(null, 0));
    }
}
