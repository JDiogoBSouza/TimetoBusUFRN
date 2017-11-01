package com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus;

import java.io.Serializable;

/**
 * Created by Diogo on 24/10/2017.
 */

public class Onibus implements Serializable
{
    private int id;
    private int tipo;
    private int empresa;
    
    public Onibus()
    {
        super();
        id = 1;
        tipo = 1;
        empresa = 1;
    }
    
    public Onibus(int id, int tipo, int empresa)
    {
        super();
        this.id = id;
        this.tipo = tipo;
        this.empresa = empresa;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getTipo() {
        return tipo;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public int getEmpresa() {
        return empresa;
    }
    
    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }
}
