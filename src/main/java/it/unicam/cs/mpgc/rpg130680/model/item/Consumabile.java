package it.unicam.cs.mpgc.rpg130680.model.item;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;

public interface Consumabile extends Oggetto {
    void usa(Soldato soldato, Nemico nemico);
    default boolean richiedeNemico(){
        return false;
    }
}
