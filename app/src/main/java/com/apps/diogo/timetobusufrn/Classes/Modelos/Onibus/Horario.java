package com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus;

/**
 * Created by Diogo on 01/11/2017.
 */

public class Horario
{
    private int id;
    private String saida;
    private String destino;
    private String chegada;
    private int id_onibus;
    
    public Horario(int id, String saida, String destino, String chegada, int id_onibus)
    {
        this.id = id;
        this.saida = saida;
        this.destino = destino;
        this.chegada = chegada;
        this.id_onibus = id_onibus;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSaida() {
        return saida;
    }
    
    public void setSaida(String saida) {
        this.saida = saida;
    }
    
    public String getDestino() {
        return destino;
    }
    
    public void setDestino(String destino) {
        this.destino = destino;
    }
    
    public String getChegada() {
        return chegada;
    }
    
    public void setChegada(String chegada) {
        this.chegada = chegada;
    }
    
    public int getId_onibus() {
        return id_onibus;
    }
    
    public void setId_onibus(int id_onibus) {
        this.id_onibus = id_onibus;
    }
}
