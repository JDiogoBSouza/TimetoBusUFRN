package com.apps.diogo.timetobusufrn.Classes.Database.Timeline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.diogo.timetobusufrn.Classes.Database.Geral.CriaBanco;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;

/**
 * Created by Diogo on 27/10/2017.
 */

public class PostDAO
{
    private SQLiteDatabase db;
    private CriaBanco banco;
    
    public PostDAO(Context context)
    {
        banco = new CriaBanco(context);
    }
    
    public long insertPost(Post post)
    {
        ContentValues valores;
        long resultado;
        
        db = banco.getWritableDatabase();
        
        valores = new ContentValues();
        
        //valores.put(CriaBanco.ID, post.getId());
        valores.put(CriaBanco.PARADA, post.getParada());
        valores.put(CriaBanco.TIPOONIBUS, post.getTipoOnibus());
        valores.put(CriaBanco.EMPRESAONIBUS, post.getEmpresaOnibus());
        valores.put(CriaBanco.HORA, post.getHora());
        valores.put(CriaBanco.SEGUNDOS, post.getSegundos());
        valores.put(CriaBanco.COMENTARIO, post.getComentario());
        valores.put(CriaBanco.MATRIUSUARIO, post.getUsuario().getMatricula());
        
        resultado = db.insert(CriaBanco.TABELA2, null, valores);
        
        db.close();
        
        return resultado;
    }
    
    public Cursor selectAllPosts()
    {
        Cursor cursor;
        
        String[] campos =  {banco.ID, banco.PARADA, banco.TIPOONIBUS, banco.EMPRESAONIBUS, banco.HORA, banco.SEGUNDOS, banco.COMENTARIO, banco.MATRIUSUARIO};
        
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA2, campos, null, null, null, null, banco.HORA + " asc " , null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
        
        db.close();
        
        return cursor;
    }
    
    public Cursor selectPostByID(int id)
    {
        Cursor cursor;
        String[] campos =  {banco.ID, banco.PARADA, banco.TIPOONIBUS, banco.EMPRESAONIBUS, banco.HORA, banco.SEGUNDOS, banco.COMENTARIO};
        
        String where = CriaBanco.ID + "=" + id;
        
        db = banco.getReadableDatabase();
        
        cursor = db.query(CriaBanco.TABELA2,campos,where, null, null, null, null, null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    
    public void updatePost(Post post)
    {
        ContentValues valores;
        String where;
        
        db = banco.getWritableDatabase();
        
        where = CriaBanco.ID + "=" + post.getId();
        
        valores = new ContentValues();
        valores.put(CriaBanco.PARADA, post.getParada());
        valores.put(CriaBanco.TIPOONIBUS, post.getTipoOnibus());
        valores.put(CriaBanco.EMPRESAONIBUS, post.getEmpresaOnibus());
        valores.put(CriaBanco.HORA, post.getHora());
        valores.put(CriaBanco.SEGUNDOS, post.getSegundos());
        valores.put(CriaBanco.COMENTARIO, post.getComentario());
        
        db.update(CriaBanco.TABELA2,valores,where,null);
        db.close();
    }
    
    public void deletePost(int id)
    {
        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.TABELA2,where,null);
        db.close();
    }
}
