package it.unicam.cs.mpgc.rpg130680.ui;

import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import it.unicam.cs.mpgc.rpg130680.ui.grafica.SfondoStanza;
import it.unicam.cs.mpgc.rpg130680.ui.grafica.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ScenaAzione {
    private static final double VELOCITA = 200;
    private static final double INIZIO_X = 100;
    private static final double INIZIO_Y = 270;
    private static final double X_MIN = 30;
    private static final double X_MAX = 570;
    private static final double Y_MIN = 240;
    private static final double Y_MAX = 290;
    private static final Set<KeyCode> TASTI = Set.of(
            KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D,
            KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);

    private final StackPane contenitore = new StackPane();
    private final Set<KeyCode> tastiPremuti = new HashSet<>();
    private final Group soldato = Sprite.soldato();
    private Stanza stanzaMostrata;

    private final AnimationTimer timer = new AnimationTimer() {
        private long ultimo;

        @Override
        public void handle(long adesso) {
            if (ultimo != 0) {
                muovi((adesso - ultimo) / 1_000_000_000.0);
            }
            ultimo = adesso;
        }
    };

    public ScenaAzione() {
        soldato.setTranslateX(INIZIO_X);
        soldato.setTranslateY(INIZIO_Y);
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
        if (nuova == null) {
            return;
        }
        if (!stessaStanza) {
            soldato.setTranslateX(INIZIO_X);
            soldato.setTranslateY(INIZIO_Y);
        }
        contenitore.getChildren().add(creaScena(nuova));
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

    private Pane creaScena(Stanza stanza) {
        Pane scena = SfondoStanza.per(stanza.getNome());
        stanza.getNemico().ifPresent(n -> posiziona(scena, Sprite.nemico(), 480, 270));
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
}

