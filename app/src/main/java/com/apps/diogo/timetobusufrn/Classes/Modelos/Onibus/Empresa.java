package com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus;

/**
 * Created by Diogo on 01/11/2017.
 */

public class Empresa
{
    public static final int GUANABARA = 1, VIASUL = 2, CONCEICAO = 3, CIDADEDONATAL = 4, SANTAMARIA = 5, REUNIDAS = 6;
    
    private int id;
    private String nome;
    
    public Empresa(int id, String nome)
    {
        this.id = id;
        this.nome = nome;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
}
