package it.unicam.cs.mpgc.rpg130680.ui;

import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import it.unicam.cs.mpgc.rpg130680.service.Partita;
import it.unicam.cs.mpgc.rpg130680.service.StatoPartita;
import it.unicam.cs.mpgc.rpg130680.ui.grafica.SfondoStanza;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class SchermataGioco extends BorderPane {
    private final PartitaController controller;
    private final Label titolo = new Label();
    private final StackPane areaScena = new StackPane();
    private final TextArea registro = new TextArea();
    private final List<Button> pulsantiStanze = new ArrayList<>();

    public SchermataGioco(PartitaController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("controller nullo");
        }
        this.controller = controller;
        registro.setEditable(false);
        registro.setPrefRowCount(5);
        setTop(titolo);
        setCenter(areaScena);
        setLeft(creaPannelloStanze());
        setBottom(registro);
        setPadding(new Insets(10));
        aggiorna();
    }

    private VBox creaPannelloStanze() {
        VBox pannello = new VBox(8);
        pannello.setPadding(new Insets(0, 10, 0, 0));
        pannello.getChildren().add(new Label("Stanze"));
        List<Stanza> stanze = controller.getPartita().getMappa().getStanze();
        for (int i = 0; i < stanze.size(); i++) {
            int indice = i;
            Button pulsante = new Button(stanze.get(i).getNome());
            pulsante.setMaxWidth(Double.MAX_VALUE);
            pulsante.setOnAction(e -> esegui(() -> controller.entra(indice)));
            pulsantiStanze.add(pulsante);
            pannello.getChildren().add(pulsante);
        }
        return pannello;
    }

    private void esegui(Supplier<String> azione) {
        try {
            registro.appendText(azione.get() + "\n");
        } catch (IllegalStateException | IllegalArgumentException e) {
            registro.appendText("Azione non possibile: " + e.getMessage() + "\n");
        }
        aggiorna();
    }

    private void aggiorna() {
        Partita partita = controller.getPartita();
        Optional<Stanza> corrente = partita.getStanzaCorrente();
        titolo.setText(corrente.map(Stanza::getNome).orElse("Scegli una stanza")
                + " - Vita: " + partita.getSoldato().getPuntiVita());

        areaScena.getChildren().clear();
        corrente.ifPresent(s -> areaScena.getChildren().add(SfondoStanza.per(s.getNome())));

        boolean inCorso = partita.getStato() == StatoPartita.IN_CORSO;
        for (int i = 0; i < pulsantiStanze.size(); i++) {
            boolean esplorata = partita.getStanzeEsplorate().contains(i);
            pulsantiStanze.get(i).setDisable(!inCorso || esplorata || corrente.isPresent());
        }
    }
}
