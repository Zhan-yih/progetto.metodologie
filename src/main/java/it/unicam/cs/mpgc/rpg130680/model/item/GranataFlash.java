package it.unicam.cs.mpgc.rpg130680.model.item;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;

public class GranataFlash implements Consumabile {
    @Override
    public String getNome() {
        return "Granata flash";
    }

    @Override
    public void usa(Soldato soldato, Nemico nemico){
        if(nemico==null){
            throw new IllegalArgumentException("Nemico nullo");
        }
        nemico.stordisci();
    }
}
