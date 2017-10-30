package com.apps.diogo.timetobusufrn.Classes;

import java.io.Serializable;

/**
 * Created by Diogo on 24/10/2017.
 */

public class Onibus implements Serializable
{
    public static final int BRANCOAMARELO = 0, BRANCOLISTRAS = 1, BRANCOAZUL = 2, VERDE = 3;
    public static final int DIRETO = 0, INVERSO = 1, EXPRESSOCET = 2, EXPRESSOREITORIA = 3;
    
    private int cor;
    private int tipo;
    
    public void Onibus()
    {
        //super();
        cor = 0;
        tipo = 0;
    }
    
    public void Onibus(String nome, int cor, int tipo)
    {
        //super();
        this.cor = cor;
        this.tipo = tipo;
    }
}
