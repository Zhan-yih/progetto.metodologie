package it.unicam.cs.mpgc.rpg130680.ui;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import it.unicam.cs.mpgc.rpg130680.persistence.SalvataggioException;
import it.unicam.cs.mpgc.rpg130680.service.Partita;
import it.unicam.cs.mpgc.rpg130680.service.StatoPartita;
import it.unicam.cs.mpgc.rpg130680.ui.grafica.Sprite;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javafx.scene.layout.StackPane;

public class SchermataGioco extends BorderPane {
    private final PartitaController controller;
    private final Label titolo = new Label();
    private final TextArea registro = new TextArea();
    private final VBox inventario = new VBox(6);
    private final ScenaAzione scenaAzione;
    private final Label messaggioFine= new Label();

    public SchermataGioco(PartitaController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("controller nullo");
        }
        this.controller = controller;
        this.scenaAzione = new ScenaAzione(new AzioniScena() {
            @Override
            public boolean spara() {
                return esegui(controller::spara);
            }

            @Override
            public boolean sparaAlVuoto() {
                return esegui(controller::sparaAlVuoto);
            }

            @Override
            public boolean colpoDelNemico() {
                return esegui(controller::turnoNemico);
            }

            @Override
            public boolean raccogliArma(Arma arma) {
                return esegui(() -> controller.raccogliArma(arma));
            }

            @Override
            public boolean raccogliConsumabile(Consumabile consumabile) {
                return esegui(() -> controller.raccogliConsumabile(consumabile));
            }

            @Override
            public boolean liberaOstaggio(Ostaggio ostaggio){
                return esegui(() -> controller.liberaOstaggio(ostaggio));
            }

            @Override
            public boolean attraversaPorta(){
                return esegui(controller::attraversaPorta);
            }

            @Override
            public boolean equipaggiaArma(int posizione) {
                List<Arma> armi = new ArrayList<>(controller.getPartita().getSoldato().getArmi());
                return posizione < armi.size() && esegui(() -> controller.equipaggia(armi.get(posizione)));
            }

            @Override
            public Optional<Consumabile> usaConsumabile(int posizione) {
                List<Consumabile> consumabili =
                        new ArrayList<>(controller.getPartita().getSoldato().getConsumabili());
                if (posizione >= consumabili.size()) {
                    return Optional.empty();
                }
                Consumabile scelto = consumabili.get(posizione);
                return esegui(() -> controller.usa(scelto)) ? Optional.of(scelto) : Optional.empty();
            }
        });
        registro.setEditable(false);
        registro.setPrefRowCount(5);
        setTop(titolo);
        messaggioFine.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;"
                + "-fx-background-color: rgba(0,0,0,0.75); -fx-padding: 20 40 20 40;");
        messaggioFine.setVisible(false);
        messaggioFine.setMouseTransparent(true);
        setCenter(new StackPane(scenaAzione.getNodo(), messaggioFine));
        setRight(creaPannelloAzioni());
        setBottom(registro);
        setPadding(new Insets(10));
        Partita partita = controller.getPartita();
        if (partita.getStanzaCorrente().isEmpty() && partita.getStanzeEsplorate().isEmpty()) {
            esegui(() -> controller.entra(0));
        }
        aggiorna();
    }

    private VBox creaPannelloAzioni() {
        Button salva = new Button("Salva");
        salva.setOnAction(e -> esegui(() -> {
            controller.salva();
            return "Partita salvata.";
        }));
        Button carica = new Button("Carica");
        carica.setOnAction(e -> esegui(() -> {
            controller.carica();
            return "Partita caricata.";
        }));
        Label comandi = new Label("WASD: muovi\nMouse/SPAZIO: spara\nE: interagisci\n1-3: arma\n4-6: consumabile");
        VBox pannello = new VBox(8, new Label("Azioni"), salva, carica, comandi,
                new Label("Inventario"), inventario);
        pannello.setPadding(new Insets(0, 0, 0, 10));
        return pannello;
    }

    private boolean esegui(Supplier<String> azione) {
        boolean riuscita= true;
        try{
            registro.appendText(azione.get() + "\n");
        } catch(IllegalStateException | IllegalArgumentException | SalvataggioException e){
            registro.appendText("Azione non possibile: " + e.getMessage() + "\n");
            riuscita = false;
        }
        aggiorna();
        return riuscita;
    }

    private void aggiorna() {
        Partita partita = controller.getPartita();
        Optional<Stanza> corrente = partita.getStanzaCorrente();
        Optional<Nemico> nemico = corrente.flatMap(Stanza::getNemico);
        boolean inCorso = partita.getStato() == StatoPartita.IN_CORSO;
        StringBuilder testo = new StringBuilder(corrente.map(Stanza::getNome).orElse("Scegli una stanza"));
        testo.append(" - Vita: ").append(partita.getSoldato().getPuntiVita());
        nemico.ifPresent(n -> testo.append(" - ").append(n.getNome())
                .append(": ").append(n.getPuntiVita()).append(" PV"));
        if (!inCorso) {
            testo.append(" - ").append(partita.getStato());
        }
        titolo.setText(testo.toString());
        scenaAzione.mostra(corrente, partita.getSoldato());
        messaggioFine.setVisible(!inCorso);
        messaggioFine.setText(partita.getStato() == StatoPartita.VITTORIA
                ? "VITTORIA! Ostaggi salvati" : "SCONFITTA");
        aggiornaInventario();
    }

    private void aggiornaInventario() {
        inventario.getChildren().clear();
        Soldato soldato = controller.getPartita().getSoldato();
        List<Arma> armi = new ArrayList<>(soldato.getArmi());
        for (int i = 0; i < armi.size(); i++) {
            Arma arma = armi.get(i);
            boolean equipaggiata = soldato.getArmaEquipaggiata().map(a -> a == arma).orElse(false);
            Label voce = new Label(numero(i, 1) + (equipaggiata ? "> " : "") + arma.getNome()
                    + " (" + arma.getMunizioni() + ")");
            voce.setGraphic(icona(Sprite.perArma(arma)));
            inventario.getChildren().add(voce);
        }
        List<Consumabile> consumabili = new ArrayList<>(soldato.getConsumabili());
        for (int i = 0; i < consumabili.size(); i++) {
            Consumabile consumabile = consumabili.get(i);
            Label voce = new Label(numero(i, 4) + consumabile.getNome());
            voce.setGraphic(icona(Sprite.perConsumabile(consumabile)));
            inventario.getChildren().add(voce);
        }
    }

    private String numero(int posizione, int primo) {
        return posizione < 3 ? "[" + (primo + posizione) + "] " : "      ";
    }

    private Node icona(Group sprite) {
        sprite.setScaleX(0.4);
        sprite.setScaleY(0.4);
        return new Group(sprite);
    }
}
