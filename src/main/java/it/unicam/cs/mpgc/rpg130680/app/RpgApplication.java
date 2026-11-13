package it.unicam.cs.mpgc.rpg130680.app;

import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.persistence.XmlSalvataggioRepository;
import it.unicam.cs.mpgc.rpg130680.service.Combattimento;
import it.unicam.cs.mpgc.rpg130680.service.MappaIniziale;
import it.unicam.cs.mpgc.rpg130680.service.Partita;
import it.unicam.cs.mpgc.rpg130680.ui.PartitaController;
import it.unicam.cs.mpgc.rpg130680.ui.SchermataGioco;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.nio.file.Path;
import java.util.Random;

public class RpgApplication extends Application {
    @Override
    public void start(Stage stage) {
        Random random = new Random();
        Combattimento combattimento = new Combattimento(() -> random.nextInt(100));
        Partita partita = new Partita(new Soldato("Soldato"), MappaIniziale.crea(), combattimento);
        PartitaController controller = new PartitaController(partita,
                new XmlSalvataggioRepository(Path.of("salvataggio.xml")), combattimento);
        stage.setTitle("RPG");
        stage.setScene(new Scene(new SchermataGioco(controller), 900, 600));
        stage.show();
    }
}
