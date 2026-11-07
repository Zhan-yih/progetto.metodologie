package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Personaggio;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import java.util.function.IntSupplier;

public class Combattimento {
    private final IntSupplier tiro;
    private static final int MALUS_FUMO= 30;

    public Combattimento(IntSupplier tiro){
        if(tiro == null){
            throw new IllegalArgumentException("tiro non valido");
        }
        this.tiro = tiro;
    }

    private int colpisci(Arma arma, Personaggio bersaglio, int precisione){
        if(!arma.haMunizioni()){
            return 0;
        }
        arma.consumaColpo();
        if(tiro.getAsInt() < precisione){
            bersaglio.subisciDanno(arma.getDanno());
            return arma.getDanno();
        }
        return 0;
    }

    public int attaccaNemico(Soldato soldato, Nemico nemico){
        Arma arma= soldato.getArmaEquipaggiata()
                .orElseThrow(() -> new IllegalStateException("nessuna arma equipaggiata"));
        return colpisci(arma, nemico, arma.getPrecisione());
    }

    public int attaccaSoldato(Nemico nemico, Soldato soldato){
        Arma arma= nemico.getArma();
        int precisione= arma.getPrecisione();
        if(nemico.isAccecato()){
            precisione= Math.max(0,precisione-MALUS_FUMO);
        }
         return colpisci(arma, soldato, precisione);
    }

    public EsitoTurno giocaTurno(Soldato soldato, Nemico nemico){
        int dannoInflitto= attaccaNemico(soldato, nemico);
        if(!nemico.isVivo()){
            return new EsitoTurno(dannoInflitto,0,true,false);
        }
        if(nemico.isStordito()){
            nemico.riprendi();
            nemico.riduciAccecamento();
            return new EsitoTurno(dannoInflitto,0,false,false);
        }
        int dannoSubito= attaccaSoldato(nemico, soldato);
        nemico.riduciAccecamento();
        return new EsitoTurno(dannoInflitto,dannoSubito,!nemico.isVivo(),!soldato.isVivo());
    }
}
