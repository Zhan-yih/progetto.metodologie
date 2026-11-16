package it.unicam.cs.mpgc.rpg130680.ui.grafica;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BarraVita extends Group {
    public static final double LARGHEZZA = 70;
    private static final double ALTEZZA = 10;

    public BarraVita(int punti, int massimo) {
        double frazione = massimo <= 0 ? 0 : Math.max(0, Math.min(1.0, (double) punti / massimo));
        Rectangle sfondo = new Rectangle(LARGHEZZA, ALTEZZA, Color.DARKSLATEGRAY);
        sfondo.setStroke(Color.BLACK);
        Rectangle riempimento = new Rectangle(LARGHEZZA * frazione, ALTEZZA, colorePer(frazione));
        Text testo = new Text(punti + " / " + massimo);
        testo.setFont(Font.font("System", FontWeight.BOLD, 11));
        testo.setFill(Color.WHITE);
        testo.setStroke(Color.BLACK);
        testo.setStrokeWidth(0.5);
        testo.setX((LARGHEZZA - testo.getLayoutBounds().getWidth()) / 2);
        testo.setY(-3);
        getChildren().addAll(sfondo, riempimento, testo);
        setMouseTransparent(true);
    }

    private Color colorePer(double frazione) {
        if (frazione > 0.5) {
            return Color.LIMEGREEN;
        }
        return frazione > 0.25 ? Color.ORANGE : Color.RED;
    }
}
