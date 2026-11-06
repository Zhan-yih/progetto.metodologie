package it.unicam.cs.mpgc.rpg130680.model.map;


import java.util.List;

public class Mappa {
    private final List<Stanza>  stanze;

    public Mappa(List<Stanza> stanze){
        if(stanze == null || stanze.isEmpty()){
            throw new IllegalArgumentException("non ci stanno le stanze");
        }
        this.stanze= List.copyOf(stanze);
    }

    public List<Stanza> getStanze(){
        return stanze;
    }

    public Stanza getStanza(int indice){
        if(indice < 0 || indice >= stanze.size()){
            throw new IllegalArgumentException("indice non valido");
        }
        return stanze.get(indice);
    }

    public int numeroStanze(){
        return stanze.size();
    }

    public List<Stanza> stanzeNemici(){
        return stanze.stream()
                .filter(s-> s.getNemico().isPresent())
                .toList();
    }
}
