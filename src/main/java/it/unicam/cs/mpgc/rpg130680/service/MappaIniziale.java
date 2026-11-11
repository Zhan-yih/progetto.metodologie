package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.item.*;
import it.unicam.cs.mpgc.rpg130680.model.map.Mappa;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import java.util.List;

public class MappaIniziale {
    private MappaIniziale() {}

    public static Mappa crea(){
        return new Mappa(List.of(
                ingresso(), armeria(), infermeria(), magazzino(), prigione()));
    }

    private static Stanza ingresso() {
        Stanza stanza= new Stanza("ingresso","Un atrio buio con una porta blindata");
        stanza.aggiungiArma(new Arma("Pistola", 15, 75, 12));
        return stanza;
    }

    private static Stanza armeria() {
        Stanza stanza= new Stanza("armeria","Una stanza con un fucile d'assalto e una GranataFlash");
        Nemico nemico= new Nemico("Guardia",40,new Arma("Pistola", 15, 75, 12));
        stanza.aggiungiArma(new Arma("Fucile d'assalto", 25, 75, 20));
        stanza.aggiungiConsumabile(new GranataFlash());
        stanza.inserisciNemico(nemico);
        return stanza;
    }

    private static Stanza infermeria() {
        Stanza stanza= new Stanza("infermeria","Una stanza con Kit medico");
        Nemico nemico= new Nemico("Suporto Nemico",50,new Arma("Fucile d'assalto", 25, 75, 20));
        stanza.aggiungiConsumabile(new KitMedico(30));
        stanza.inserisciNemico(nemico);
        return stanza;
    }

    private static Stanza magazzino() {
        Stanza stanza= new Stanza("magazzino", "Una grande stanza con granata fumogena");
        Nemico nemico= new Nemico("Mercenario", 55,new Arma("Fucile d'assalto", 25, 75, 20));
        stanza.aggiungiConsumabile(new GranataFumogena());
        stanza.inserisciNemico(nemico);
        return stanza;
    }

    private static Stanza prigione() {
        Stanza stanza= new Stanza("prigione","Una stanza con un nemici e tre ostaggi");
        Nemico nemico= new Nemico("Capo",80,new Arma("Cecchino", 50, 70, 5));
        Ostaggio osatggio1= new Ostaggio("Marco");
        Ostaggio osatggio2= new Ostaggio("Anna");
        Ostaggio osatggio3= new Ostaggio("Matteo");
        stanza.inserisciNemico(nemico);
        stanza.aggiungiOstaggio(osatggio1);
        stanza.aggiungiOstaggio(osatggio2);
        stanza.aggiungiOstaggio(osatggio3);
        return stanza;
    }
}
