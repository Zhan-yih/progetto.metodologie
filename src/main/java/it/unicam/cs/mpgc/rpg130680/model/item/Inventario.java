package it.unicam.cs.mpgc.rpg130680.model.item;

import java.util.List;
import java.util.ArrayList;

public class Inventario {
    private final List<Arma> armi = new ArrayList<>();
    private final List<Consumabile> consumabili = new ArrayList<>();

    public void aggiungiArma(Arma arma) {
        if(arma == null){
            throw new IllegalArgumentException("Arma nulla");
        }
        armi.add(arma);
    }

    public boolean rimuoviArma(Arma arma) {
        return armi.remove(arma);
    }

    public boolean contiene(Arma arma) {
        return armi.contains(arma);
    }

    public List<Arma> getArmi() {
        return List.copyOf(armi);
    }

    public boolean isVuoto(){
        return armi.isEmpty() && consumabili.isEmpty();
    }

    public List<Arma> armiUtilizzabili(){
        return armi.stream()
                .filter(a-> a.haMunizioni())
                .toList();
    }

    public void aggiungiConsumabile(Consumabile c){
        if(c == null){
            throw new IllegalArgumentException("Consumabile nullo");
        }
        consumabili.add(c);
    }

    public boolean rimuoviConsumabile(Consumabile c){
        return consumabili.remove(c);
    }

    public List<Consumabile> getConsumabili() {
        return List.copyOf(consumabili);
    }

}
