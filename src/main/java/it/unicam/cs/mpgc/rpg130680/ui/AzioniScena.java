package it.unicam.cs.mpgc.rpg130680.ui;

import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;

import java.util.Optional;

public interface AzioniScena {
    boolean spara();

    boolean sparaAlVuoto();

    boolean colpoDelNemico();

    boolean raccogliArma(Arma arma);

    boolean raccogliConsumabile(Consumabile consumabile);

    boolean liberaOstaggio(Ostaggio ostaggio);

    boolean attraversaPorta();

    boolean equipaggiaArma(int posizione);

    Optional<Consumabile> usaConsumabile(int posizione);
}
