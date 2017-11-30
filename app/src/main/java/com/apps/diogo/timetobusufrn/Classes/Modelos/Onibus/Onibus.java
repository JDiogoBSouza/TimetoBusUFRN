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
    
    /**
     * Retorna o nome da empresa em formato String de acordo com o id informado.
     * @return : Nome da empresa.
     */
    public String getNomeEmpresa()
    {
        String nome = "";
        
        switch( this.empresa )
        {
            case 1:
                nome = "Guanabara";
                break;
            
            case 2:
                nome = "Via Sul";
                break;
            
            case 3:
                nome = "Conceição";
                break;
            
            case 4:
                nome = "Cid. do Natal";
                break;
            
            case 5:
                nome = "Santa Maria";
                break;
            
            case 6:
                nome = "Reunidas";
                break;
            
            default:
                nome = "Default";
                break;
        }
        
        return nome;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getTipo() {
        return this.tipo;
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
