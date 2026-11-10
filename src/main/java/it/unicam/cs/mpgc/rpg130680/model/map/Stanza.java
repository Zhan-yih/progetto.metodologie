package it.unicam.cs.mpgc.rpg130680.model.map;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stanza {
    private final String nome;
    private final String descrizione;
    private final List<Arma> armi = new ArrayList<>();
    private final List<Consumabile> consumabili = new ArrayList<>();
    private final List<Ostaggio> ostaggi = new ArrayList<>();
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

    public void aggiungiConsumabile(Consumabile consumabile) {
        if(consumabile == null){
            throw new IllegalArgumentException("consumabile non valido");
        }
        consumabili.add(consumabile);
    }

    public List<Consumabile> prendiConsumabili() {
        List<Consumabile> copia = List.copyOf(consumabili);
        consumabili.clear();
        return copia;
    }

    public void aggiungiOstaggio(Ostaggio ostaggio) {
        if(ostaggio == null){
            throw new IllegalArgumentException("ostaggio non valido");
        }
        ostaggi.add(ostaggio);
    }

    public List<Ostaggio> getOstaggi() {
        return List.copyOf(ostaggi);
    }

    public List<Ostaggio> ostaggiDaLiberare() {
        return ostaggi.stream()
                .filter(o-> !o.isLiberato())
                .toList();
    }

    public List<Arma> getArmi() {
        return List.copyOf(armi);
    }

    public List<Consumabile> getConsumabili() {
        return List.copyOf(consumabili);
    }
}
