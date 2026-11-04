package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.Arma;
import it.unicam.cs.mpgc.rpg130680.model.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.Personaggio;
import it.unicam.cs.mpgc.rpg130680.model.Soldato;
import java.util.function.IntSupplier;

public class Combattimento {
    private final IntSupplier tiro;

    public Combattimento(IntSupplier tiro){
        if(tiro == null){
            throw new IllegalArgumentException("tiro non valido");
        }
        this.tiro = tiro;
    }

    private int colpisci(Arma arma, Personaggio bersaglio){
        if(!arma.haMunizioni()){
            return 0;
        }
        arma.consumaColpo();
        if(tiro.getAsInt() < arma.getPrecisione()){
            bersaglio.subisciDanno(arma.getDanno());
            return arma.getDanno();
        }
        return 0;
    }

    public int attaccaNemico(Soldato soldato, Nemico nemico){
        Arma arma= soldato.getArmaEquipaggiata()
                .orElseThrow(() -> new IllegalStateException("nessuna arma equipaggiata"));
        return colpisci(arma, nemico);
    }

    public int attaccaSoldato(Nemico nemico, Soldato soldato){
         return colpisci(nemico.getArma(), soldato);
    }
}
