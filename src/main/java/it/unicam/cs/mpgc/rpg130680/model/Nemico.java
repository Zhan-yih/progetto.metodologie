package it.unicam.cs.mpgc.rpg130680.model;

public class Nemico extends Personaggio{
    private final Arma arma;

    public Nemico(String nome, int puntiVitaMax, Arma arma){
        super(nome, puntiVitaMax);
        if(arma == null){
            throw new IllegalArgumentException("armqa non esiste");
        }
        this.arma = arma;
    }

    public Arma getArma() {
        return arma;
    }
}
