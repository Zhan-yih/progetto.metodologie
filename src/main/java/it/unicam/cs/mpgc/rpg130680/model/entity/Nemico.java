package it.unicam.cs.mpgc.rpg130680.model.entity;

import it.unicam.cs.mpgc.rpg130680.model.item.Arma;

public class Nemico extends Personaggio{
    private final Arma arma;
    private boolean stordito;

    public Nemico(String nome, int puntiVitaMax, Arma arma){
        super(nome, puntiVitaMax);
        if(arma == null){
            throw new IllegalArgumentException("arma nulla");
        }
        this.arma = arma;
    }

    public Arma getArma() {
        return arma;
    }

    public boolean isStordito() {
        return stordito;
    }

    public void stordisci() {
        stordito = true;
    }

    public void riprendi(){
        stordito = false;
    }
}
