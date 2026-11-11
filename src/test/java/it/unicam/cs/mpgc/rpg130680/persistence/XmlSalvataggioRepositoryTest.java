package it.unicam.cs.mpgc.rpg130680.persistence;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.KitMedico;
import it.unicam.cs.mpgc.rpg130680.model.map.Mappa;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import it.unicam.cs.mpgc.rpg130680.service.Combattimento;
import it.unicam.cs.mpgc.rpg130680.service.Partita;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class XmlSalvataggioRepositoryTest {

    @TempDir
    Path cartella;

    @Test
    void salvaCreaIlFile() {
        Path file = cartella.resolve("salvataggio.xml");
        Soldato soldato = new Soldato("io");
        Arma pistola = new Arma("Pistola", 15, 75, 12);
        soldato.raccogliArma(pistola);
        soldato.equipaggiaArma(pistola);
        soldato.raccogliConsumabile(new KitMedico(30));
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        stanza.aggiungiArma(new Arma("Fucile", 24, 75, 20));
        stanza.aggiungiConsumabile(new KitMedico(30));
        stanza.aggiungiOstaggio(new Ostaggio("prova"));
        Nemico nemico = new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15));
        nemico.subisciDanno(30);
        nemico.stordisci();
        nemico.accieca(2);
        stanza.inserisciNemico(nemico);
        Partita partita = new Partita(soldato,
                new Mappa(List.of(stanza)),
                new Combattimento(() -> 0));
        new XmlSalvataggioRepository(file).salva(partita);
        assertTrue(Files.exists(file));
    }

    @Test
    void costruttoreRifiutaFileNullo() {
        assertThrows(IllegalArgumentException.class, () -> new XmlSalvataggioRepository(null));
    }

    @Test
    void salvaECaricaRicreanoLoStato() {
        Path file = cartella.resolve("salvataggio.xml");
        Soldato soldato = new Soldato("io");
        Arma pistola = new Arma("Pistola", 15, 75, 12);
        soldato.raccogliArma(pistola);
        soldato.equipaggiaArma(pistola);
        soldato.raccogliConsumabile(new KitMedico(30));
        soldato.subisciDanno(20);
        Stanza stanza0 = new Stanza("ingresso", "Una porta blindata");
        stanza0.aggiungiArma(new Arma("Fucile", 24, 75, 20));
        stanza0.aggiungiConsumabile(new KitMedico(30));
        stanza0.aggiungiOstaggio(new Ostaggio("prova"));
        Nemico nemico = new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15));
        nemico.subisciDanno(30);
        nemico.stordisci();
        nemico.accieca(2);
        stanza0.inserisciNemico(nemico);
        Stanza stanza1 = new Stanza("sala", "Una sala vuota");
        Partita partita = new Partita(soldato, new Mappa(List.of(stanza0, stanza1)),
                new Combattimento(() -> 0));
        partita.entraInStanza(1);
        partita.completaStanza();
        partita.entraInStanza(0);
        XmlSalvataggioRepository repository = new XmlSalvataggioRepository(file);
        repository.salva(partita);
        Partita caricata = repository.carica(new Combattimento(() -> 0));
        Soldato s = caricata.getSoldato();
        assertEquals("io", s.getNome());
        assertEquals(80, s.getPuntiVita());
        assertEquals(1, s.getArmi().size());
        assertEquals("Pistola", s.getArmaEquipaggiata().get().getNome());
        assertEquals(1, s.getConsumabili().size());
        assertInstanceOf(KitMedico.class, s.getConsumabili().get(0));
        assertEquals(Set.of(1), caricata.getStanzeEsplorate());
        assertEquals(0, caricata.getIndiceStanzaCorrente());
        Stanza stanza = caricata.getMappa().getStanza(0);
        assertEquals("ingresso", stanza.getNome());
        assertEquals(1, stanza.getArmi().size());
        assertEquals(1, stanza.getConsumabili().size());
        assertEquals(1, stanza.getOstaggi().size());
        assertFalse(stanza.getOstaggi().get(0).isLiberato());
        Nemico n = stanza.getNemico().get();
        assertEquals(70, n.getPuntiVita());
        assertTrue(n.isStordito());
        assertEquals(2, n.getTurniAccecamento());
        assertEquals("Fucile", n.getArma().getNome());
    }

    @Test
    void caricaDaFileInesistenteLanciaEccezione() {
        XmlSalvataggioRepository repository =
                new XmlSalvataggioRepository(cartella.resolve("non-esiste.xml"));
        assertThrows(SalvataggioException.class,
                () -> repository.carica(new Combattimento(() -> 0)));
    }

    @Test
    void caricaConCombattimentoNulloLanciaEccezione() {
        XmlSalvataggioRepository repository =
                new XmlSalvataggioRepository(cartella.resolve("salvataggio.xml"));
        assertThrows(IllegalArgumentException.class, () -> repository.carica(null));
    }
}
