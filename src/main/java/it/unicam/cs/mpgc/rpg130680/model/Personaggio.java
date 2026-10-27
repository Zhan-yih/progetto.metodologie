package it.unicam.cs.mpgc.rpg130680.model;

public abstract class Personaggio {
    private final String nome;
    private final int puntiVitaMax;
    private int puntiVita;

    public Personaggio(String nome, int puntiVitaMax ){
        if(puntiVitaMax <= 0){
            throw new IllegalArgumentException("punti di vita non valido");
        }
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("nome non valido");
        }
        this.nome = nome;
        this.puntiVita = puntiVitaMax;
        this.puntiVitaMax = puntiVitaMax;
    }


    public String getNome() {
        return nome;
    }

    public int getPuntiVita() {
        return puntiVita;
    }

    public int getPuntiVitaMax() {
        return puntiVitaMax;
    }

    public boolean isVivo() {
        return puntiVita > 0;
    }

    public void subisciDanno(int danno) {
        if (danno < 0) {
            throw new IllegalArgumentException("valore non valido   ");
        }
        puntiVita = Math.max(0, puntiVita - danno);
    }

    public void cura(int quantita){
        if(quantita < 0){
            throw new IllegalArgumentException("valore non valido   ");
        }
        puntiVita = Math.min(puntiVita + quantita, puntiVitaMax);
    }
}


