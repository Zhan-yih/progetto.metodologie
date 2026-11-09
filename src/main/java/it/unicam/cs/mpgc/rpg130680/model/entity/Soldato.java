package it.unicam.cs.mpgc.rpg130680.model.entity;

import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.item.Inventario;
import java.util.List;
import java.util.Optional;

public class Soldato extends Personaggio {
    private final Inventario inventario;
    private Arma armaEquipaggiata;

    public Soldato( String nome) {
        super(nome, 100);
        inventario = new Inventario();
    }

    public void raccogliArma(Arma arma) {
        inventario.aggiungiArma(arma);
    }

    public void equipaggiaArma(Arma arma) {
        if (arma == null || !inventario.contiene(arma)) {
            throw new IllegalArgumentException("Arma non posseduta");
        }
        armaEquipaggiata = arma;
    }

    public Optional<Arma> getArmaEquipaggiata() {
       return Optional.ofNullable(armaEquipaggiata);

    }

    public List<Arma> getArmi() {
        return inventario.getArmi();
    }

    public void raccogliConsumabile(Consumabile c){
        inventario.aggiungiConsumabile(c);
    }

    public List<Consumabile> getConsumabili() {
        return inventario.getConsumabili();
    }

    public void usaConsumabile(Consumabile c, Nemico nemico){
        if(c == null || !inventario.getConsumabili().contains(c)){
            throw new IllegalArgumentException("Consumabile non posseduto");
        }
        c.usa(this,nemico);
        inventario.rimuoviConsumabile(c);
    }

    public void scartaConsumabile(Consumabile c){
        if(c == null || !inventario.getConsumabili().contains(c)){
            throw new IllegalArgumentException("Consumabile non posseduto");
        }
        inventario.rimuoviConsumabile(c);
    }

}
