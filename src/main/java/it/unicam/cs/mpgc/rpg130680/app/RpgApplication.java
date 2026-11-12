package it.unicam.cs.mpgc.rpg130680.app;

import it.unicam.cs.mpgc.rpg130680.ui.grafica.Sprite;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RpgApplication extends Application {
    @Override
    public void start(Stage stage) {
        HBox fila = new HBox(40,
                Sprite.pistola(), Sprite.fucileAssalto(), Sprite.cecchino(),
                Sprite.granataFlash(), Sprite.granataFumogena(), Sprite.kitMedico());
        fila.setPadding(new Insets(100, 40, 40, 80));
        stage.setTitle("RPG");
        stage.setScene(new Scene(fila, 700, 300));
        stage.show();
    }
}
