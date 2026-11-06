package it.unicam.cs.mpgc.rpg130680.model.item;

public class Arma implements Oggetto {
    private final String nome;
    private final int danno;
    private final int precisione;
    private int munizioni;

    public Arma(String nome, int danno, int precisione, int munizioni) {
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("nome è nullo");
        }
        if(danno <= 0){
            throw new IllegalArgumentException("danno non valido");
        }
        if(precisione < 0 || precisione > 100){
            throw new IllegalArgumentException("precisione non valido");
        }
        if(munizioni < 0){
            throw new IllegalArgumentException("munizioni non valido");
        }
        this.nome = nome;
        this.danno = danno;
        this.precisione = precisione;
        this.munizioni = munizioni;
    }
    @Override
    public String getNome() {
        return nome;
    }

    public int getDanno() {
        return danno;
    }

    public int getPrecisione() {
        return precisione;
    }

    public int getMunizioni() {
        return munizioni;
    }

    public boolean haMunizioni(){
        return munizioni > 0;
    }

    public void consumaColpo(){
        if(!haMunizioni()){
            throw new IllegalStateException("colpi finiti");
        }
        munizioni--;
    }

    public void aggiungiMunizioni(int quantita){
        if(quantita < 0){
            throw new IllegalArgumentException("quantita non valido");
        }
        this.munizioni= this.munizioni + quantita;
    }

}
