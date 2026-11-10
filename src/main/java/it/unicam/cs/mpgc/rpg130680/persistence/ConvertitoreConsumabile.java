package it.unicam.cs.mpgc.rpg130680.persistence;

import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.item.GranataFlash;
import it.unicam.cs.mpgc.rpg130680.model.item.GranataFumogena;
import it.unicam.cs.mpgc.rpg130680.model.item.KitMedico;

public class ConvertitoreConsumabile {
    public static String tipoDi(Consumabile c) {
        if(c instanceof KitMedico){
            return "KIT_MEDICO";
        }
        if(c instanceof GranataFumogena){
            return "GRANATA_FUMOGENA";
        }
        if(c instanceof GranataFlash){
            return "GRANATA_FLASH";
        }
        throw new IllegalArgumentException("Tipo di non trovato");
    }

    public static Consumabile crea(String tipo, int quantita){
        if(tipo == null){
            throw new IllegalArgumentException("Tipo non trovato");
        }
        if(tipo.equals("KIT_MEDICO")){
            return new KitMedico(quantita);
        }
        if(tipo.equals("GRANATA_FUMOGENA")){
            return new GranataFumogena();
        }
        if(tipo.equals("GRANATA_FLASH")){
            return new GranataFlash();
        }
        throw new IllegalArgumentException("Tipo di non trovato");
    }
}
