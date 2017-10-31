package com.apps.diogo.timetobusufrn.Classes.Database;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.apps.diogo.timetobusufrn.Classes.Usuario;
import com.apps.diogo.timetobusufrn.Classes.Post;

/**
 * Created by Diogo on 27/10/2017.
 */

public class CriaBanco extends SQLiteOpenHelper
{
    public static final String NOME_BANCO = "banco.db";
    
    public static final String TABELA1 = "usuarios";
    public static final String MATRICULA = "matricula";
    public static final String NOME = "nome";
    public static final String SENHA = "senha";
    public static final String FOTO = "foto";
    
    public static final String TABELA2 = "posts";
    public static final String ID = "id";
    public static final String PARADA = "parada";
    public static final String ONIBUS = "onibus";
    public static final String HORA = "hora";
    public static final String SEGUNDOS = "segundos";
    public static final String COMENTARIO = "comentario";
    public static final String MATRIUSUARIO = "matriusuario";
    
    public static final int VERSAO = 1;
    
    private Context contexto;
    
    public CriaBanco(Context context)
    {
        super(context, NOME_BANCO,null,VERSAO);
        contexto = context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE "+TABELA1+"("
                + MATRICULA + " integer primary key,"
                + NOME + " text,"
                + SENHA + " text,"
                + FOTO + " text"
                +")";
    
        db.execSQL(sql);
        
        sql = "CREATE TABLE "+TABELA2+"("
            + ID + " integer primary key autoincrement,"
            + PARADA + " int,"
            + ONIBUS + " text,"
            + HORA + " text,"
            + SEGUNDOS + " text,"
            + COMENTARIO + " text,"
            + MATRIUSUARIO + " integer,"
            + "FOREIGN KEY(" + MATRIUSUARIO + ") REFERENCES " + TABELA1 + "("+ MATRICULA +")"
            +")";
    
        db.execSQL(sql);
    
        sql = "INSERT INTO usuarios values (1111,'Diogo Souza', 'dg123456',  'enderecoFoto1')";
        db.execSQL(sql);
        sql = "INSERT INTO usuarios values (2222,'Alex Lima', 'dg22221',  'enderecoFoto2')";
        db.execSQL(sql);
        sql = "INSERT INTO usuarios values (3333,'Janna Silva', 'dg3333',  'enderecoFoto3')";
        db.execSQL(sql);
        sql = "INSERT INTO usuarios values (4444, 'Bruno Silva', 'dg4444', 'enderecoFoto4')";
        db.execSQL(sql);
        
        sql = "INSERT INTO posts values (NULL, 'Reitoria', 'Inverso', '12:45', ':44', 'Busao lotado', 2222)";
        db.execSQL(sql);
        sql = "INSERT INTO posts values (NULL, 'Via Direta', 'Direto', '10:40', ':34', 'Busao Vazio', 4444)";
        db.execSQL(sql);
        sql = "INSERT INTO posts values (NULL, 'Via Direta', 'Inverso', '10:00', ':34', 'Busao Vazio', 1111)";
        db.execSQL(sql);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA1);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA2);
        onCreate(db);
    }
}

