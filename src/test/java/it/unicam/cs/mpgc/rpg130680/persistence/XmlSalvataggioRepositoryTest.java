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
}
