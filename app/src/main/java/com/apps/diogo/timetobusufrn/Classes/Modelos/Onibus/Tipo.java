package com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus;

/**
 * Created by Diogo on 01/11/2017.
 */

public class Tipo
{
    public static final int DIRETO = 1, INVERSO = 2, EXPRESSOCET = 3, EXPRESSOCETVDRU = 4, EXPRESSOCETRUVD = 5, EXPRESSOREITORIA = 6, EXPRESSOREITORIAVDRU = 7, EXPRESSOREITORIARUVD = 8;
    
    private int id;
    private String descricao;
    
    public Tipo(int id, String descricao)
    {
        this.id = id;
        this.descricao = descricao;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
