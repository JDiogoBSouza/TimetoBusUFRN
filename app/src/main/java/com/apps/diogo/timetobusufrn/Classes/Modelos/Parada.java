package com.apps.diogo.timetobusufrn.Classes.Modelos;

import java.io.Serializable;

/**
 * Created by Diogo on 24/10/2017.
 */

public class Parada implements Serializable
{
    String localizacao;
    int local;
    
    public void Parada(String localizacao, int local)
    {
        //super();
        this.localizacao = localizacao;
        this.local = local;
    }
}
