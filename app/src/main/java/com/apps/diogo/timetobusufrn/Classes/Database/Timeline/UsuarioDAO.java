package com.apps.diogo.timetobusufrn.Classes.Database.Timeline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.diogo.timetobusufrn.Classes.Database.Geral.CriaBanco;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Usuario;

/**
 * Created by Diogo on 27/10/2017.
 */

public class UsuarioDAO
{
    private SQLiteDatabase db;
    private CriaBanco banco;
    
    public UsuarioDAO(Context context)
    {
        banco = new CriaBanco(context);
    }
    
    public String insertUsuario(Usuario user)
    {
        ContentValues valores;
        long resultado;
        
        db = banco.getWritableDatabase();
        
        valores = new ContentValues();
        
        valores.put(CriaBanco.MATRICULA, user.getMatricula());
        valores.put(CriaBanco.SENHA, user.getSenha());
        valores.put(CriaBanco.NOME, user.getNome());
        valores.put(CriaBanco.FOTO, user.getFoto());
        
        resultado = db.insert(CriaBanco.TABELA1, null, valores);
        db.close();
        
        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }
    
    public Cursor selectAllUsuarios()
    {
        Cursor cursor;
        
        String[] campos =  {banco.MATRICULA, banco.SENHA, banco.NOME, banco.FOTO};
        
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA1, campos, null, null, null, null, null, null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
        
        db.close();
        
        return cursor;
    }
    
    public Cursor selectUsuarioByMatricula(int matricula)
    {
        Cursor cursor;
        String[] campos =  {banco.MATRICULA,banco.SENHA,banco.NOME,banco.FOTO};
        
        String where = CriaBanco.MATRICULA + "=" + matricula;
        
        db = banco.getReadableDatabase();
        
        cursor = db.query(CriaBanco.TABELA1,campos,where, null, null, null, null, null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    
    public Cursor selectUsuarioNoPass(int matricula)
    {
        Cursor cursor;
        String[] campos =  {banco.MATRICULA,banco.NOME,banco.FOTO};
        
        String where = CriaBanco.MATRICULA + "=" + matricula;
        
        db = banco.getReadableDatabase();
        
        cursor = db.query(CriaBanco.TABELA1,campos,where, null, null, null, null, null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    
    public void updateUsuario(Usuario user)
    {
        ContentValues valores;
        String where;
        
        db = banco.getWritableDatabase();
        
        where = CriaBanco.MATRICULA + "=" + user.getMatricula();
        
        valores = new ContentValues();
        valores.put(CriaBanco.SENHA, user.getSenha());
        valores.put(CriaBanco.NOME, user.getNome());
        valores.put(CriaBanco.FOTO, user.getFoto());
        
        db.update(CriaBanco.TABELA1,valores,where,null);
        db.close();
    }
    
    public void deleteUsuario(int matricula)
    {
        String where = CriaBanco.MATRICULA + "=" + matricula;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.TABELA1,where,null);
        db.close();
    }
}
