package it.unicam.cs.mpgc.rpg130680.persistence;

import it.unicam.cs.mpgc.rpg130680.service.Combattimento;
import it.unicam.cs.mpgc.rpg130680.service.Partita;

public interface SalvataggioRepository {
    void salva(Partita partita);
    Partita carica(Combattimento combattimento);
}
