package com.apps.diogo.timetobusufrn.Classes.Modelos;

import java.io.Serializable;

/**
 * Created by Diogo on 07/11/2017.
 */

public class Onibuss implements Serializable
{
    String nomeEmpresa;
    int idEmpresa;
    
    public Onibuss(String nomeEmpresa, int idEmpresa)
    {
        this.nomeEmpresa = nomeEmpresa;
        this.idEmpresa = idEmpresa;
    }
    
    public String getNomeEmpresa()
    {
        return nomeEmpresa;
    }
    
    public int getIdEmpresa()
    {
        return idEmpresa;
    }
}
