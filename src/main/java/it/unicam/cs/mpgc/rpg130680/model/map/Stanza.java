package it.unicam.cs.mpgc.rpg130680.model.map;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stanza {
    private final String nome;
    private final String descrizione;
    private final List<Arma> armi = new ArrayList<>();
    private Nemico nemico;

    public Stanza(String nome, String descrizione) {
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("nome non valido");
        }
        if(descrizione == null || descrizione.isBlank()){
            throw new IllegalArgumentException("descrizione non valido");
        }
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public List<Arma> prendiArmi() {
        List<Arma> copia = List.copyOf(armi);
        armi.clear();
        return copia;
    }

    public void aggiungiArma(Arma arma) {
        if(arma == null){
            throw new IllegalArgumentException("arma non valido");
        }
        armi.add(arma);
    }

    public void inserisciNemico(Nemico nemico) {
        if(nemico == null){
            throw new IllegalArgumentException("nemico non valido");
        }
        this.nemico = nemico;
    }

    public Optional<Nemico> getNemico() {
        return Optional.ofNullable(nemico);
    }

    public void rimuoviNemico(){
        this.nemico = null;
    }
}
