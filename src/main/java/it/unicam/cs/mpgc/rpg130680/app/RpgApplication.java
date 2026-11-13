package it.unicam.cs.mpgc.rpg130680.app;

import it.unicam.cs.mpgc.rpg130680.ui.grafica.SfondoStanza;
import it.unicam.cs.mpgc.rpg130680.ui.grafica.Sprite;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RpgApplication extends Application {
    @Override
    public void start(Stage stage) {
        Pane scena = SfondoStanza.per("ingresso");
        Group soldato = Sprite.soldato();
        soldato.setTranslateX(100);
        soldato.setTranslateY(270);
        Group nemico = Sprite.nemico();
        nemico.setTranslateX(480);
        nemico.setTranslateY(270);
        scena.getChildren().addAll(soldato, nemico);

        stage.setTitle("RPG");
        stage.setScene(new Scene(scena));
        stage.show();
    }
}
