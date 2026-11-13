package it.unicam.cs.mpgc.rpg130680.ui.grafica;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public final class SfondoStanza {

    public static final double LARGHEZZA = 600;
    public static final double ALTEZZA = 300;
    private static final double Y_PAVIMENTO = 220;

    private SfondoStanza() {
    }

    public static Pane per(String nomeStanza) {
        return switch (nomeStanza.toLowerCase()) {
            case "ingresso" -> ingresso();
            case "armeria" -> armeria();
            case "infermeria" -> infermeria();
            case "magazzino" -> magazzino();
            case "prigione" -> prigione();
            default -> base(Color.GRAY, Color.DARKGRAY);
        };
    }

    private static Pane base(Color parete, Color pavimento) {
        Pane pane = new Pane();
        pane.setPrefSize(LARGHEZZA, ALTEZZA);
        pane.setMinSize(LARGHEZZA, ALTEZZA);
        pane.setMaxSize(LARGHEZZA, ALTEZZA);
        pane.getChildren().addAll(
                Sprite.rettangolo(0, 0, LARGHEZZA, Y_PAVIMENTO, parete),
                Sprite.rettangolo(0, Y_PAVIMENTO, LARGHEZZA, ALTEZZA - Y_PAVIMENTO, pavimento));
        return pane;
    }

    private static Pane ingresso() {
        Pane pane = base(Color.web("#8a97a6"), Color.DIMGRAY);
        Rectangle porta = Sprite.rettangolo(240, 80, 120, 140, Color.DARKGRAY);
        porta.setStroke(Color.BLACK);
        pane.getChildren().addAll(porta, new Circle(340, 150, 5, Color.GOLD));
        return pane;
    }

    private static Pane armeria() {
        Pane pane = base(Color.web("#5a5f3a"), Color.web("#3b3b2e"));
        pane.getChildren().addAll(
                Sprite.rettangolo(100, 80, 400, 12, Color.SADDLEBROWN),
                Sprite.rettangolo(130, 92, 8, 70, Color.BLACK),
                Sprite.rettangolo(250, 92, 8, 90, Color.BLACK),
                Sprite.rettangolo(370, 92, 8, 60, Color.BLACK),
                Sprite.rettangolo(460, 92, 8, 80, Color.BLACK));
        return pane;
    }

    private static Pane infermeria() {
        Pane pane = base(Color.web("#e8f1f2"), Color.web("#b8d4da"));
        Rectangle lettino = Sprite.rettangolo(60, 150, 160, 20, Color.WHITE);
        lettino.setStroke(Color.GRAY);
        pane.getChildren().addAll(
                lettino,
                Sprite.rettangolo(70, 170, 8, 50, Color.GRAY),
                Sprite.rettangolo(200, 170, 8, 50, Color.GRAY),
                Sprite.rettangolo(490, 60, 20, 60, Color.RED),
                Sprite.rettangolo(470, 80, 60, 20, Color.RED));
        return pane;
    }

    private static Pane magazzino() {
        Pane pane = base(Color.web("#6b5b45"), Color.web("#4a3f33"));
        pane.getChildren().addAll(
                cassa(50, 150, 70, 70),
                cassa(120, 170, 50, 50),
                cassa(60, 80, 60, 70),
                cassa(480, 160, 80, 60),
                cassa(490, 100, 60, 60));
        return pane;
    }

    private static Rectangle cassa(double x, double y, double larghezza, double altezza) {
        Rectangle cassa = Sprite.rettangolo(x, y, larghezza, altezza, Color.PERU);
        cassa.setStroke(Color.SADDLEBROWN);
        return cassa;
    }

    private static Pane prigione() {
        Pane pane = base(Color.web("#3d3d3d"), Color.web("#2a2a2a"));
        pane.getChildren().add(Sprite.rettangolo(240, 50, 120, 90, Color.MIDNIGHTBLUE));
        for (int i = 0; i < 5; i++) {
            pane.getChildren().add(Sprite.rettangolo(250 + i * 25, 50, 6, 90, Color.GRAY));
        }
        return pane;
    }
}

