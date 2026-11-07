package it.unicam.cs.mpgc.rpg130680.model.item;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;

public class GranataFumogena implements Consumabile{
    private static final int DURATA = 2;

    @Override
    public String getNome(){
        return "Granata fumogena";
    }

    @Override
    public void usa(Soldato soldato, Nemico nemico){
        if(nemico == null){
            throw new IllegalArgumentException("Nemico nullo");
        }
        nemico.accieca(DURATA);
    }
}
