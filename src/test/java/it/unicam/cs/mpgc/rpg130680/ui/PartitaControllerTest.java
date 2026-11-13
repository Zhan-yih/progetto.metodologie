package it.unicam.cs.mpgc.rpg130680.ui;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.KitMedico;
import it.unicam.cs.mpgc.rpg130680.model.map.Mappa;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import it.unicam.cs.mpgc.rpg130680.persistence.XmlSalvataggioRepository;
import it.unicam.cs.mpgc.rpg130680.service.Combattimento;
import it.unicam.cs.mpgc.rpg130680.service.Partita;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PartitaControllerTest {
    @TempDir
    Path cartella;

    private PartitaController creaController(Nemico nemico) {
        Soldato soldato = new Soldato("io");
        Arma pistola = new Arma("Pistola", 15, 75, 12);
        soldato.raccogliArma(pistola);
        soldato.equipaggiaArma(pistola);
        Stanza stanza = new Stanza("armeria", "Una stanza con armi");
        stanza.inserisciNemico(nemico);
        Combattimento combattimento = new Combattimento(() -> 0);
        Partita partita = new Partita(soldato, new Mappa(List.of(stanza)), combattimento);
        return new PartitaController(partita,
                new XmlSalvataggioRepository(cartella.resolve("salvataggio.xml")),
                combattimento);
    }

    @Test
    void entraRestituisceNomeEDescrizioneDellaStanza() {
        PartitaController controller =
                creaController(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        String messaggio = controller.entra(0);
        assertTrue(messaggio.contains("armeria"));
        assertTrue(messaggio.contains("Una stanza con armi"));
    }

    @Test
    void combattiRiportaIDanni() {
        PartitaController controller =
                creaController(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        controller.entra(0);
        String messaggio = controller.combatti();
        assertTrue(messaggio.contains("Infliggi 15"));
        assertTrue(messaggio.contains("ti infligge 10"));
    }

    @Test
    void combattiSegnalaNemicoSconfitto() {
        PartitaController controller =
                creaController(new Nemico("avversario", 10, new Arma("Fucile", 10, 60, 15)));
        controller.entra(0);
        assertTrue(controller.combatti().contains("Nemico sconfitto!"));
    }

    @Test
    void costruttoreRifiutaValoriNulli() {
        assertThrows(IllegalArgumentException.class,
                () -> new PartitaController(null, null, null));
    }

    @Test
    void completaRestituisceIlRiepilogo() {
        PartitaController controller =
                creaController(new Nemico("avversario", 10, new Arma("Fucile", 10, 60, 15)));
        controller.entra(0);
        controller.combatti();
        assertTrue(controller.completa().contains("completata"));
    }

    @Test
    void equipaggiaRestituisceIlNomeDellArma() {
        PartitaController controller =
                creaController(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        Arma arma = controller.getPartita().getSoldato().getArmi().get(0);
        assertTrue(controller.equipaggia(arma).contains("Pistola"));
    }

    @Test
    void usaApplicaIlConsumabile() {
        PartitaController controller =
                creaController(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        Soldato soldato = controller.getPartita().getSoldato();
        KitMedico kit = new KitMedico(30);
        soldato.subisciDanno(50);
        soldato.raccogliConsumabile(kit);
        controller.entra(0);
        assertTrue(controller.usa(kit).contains("Kit medico"));
        assertEquals(80, soldato.getPuntiVita());
    }

    @Test
    void salvaECaricaRipristinanoLoStato() {
        PartitaController controller =
                creaController(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        controller.salva();
        controller.entra(0);
        assertEquals(0, controller.getPartita().getIndiceStanzaCorrente());
        controller.carica();
        assertEquals(-1, controller.getPartita().getIndiceStanzaCorrente());
    }

}
