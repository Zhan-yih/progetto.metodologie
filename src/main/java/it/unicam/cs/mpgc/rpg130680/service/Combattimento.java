package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Personaggio;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
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

    public EsitoTurno giocaTurno(Soldato soldato, Nemico nemico){
        int dannoInflitto= attaccaNemico(soldato, nemico);
        if(!nemico.isVivo()){
            return new EsitoTurno(dannoInflitto,0,true,false);
        }
        if(nemico.isStordito()){
            nemico.riprendi();
            return new EsitoTurno(dannoInflitto,0,false,false);
        }
        int dannoSubito= attaccaSoldato(nemico, soldato);
        return new EsitoTurno(dannoInflitto,dannoSubito,!nemico.isVivo(),!soldato.isVivo());
    }
}
