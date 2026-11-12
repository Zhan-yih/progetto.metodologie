package it.unicam.cs.mpgc.rpg130680.ui.grafica;

import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.item.GranataFumogena;
import it.unicam.cs.mpgc.rpg130680.model.item.KitMedico;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public final class Sprite {
    private Sprite() {
    }

    public static Group soldato() {
        Rectangle gambaSx = new Rectangle(-12, -24, 10, 24);
        Rectangle gambaDx = new Rectangle(2, -24, 10, 24);
        gambaSx.setFill(Color.DARKOLIVEGREEN);
        gambaDx.setFill(Color.DARKOLIVEGREEN);
        Rectangle corpo = new Rectangle(-14, -56, 28, 32);
        corpo.setFill(Color.OLIVEDRAB);
        Rectangle braccio = new Rectangle(12, -52, 8, 22);
        braccio.setFill(Color.OLIVEDRAB);
        Circle testa = new Circle(0, -66, 10, Color.web("#f1c27d"));
        Rectangle elmetto = new Rectangle(-12, -80, 24, 10);
        elmetto.setFill(Color.DARKGREEN);
        return new Group(gambaSx, gambaDx, corpo, braccio, testa, elmetto);
    }

    public static Group nemico() {
        Rectangle gambaSx = new Rectangle(-12, -24, 10, 24);
        Rectangle gambaDx = new Rectangle(2, -24, 10, 24);
        gambaSx.setFill(Color.DIMGRAY);
        gambaDx.setFill(Color.DIMGRAY);
        Rectangle corpo = new Rectangle(-14, -56, 28, 32);
        corpo.setFill(Color.DARKRED);
        Rectangle striscia = new Rectangle(-14, -44, 28, 6);
        striscia.setFill(Color.BLACK);
        Rectangle braccio = new Rectangle(12, -52, 8, 22);
        braccio.setFill(Color.DARKRED);
        Circle testa = new Circle(0, -66, 10, Color.web("#e0ac69"));
        Rectangle elmetto = new Rectangle(-12, -80, 24, 10);
        elmetto.setFill(Color.BLACK);
        return new Group(gambaSx, gambaDx, corpo, striscia, braccio, testa, elmetto);
    }

    public static Group ostaggio() {
        Rectangle gambaSx = new Rectangle(-12, -24, 10, 24);
        Rectangle gambaDx = new Rectangle(2, -24, 10, 24);
        gambaSx.setFill(Color.SLATEGRAY);
        gambaDx.setFill(Color.SLATEGRAY);
        Rectangle corpo = new Rectangle(-14, -56, 28, 32);
        corpo.setFill(Color.STEELBLUE);
        Rectangle braccio = new Rectangle(12, -52, 8, 22);
        braccio.setFill(Color.STEELBLUE);
        Rectangle mani = new Rectangle(-4, -34, 14, 6);
        mani.setFill(Color.SADDLEBROWN);
        Circle testa = new Circle(0, -66, 10, Color.web("#f1c27d"));
        Rectangle capelli = new Rectangle(-10, -78, 20, 8);
        capelli.setFill(Color.SADDLEBROWN);
        return new Group(gambaSx, gambaDx, corpo, braccio, mani, testa, capelli);
    }

    static Rectangle rettangolo(double x, double y, double larghezza, double altezza, Color colore) {
        Rectangle r = new Rectangle(x, y, larghezza, altezza);
        r.setFill(colore);
        return r;
    }

    public static Group pistola() {
        return new Group(
                rettangolo(-16, -6, 28, 8, Color.DARKGRAY),
                rettangolo(-16, 2, 10, 16, Color.DIMGRAY));
    }

    public static Group fucileAssalto() {
        return new Group(
                rettangolo(-42, -6, 12, 12, Color.SADDLEBROWN),
                rettangolo(-30, -5, 60, 10, Color.DIMGRAY),
                rettangolo(30, -3, 16, 6, Color.BLACK),
                rettangolo(-10, -9, 14, 4, Color.BLACK),
                rettangolo(-6, 5, 8, 16, Color.BLACK));
    }

    public static Group cecchino() {
        Circle lente = new Circle(14, -10, 4, Color.LIGHTBLUE);
        return new Group(
                rettangolo(-56, -6, 16, 12, Color.SADDLEBROWN),
                rettangolo(-40, -4, 80, 8, Color.DARKSLATEGRAY),
                rettangolo(40, -2, 30, 4, Color.BLACK),
                rettangolo(-14, -14, 28, 8, Color.BLACK),
                lente);
    }

    public static Group granataFlash() {
        Circle corpo = new Circle(0, 0, 12, Color.GOLD);
        corpo.setStroke(Color.BLACK);
        return new Group(corpo, rettangolo(-5, -18, 10, 8, Color.DARKGRAY));
    }

    public static Group granataFumogena() {
        Circle corpo = new Circle(0, 0, 12, Color.DARKGRAY);
        corpo.setStroke(Color.BLACK);
        Circle fumo1 = new Circle(-6, -24, 6, Color.rgb(200, 200, 200, 0.7));
        Circle fumo2 = new Circle(6, -28, 7, Color.rgb(200, 200, 200, 0.5));
        return new Group(corpo, rettangolo(-5, -18, 10, 8, Color.GRAY), fumo1, fumo2);
    }

    public static Group kitMedico() {
        Rectangle scatola = rettangolo(-16, -12, 32, 24, Color.WHITE);
        scatola.setStroke(Color.DARKRED);
        return new Group(scatola,
                rettangolo(-3, -8, 6, 16, Color.RED),
                rettangolo(-8, -3, 16, 6, Color.RED));
    }

    public static Group perArma(Arma arma) {
        return switch (arma.getNome()) {
            case "Fucile d'assalto" -> fucileAssalto();
            case "Cecchino" -> cecchino();
            default -> pistola();
        };
    }

    public static Group perConsumabile(Consumabile consumabile) {
        if (consumabile instanceof KitMedico) {
            return kitMedico();
        }
        if (consumabile instanceof GranataFumogena) {
            return granataFumogena();
        }
        return granataFlash();
    }
}
