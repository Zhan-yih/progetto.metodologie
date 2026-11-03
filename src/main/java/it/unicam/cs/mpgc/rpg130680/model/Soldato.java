package it.unicam.cs.mpgc.rpg130680.model;

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

}
