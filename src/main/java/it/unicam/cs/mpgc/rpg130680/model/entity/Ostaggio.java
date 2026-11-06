package it.unicam.cs.mpgc.rpg130680.model.entity;

public class Ostaggio extends Personaggio {
    private boolean liberato;

    public Ostaggio(String nome) {
        super(nome,50);
    }

    public boolean isLiberato() {
        return liberato;
    }

    public void libera() {
        if(liberato){
            throw new IllegalStateException("ostaggio già liberato");
        }
        liberato=true;
    }
}
