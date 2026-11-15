package it.unicam.cs.mpgc.rpg130680.ui;

import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import it.unicam.cs.mpgc.rpg130680.ui.grafica.SfondoStanza;
import it.unicam.cs.mpgc.rpg130680.ui.grafica.Sprite;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.*;
import java.util.function.BooleanSupplier;

public class ScenaAzione {
    private static final double VELOCITA = 200;
    private static final double VELOCITA_PROIETTILE = 700;
    private static final double CADENZA = 0.3;
    private static final double LUNGHEZZA_MAX = 800;
    private static final double INIZIO_X = 100;
    private static final double INIZIO_Y = 270;
    private static final double X_MIN = 30;
    private static final double X_MAX = 570;
    private static final double Y_MIN = 240;
    private static final double Y_MAX = 290;
    private static final double CANNA_X = 20;
    private static final double CANNA_Y = -42;
    private static final Set<KeyCode> TASTI = Set.of(
            KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D,
            KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);

    private final BooleanSupplier suColpoANemico;
    private final BooleanSupplier suColpoAVuoto;
    private final StackPane contenitore = new StackPane();
    private final Pane stratoProiettili = new Pane();
    private final List<Proiettile> proiettili = new ArrayList<>();
    private final Set<KeyCode> tastiPremuti = new HashSet<>();
    private final Group soldato = Sprite.soldato();
    private Group nemicoSprite;
    private Stanza stanzaMostrata;
    private double miraX = 500;
    private double miraY = 230;
    private double ricarica;

    private final AnimationTimer timer = new AnimationTimer() {
        private long ultimo;

        @Override
        public void handle(long adesso) {
            if (ultimo != 0) {
                double secondi = (adesso - ultimo) / 1_000_000_000.0;
                ricarica = Math.max(0, ricarica - secondi);
                muovi(secondi);
                if (tastiPremuti.contains(KeyCode.SPACE)) {
                    provaASparare();
                }
                aggiornaProiettili(secondi);
            }
            ultimo = adesso;
        }
    };

    public ScenaAzione(BooleanSupplier suColpoANemico, BooleanSupplier suColpoAVuoto) {
        if (suColpoANemico == null || suColpoAVuoto == null) {
            throw new IllegalArgumentException("azioni di sparo nulle");
        }
        this.suColpoANemico = suColpoANemico;
        this.suColpoAVuoto = suColpoAVuoto;
        soldato.setTranslateX(INIZIO_X);
        soldato.setTranslateY(INIZIO_Y);
        stratoProiettili.setPrefSize(SfondoStanza.LARGHEZZA, SfondoStanza.ALTEZZA);
        stratoProiettili.setMinSize(SfondoStanza.LARGHEZZA, SfondoStanza.ALTEZZA);
        stratoProiettili.setMaxSize(SfondoStanza.LARGHEZZA, SfondoStanza.ALTEZZA);
        stratoProiettili.setMouseTransparent(true);
        contenitore.setCursor(Cursor.CROSSHAIR);
        contenitore.setOnMouseMoved(this::aggiornaMira);
        contenitore.setOnMouseDragged(this::aggiornaMira);
        contenitore.setOnMousePressed(evento -> {
            aggiornaMira(evento);
            provaASparare();
        });
        contenitore.sceneProperty().addListener((osservato, vecchia, nuova) -> {
            if (nuova != null) {
                nuova.addEventFilter(KeyEvent.KEY_PRESSED, this::tastoPremuto);
                nuova.addEventFilter(KeyEvent.KEY_RELEASED, this::tastoRilasciato);
            }
        });
        timer.start();
    }

    public Node getNodo() {
        return contenitore;
    }

    public void mostra(Optional<Stanza> stanza) {
        Stanza nuova = stanza.orElse(null);
        boolean stessaStanza = nuova == stanzaMostrata;
        stanzaMostrata = nuova;
        contenitore.getChildren().clear();
        if (!stessaStanza) {
            soldato.setTranslateX(INIZIO_X);
            soldato.setTranslateY(INIZIO_Y);
            stratoProiettili.getChildren().clear();
            proiettili.clear();
        }
        if (nuova == null) {
            nemicoSprite = null;
            return;
        }
        contenitore.getChildren().addAll(creaScena(nuova), stratoProiettili);
    }

    private void aggiornaMira(MouseEvent evento) {
        Point2D punto = stratoProiettili.sceneToLocal(evento.getSceneX(), evento.getSceneY());
        if (punto != null) {
            miraX = punto.getX();
            miraY = punto.getY();
        }
    }

    private void tastoPremuto(KeyEvent evento) {
        if (TASTI.contains(evento.getCode())) {
            tastiPremuti.add(evento.getCode());
            evento.consume();
        }
    }

    private void tastoRilasciato(KeyEvent evento) {
        if (TASTI.contains(evento.getCode())) {
            tastiPremuti.remove(evento.getCode());
            evento.consume();
        }
    }

    private void muovi(double secondi) {
        double dx = 0;
        double dy = 0;
        if (tastiPremuti.contains(KeyCode.A) || tastiPremuti.contains(KeyCode.LEFT)) {
            dx -= 1;
        }
        if (tastiPremuti.contains(KeyCode.D) || tastiPremuti.contains(KeyCode.RIGHT)) {
            dx += 1;
        }
        if (tastiPremuti.contains(KeyCode.W) || tastiPremuti.contains(KeyCode.UP)) {
            dy -= 1;
        }
        if (tastiPremuti.contains(KeyCode.S) || tastiPremuti.contains(KeyCode.DOWN)) {
            dy += 1;
        }
        if (dx == 0 && dy == 0) {
            return;
        }
        double lunghezza = Math.hypot(dx, dy);
        double x = soldato.getTranslateX() + dx / lunghezza * VELOCITA * secondi;
        double y = soldato.getTranslateY() + dy / lunghezza * VELOCITA * secondi;
        soldato.setTranslateX(limita(x, X_MIN, X_MAX));
        soldato.setTranslateY(limita(y, Y_MIN, Y_MAX));
    }

    private double limita(double valore, double minimo, double massimo) {
        return Math.max(minimo, Math.min(massimo, valore));
    }

    private void provaASparare() {
        if (stanzaMostrata == null || ricarica > 0) {
            return;
        }
        double x0 = soldato.getTranslateX() + CANNA_X;
        double y0 = soldato.getTranslateY() + CANNA_Y;
        double lunghezza = Math.hypot(miraX - x0, miraY - y0);
        double dx = lunghezza == 0 ? 1 : (miraX - x0) / lunghezza;
        double dy = lunghezza == 0 ? 0 : (miraY - y0) / lunghezza;
        double distanza = distanzaDalNemico(x0, y0, dx, dy);
        boolean sullaLineaDiTiro = distanza >= 0;
        boolean sparato = sullaLineaDiTiro
                ? suColpoANemico.getAsBoolean()
                : suColpoAVuoto.getAsBoolean();
        if (!sparato) {
            return;
        }
        ricarica = CADENZA;
        lancia(x0, y0, dx, dy, sullaLineaDiTiro ? distanza : LUNGHEZZA_MAX);
    }

    private double distanzaDalNemico(double x0, double y0, double dx, double dy) {
        if (nemicoSprite == null) {
            return -1;
        }
        Bounds limiti = nemicoSprite.getBoundsInParent();
        for (double t = 0; t <= LUNGHEZZA_MAX; t += 4) {
            if (limiti.contains(x0 + dx * t, y0 + dy * t)) {
                return t;
            }
        }
        return -1;
    }

    private void lancia(double x0, double y0, double dx, double dy, double distanza) {
        Circle forma = new Circle(x0, y0, 3, Color.GOLD);
        stratoProiettili.getChildren().add(forma);
        proiettili.add(new Proiettile(forma, dx * VELOCITA_PROIETTILE,
                dy * VELOCITA_PROIETTILE, distanza));
    }

    private void aggiornaProiettili(double secondi) {
        Iterator<Proiettile> iteratore = proiettili.iterator();
        while (iteratore.hasNext()) {
            Proiettile p = iteratore.next();
            p.forma.setCenterX(p.forma.getCenterX() + p.vx * secondi);
            p.forma.setCenterY(p.forma.getCenterY() + p.vy * secondi);
            p.restante -= VELOCITA_PROIETTILE * secondi;
            if (p.restante <= 0 || fuori(p.forma)) {
                stratoProiettili.getChildren().remove(p.forma);
                iteratore.remove();
            }
        }
    }

    private boolean fuori(Circle forma) {
        return forma.getCenterX() < 0 || forma.getCenterX() > SfondoStanza.LARGHEZZA
                || forma.getCenterY() < 0 || forma.getCenterY() > SfondoStanza.ALTEZZA;
    }

    private Pane creaScena(Stanza stanza) {
        Pane scena = SfondoStanza.per(stanza.getNome());
        nemicoSprite = null;
        stanza.getNemico().ifPresent(n -> {
            nemicoSprite = Sprite.nemico();
            posiziona(scena, nemicoSprite, 480, 270);
        });
        List<Ostaggio> ostaggi = stanza.ostaggiDaLiberare();
        for (int i = 0; i < ostaggi.size(); i++) {
            posiziona(scena, Sprite.ostaggio(), 250 + i * 60, 270);
        }
        int posizione = 0;
        for (Arma arma : stanza.getArmi()) {
            posiziona(scena, ridotto(Sprite.perArma(arma)), 180 + posizione * 60, 250);
            posizione++;
        }
        for (Consumabile consumabile : stanza.getConsumabili()) {
            posiziona(scena, ridotto(Sprite.perConsumabile(consumabile)), 180 + posizione * 60, 250);
            posizione++;
        }
        scena.getChildren().add(soldato);
        return scena;
    }

    private Group ridotto(Group sprite) {
        sprite.setScaleX(0.6);
        sprite.setScaleY(0.6);
        return sprite;
    }

    private void posiziona(Pane scena, Node nodo, double x, double y) {
        nodo.setTranslateX(x);
        nodo.setTranslateY(y);
        scena.getChildren().add(nodo);
    }

    private static class Proiettile {
        private final Circle forma;
        private final double vx;
        private final double vy;
        private double restante;

        private Proiettile(Circle forma, double vx, double vy, double restante) {
            this.forma = forma;
            this.vx = vx;
            this.vy = vy;
            this.restante = restante;
        }
    }
}

