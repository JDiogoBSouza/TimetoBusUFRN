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
    private String parada;
    private String onibus;
    private String comentario;
    private Usuario usuario;
    private String hora;
    private String segundos;
    
    //private Parada parada;
    //private Onibus onibus;
    
    
    public Post(Usuario usuario, String parada, String onibus, String comentario)
    {
        super();
        //this.id = id;
        this.parada = parada;
        this.onibus = onibus;
        this.comentario = comentario;
        this.usuario = usuario;
        
        Date data = new Date();
        
        this.hora = cortaHora(data);
        this.segundos = cortaSegundos(data);
        //id = ++idCount;
    }
    
    public Post(Usuario usuario, String parada, String onibus, String hora, String segundos, String comentario)
    {
        super();
        this.comentario = comentario;
        this.usuario = usuario;
        this.hora = hora;
        this.segundos = segundos;
        this.parada = parada;
        this.onibus = onibus;
    }
    
    public String getParada() {
        return parada;
    }
    
    public void setParada(String parada) {
        this.parada = parada;
    }
    
    public String getOnibus() {
        return onibus;
    }
    
    public void setOnibus(String onibus) {
        this.onibus = onibus;
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
        String content = usuario + "\n" + comentario + "\n" + hora + segundos;
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
    
    @Override
    public boolean equals(Object o)
    {
        return ((Post)o).id == id;
    }
}