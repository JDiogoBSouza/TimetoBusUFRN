package com.apps.diogo.timetobusufrn.Classes.Modelos;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Diogo on 19/10/2017.
 */

public class Post implements Serializable
{
    
    public static final String POST_INFO = "POST_INFO";
    //private static int idCount = 0;
    
    private int id;
    private int idParada;
    private String tipoOnibus;
    private int empresaOnibus;
    private String comentario;
    private Usuario usuario;
    private String hora;
    private String segundos;
    
    //private Parada parada;
    //private Onibus onibus;
    
    
    public Post(Usuario usuario, int idParada, String tipoOnibus, int empresaOnibus, String comentario)
    {
        super();
        this.idParada = idParada;
        this.tipoOnibus = tipoOnibus;
        this.empresaOnibus = empresaOnibus;
        this.comentario = comentario;
        this.usuario = usuario;
        
        Date data = new Date();
        
        this.hora = cortaHora(data);
        this.segundos = cortaSegundos(data);
    }
    
    public Post(int id, Usuario usuario, int idParada, String tipoOnibus, int empresaOnibus, String hora, String segundos, String comentario)
    {
        super();
        this.id = id;
        this.comentario = comentario;
        this.usuario = usuario;
        this.hora = hora;
        this.segundos = segundos;
        this.idParada = idParada;
        this.tipoOnibus = tipoOnibus;
        this.empresaOnibus = empresaOnibus;
    }
    
    public String getTipoOnibus() {
        return tipoOnibus;
    }
    
    public void setTipoOnibus(String onibus) {
        this.tipoOnibus = onibus;
    }
    
    public int getEmpresaOnibus() {
        return empresaOnibus;
    }
    
    public void setEmpresaOnibus(int empresaOnibus) {
        this.empresaOnibus = empresaOnibus;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getHora() {
        return hora;
    }
    
    public void setHora(String hora) {
        this.hora = hora;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSegundos() {
        return segundos;
    }
    
    public void setSegundos(String segundos) {
        this.segundos = segundos;
    }
    
    @Override
    public String toString()
    {
        String content = "Hora: " + hora + segundos + "\nCircular " + tipoOnibus + "\nComentario: " + comentario;
        return content;
    }
    
    private String cortaHora(Date data)
    {
        String hora;
    
        if( data.getHours() > 9 )
            hora = data.getHours() + ":";
        else
            hora = "0" + data.getHours() + ":";
    
        if( data.getMinutes() > 9 )
            hora += data.getMinutes();
        else
            hora += "0" + data.getMinutes();
        
        return hora;
    }
    
    public String cortaSegundos(Date data)
    {
        String segundos;
        
        if( data.getSeconds() > 9 )
            segundos = ":" + data.getSeconds();
        else
            segundos = ":0" + data.getSeconds();
        
        return segundos;
    }
    
    public int getIdParada() {
        return idParada;
    }
    
    public void setIdParada(int idParada) {
        this.idParada = idParada;
    }
    
    public String getNomeParada()
    {
        String nomeParada = "";
    
        switch(idParada)
        {
            case 0:
                nomeParada = "Terminal";
                break;
            case 1:
                nomeParada = "Fisioterapia";
                break;
            case 2:
                nomeParada = "Setor I";
                break;
            case 3:
                nomeParada = "Setor II";
                break;
            case 4:
                nomeParada = "Geologia";
                break;
            case 5:
                nomeParada = "Lab Petróleo";
                break;
            case 6:
                nomeParada = "ECT";
                break;
            case 7:
                nomeParada = "CB";
                break;
            case 8:
                nomeParada = "NUPLAN";
                break;
            case 9:
                nomeParada = "Rotatória";
                break;
            case 10:
                nomeParada = "Saída UFRN";
                break;
            case 11:
                nomeParada = "Bar de Mãe";
                break;
            case 12:
                nomeParada = "Igreja Mirassol";
                break;
            case 13:
                nomeParada = "Via Direta";
                break;
            case 14:
                nomeParada = "DEART";
                break;
            case 15:
                nomeParada = "Escola Musica";
                break;
            case 16:
                nomeParada = "Reitoria";
                break;
            case 17:
                nomeParada = "DEF";
                break;
            case 18:
                nomeParada = "DEF";
                break;
            case 19:
                nomeParada = "REITORIA";
                break;
            case 20:
                nomeParada = "CB";
                break;
            case 21:
                nomeParada = "ECT";
                break;
            case 22:
                nomeParada = "Lab Petróleo";
                break;
            case 23:
                nomeParada = "Geologia";
                break;
            case 24:
                nomeParada = "Setor II";
                break;
            case 25:
                nomeParada = "Setor I";
                break;
            case 26:
                nomeParada = "Fisioterapia";
                break;
            case 27:
                nomeParada = "Júlio Barata";
                break;
            case 28:
                nomeParada = "Residências";
                break;
            default:
                nomeParada = "Terminal";
                break;
        }
        
        return nomeParada;
    }
    
    @Override
    public boolean equals(Object o)
    {
        return ((Post)o).id == id;
    }
}
