package it.unicam.cs.mpgc.rpg130680.persistence;

import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
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
    void salvaCreaIlFile()  {
        Path file = cartella.resolve("salvataggio.xml");
        Partita partita = new Partita(new Soldato("io"),
                new Mappa(List.of(new Stanza("ingresso", "Una porta blindata"))),
                new Combattimento(() -> 0));
        new XmlSalvataggioRepository(file).salva(partita);
        assertTrue(Files.exists(file));
    }

    @Test
    void costruttoreRifiutaFileNullo() {
        assertThrows(IllegalArgumentException.class, () -> new XmlSalvataggioRepository(null));
    }
}
