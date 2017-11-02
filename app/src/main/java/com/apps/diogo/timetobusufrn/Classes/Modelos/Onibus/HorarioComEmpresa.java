package com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus;

/**
 * Created by Diogo on 01/11/2017.
 */

public class HorarioComEmpresa extends Horario
{
    private String nomeEmpresa;
    
    public HorarioComEmpresa(int id, String saida, String destino, String chegada, int id_onibus, String nomeEmpresa)
    {
        super(id, saida, destino, chegada, id_onibus);
        this.nomeEmpresa = nomeEmpresa;
    }
    
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }
    
    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
}
