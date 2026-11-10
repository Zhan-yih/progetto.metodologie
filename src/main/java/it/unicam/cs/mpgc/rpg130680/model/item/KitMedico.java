package it.unicam.cs.mpgc.rpg130680.model.item;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;

public class KitMedico implements Consumabile {
    private final int quantita;

    public KitMedico(int quantita) {
        if(quantita <= 0){
            throw new IllegalArgumentException("valore non valido");
        }
        this.quantita = quantita;
    }

    @Override
    public String getNome() {
        return "Kit medico";
    }

    @Override
    public void usa(Soldato soldato, Nemico nemico){
        if(soldato == null){
            throw new IllegalArgumentException("soldato non esiste");
        }
        soldato.cura(quantita);
    }

    public int getQuantita() {
        return quantita;
    }
}
