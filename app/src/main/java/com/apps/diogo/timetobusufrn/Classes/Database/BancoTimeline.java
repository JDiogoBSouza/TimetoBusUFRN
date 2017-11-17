package com.apps.diogo.timetobusufrn.Classes.Database;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Diogo on 27/10/2017.
 */

public class BancoTimeline extends SQLiteOpenHelper
{
    public static final String NOME_BANCO = "bancoTimeline.db";
    
    public static final String TABELA1 = "usuarios";
    public static final String MATRICULA = "matricula";
    public static final String NOME = "nome";
    public static final String SENHA = "senha";
    public static final String FOTO = "foto";
    
    public static final String TABELA2 = "posts";
    public static final String ID = "id";
    public static final String PARADA = "parada";
    public static final String TIPOONIBUS = "tipoonibus";
    public static final String EMPRESAONIBUS = "empresaonibus";
    public static final String HORA = "hora";
    public static final String SEGUNDOS = "segundos";
    public static final String COMENTARIO = "comentario";
    public static final String MATRIUSUARIO = "matriusuario";
    
    public static final int VERSAO = 2;
    
    private Context contexto;
    
    public BancoTimeline(Context context)
    {
        super(context, NOME_BANCO,null,VERSAO);
        contexto = context;
    }
    
    /**
     * @param comSenha : Identificar se deseja adicionar o campo senha, ou não.
     * @return : Vetor de String com os campos da tabela usuário.
     */
    public static String[] getStringsUsuario(boolean comSenha)
    {
        String[] nomeCamposUser;
        
        if( comSenha )
        {
            nomeCamposUser = new String[]{MATRICULA, SENHA, NOME, FOTO};
        }
        else
        {
            nomeCamposUser = new String[]{MATRICULA, NOME, FOTO};
        }
            
        return nomeCamposUser;
    }
    
    /**
     * Cria as tabelas no banco de dados da aplicação.
     * @param db : Banco de dados da aplicação.
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE "+TABELA1+"("
                + MATRICULA + " integer primary key,"
                + NOME + " text,"
                + SENHA + " text,"
                + FOTO + " blob"
                +")";
    
        db.execSQL(sql);
        
        sql = "CREATE TABLE "+TABELA2+"("
            + ID + " integer primary key autoincrement,"
            + PARADA + " int,"
            + TIPOONIBUS + " text,"
            + EMPRESAONIBUS + " int,"
            + HORA + " text,"
            + SEGUNDOS + " text,"
            + COMENTARIO + " text,"
            + MATRIUSUARIO + " integer,"
            + "FOREIGN KEY(" + MATRIUSUARIO + ") REFERENCES " + TABELA1 + "("+ MATRICULA +")"
            +")";
        db.execSQL(sql);
        
        inserirDadosTeste(db);
    }
    
    /**
     * Método para atualizar o banco de dados caso seja necessário.
     * @param db : Banco de dados da aplicação.
     * @param oldVersion : Versão anterior da aplicação.
     * @param newVersion : Versão atual da aplicação.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // No Momento só existe uma versão do banco no momento.
        int i = 0;
        switch( oldVersion )
        {
            case 1:
                // Comandos para mudar da versão 1 para a 2.
                
            case 2:
                // Comandos para mudar da versão 2 para a 3.
            break;
        }
    }
    
    /**
     * Método para dar um downgrade no banco de dados caso seja necessário.
     * @param db : Banco de dados da aplicação.
     * @param oldVersion : Versão anterior da aplicação.
     * @param newVersion : Versão atual da aplicação.
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // ler SQL da pasta raw
        switch( oldVersion )
        {
            case 2:
                // Comandos para mudar da versão 2 para a 1.
            break;
        }
    }
    
    /**
     * Insere os dados de teste no banco de dados da aplicação.
     * @param db : Banco de dados da aplicação
     */
    public void inserirDadosTeste(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO usuarios values (1111,'Diogo Souza', 'dg123456',  NULL)");
        db.execSQL("INSERT INTO usuarios values (2222,'Alex Lima', 'dg22221',  NULL)");
        db.execSQL("INSERT INTO usuarios values (3333,'Janna Silva', 'dg3333',  NULL)");
        db.execSQL("INSERT INTO usuarios values (4444, 'Bruno Silva', 'dg4444', NULL)");
    
        db.execSQL("INSERT INTO posts values (NULL, 'Reitoria', 'Inverso',2 , '12:45', ':44', 'Busao lotado', 2222)");
        db.execSQL("INSERT INTO posts values (NULL, 'Via Direta', 'Direto',0 , '10:40', ':34', 'Busao Vazio', 4444)");
        db.execSQL("INSERT INTO posts values (NULL, 'Via Direta', 'Inverso',1 , '10:00', ':34', 'Busao Vazio', 1111)");
    }
}

