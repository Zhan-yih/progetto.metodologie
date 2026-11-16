package it.unicam.cs.mpgc.rpg130680.ui;

import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;

public interface AzioniScena {
    boolean spara();

    boolean sparaAlVuoto();

    boolean colpoDelNemico();

    boolean raccogliArma(Arma arma);

    boolean raccogliConsumabile(Consumabile consumabile);

    boolean liberaOstaggio(Ostaggio ostaggio);
}
