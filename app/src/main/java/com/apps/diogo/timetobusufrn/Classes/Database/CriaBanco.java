package com.apps.diogo.timetobusufrn.Classes.Database;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
    
    
    public static final String TABELAEMPRESAS = "empresas";
    public static final String ID_EMPRESA = "id_empresa";
    public static final String NOME_EMPRESA = "nome_empresa";
    
    public static final String TABELATIPOS = "tipos";
    public static final String ID_TIPO = "id_tipo";
    public static final String DESCRICAO_TIPO = "descricao";
    
    public static final String TABELAONIBUS = "onibus";
    public static final String ID_ONIBUS = "id_onibus";
    public static final String ONIBUS_ID_TIPO = "id_tipo";
    public static final String ONIBUS_ID_EMPRESA = "id_empresa";
    
    public static final String TABELAHORARIOS = "horarios";
    public static final String ID_HORARIO = "id_horario";
    public static final String CHEGADAVIADIRETA = "chegadavd";
    public static final String SAIDAVIADIRETA = "saidavd";
    public static final String SAIDATERMINAL = "saidaterminal";
    public static final String CARRO = "carro";
    public static final String CHEGADARU = "chegadaru";
    public static final String CHEGADAECT = "chegadaect";
    public static final String CHEGADAREITORIA = "chegadareitoria";
    public static final String OBS = "obs";
    public static final String IDONIBUS = "idonibus";
    
    
    public static final int VERSAO = 1;
    
    private Context contexto;
    
    public CriaBanco(Context context)
    {
        super(context, NOME_BANCO,null,VERSAO);
        contexto = context;
    }
    
    public static String[] getStringsUsuario(boolean comSenha)
    {
        String[] nomeCamposUser;
        
        if( comSenha )
        {
            nomeCamposUser = new String[]{CriaBanco.MATRICULA, CriaBanco.SENHA, CriaBanco.NOME, CriaBanco.FOTO};
        }
        else
        {
            nomeCamposUser = new String[]{CriaBanco.MATRICULA, CriaBanco.NOME, CriaBanco.FOTO};
        }
            
        return nomeCamposUser;
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
        
        sql = "CREATE TABLE "+TABELAEMPRESAS+"("
                + ID_EMPRESA + " integer primary key autoincrement,"
                + NOME_EMPRESA + " text"
                +")";
        db.execSQL(sql);
    
        sql = "CREATE TABLE "+TABELATIPOS+"("
                + ID_TIPO + " integer primary key autoincrement,"
                + DESCRICAO_TIPO + " text"
                +")";
        db.execSQL(sql);
    
        sql = "CREATE TABLE "+TABELAONIBUS+"("
                + ID_ONIBUS + " integer primary key autoincrement,"
                + ONIBUS_ID_EMPRESA + " int,"
                + ONIBUS_ID_TIPO + " int,"
                + "FOREIGN KEY(" + ONIBUS_ID_EMPRESA + ") REFERENCES " + TABELAEMPRESAS + "("+ ID_EMPRESA +"),"
                + "FOREIGN KEY(" + ONIBUS_ID_TIPO + ") REFERENCES " + TABELATIPOS + "("+ ID_TIPO +")"
                +")";
        db.execSQL(sql);
    
        sql = "CREATE TABLE "+TABELAHORARIOS+"("
                + ID_HORARIO + " integer primary key autoincrement,"
                + CHEGADAVIADIRETA + " text,"
                + SAIDAVIADIRETA + " text,"
                + SAIDATERMINAL + " text,"
                + CARRO + " text,"
                + CHEGADARU + " text,"
                + CHEGADAECT + " text,"
                + CHEGADAREITORIA + " text,"
                + OBS + " text,"
                + IDONIBUS + " int,"
                + "FOREIGN KEY(" + IDONIBUS + ") REFERENCES " + TABELAONIBUS + "("+ ID_ONIBUS +")"
                +")";
        db.execSQL(sql);
    
        db.execSQL("INSERT INTO usuarios values (1111,'Diogo Souza', 'dg123456',  'enderecoFoto1')");
        db.execSQL("INSERT INTO usuarios values (2222,'Alex Lima', 'dg22221',  'enderecoFoto2')");
        db.execSQL("INSERT INTO usuarios values (3333,'Janna Silva', 'dg3333',  'enderecoFoto3')");
        db.execSQL("INSERT INTO usuarios values (4444, 'Bruno Silva', 'dg4444', 'enderecoFoto4')");
    
        db.execSQL("INSERT INTO posts values (NULL, 'Reitoria', 'Inverso', '12:45', ':44', 'Busao lotado', 2222)");
        db.execSQL("INSERT INTO posts values (NULL, 'Via Direta', 'Direto', '10:40', ':34', 'Busao Vazio', 4444)");
        db.execSQL("INSERT INTO posts values (NULL, 'Via Direta', 'Inverso', '10:00', ':34', 'Busao Vazio', 1111)");
    
        db.execSQL("INSERT INTO Empresas values (NULL,'Guanabara')");
        db.execSQL("INSERT INTO Empresas values (NULL,'Via Sul')");
        db.execSQL("INSERT INTO Empresas values (NULL,'Conceição')");
        db.execSQL("INSERT INTO Empresas values (NULL,'Cidade do Natal')");
        db.execSQL("INSERT INTO Empresas values (NULL,'Santa Maria')");
        db.execSQL("INSERT INTO Empresas values (NULL,'Reunidas')");
    
        db.execSQL("INSERT INTO Tipos values (NULL,'Direto')");
        db.execSQL("INSERT INTO Tipos values (NULL,'Inverso')");
        db.execSQL("INSERT INTO Tipos values (NULL,'Ex C&T')");
        db.execSQL("INSERT INTO Tipos values (NULL,'Ex Reitoria')");
    
        db.execSQL("INSERT INTO Onibus values (NULL, 1, 1)"); 	// 1. Direto	- Guanabara
        db.execSQL("INSERT INTO Onibus values (NULL, 2, 1)");	// 2. Direto	- Via Sul
    
        db.execSQL("INSERT INTO Onibus values (NULL, 3, 2)"); 	// 3. Inverso	- Conceição
        db.execSQL("INSERT INTO Onibus values (NULL, 4, 2)");	// 4. Inverso	- Cidade do Natal
    
        db.execSQL("INSERT INTO Onibus values (NULL, 1, 3)");	// 5. Expresso	C&T - Guanabara
    
        db.execSQL("INSERT INTO Onibus values (NULL, 5, 4)");	// 6. Expresso Reitoria - Santa Maria
        db.execSQL("INSERT INTO Onibus values (NULL, 6, 4)");	// 7. Expresso Reitoria - Reunidas
    
        // Horarios Direto
        db.execSQL("INSERT INTO Horarios values (NULL, '06:15', '06:15', NULL, 'A1', '06:30', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:35', '06:35', '06:20', 'A2', '06:50', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:55', '06:55', '06:40', 'A1', '07:10', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:15', '07:15', '07:00', 'A2', '07:25', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:35', '07:35', '07:20', 'A1', '07:50', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:45', '07:45', '07:30', 'A2', '07:55', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:15', '08:15', '08:00', 'A2', '08:25', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:45', '08:45', '08:30', 'A2', '09:00', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:15', '09:15', '09:00', 'A1', '09:25', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:45', '09:45', '09:30', 'A1', '09:55', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:15', '10:15', '10:00', 'A1', '10:25', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:25', '10:25', '10:10', 'A2', '10:35', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:45', '10:45', '10:30', 'A1', '10:55', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:15', '11:15', '11:00', 'A2', '11:27', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:35', '11:35', '11:20', 'A1', '11:47', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:45', '11:45', '11:30', 'A2', '11:57', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:05', '12:05', '11:50', 'A1', '12:20', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:25', '12:25', '12:05', 'A2', '12:40', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:45', '12:45', '12:25', 'A1', '13:00', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:05', '13:05', '12:50', 'A2', '13:20', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:25', '13:25', '13:10', 'A1', '13:40', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:45', '13:45', '13:30', 'A2', '14:00', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:15', '14:15', '14:00', 'B1', '14:30', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:45', '14:45', '14:30', 'B2', '15:00', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:15', '15:15', '15:00', 'B1', '15:30', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:45', '15:45', '15:30', 'B2', '16:00', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:25', '16:25', '16:10', 'B2', '16:40', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:55', '16:55', '16:40', 'B1', '17:10', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:35', '17:35', '17:20', 'B1', '17:50', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:55', '17:55', '17:40', 'B2', '18:10', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:15', '18:15', '18:00', 'B1', '18:30', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:35', '18:35', '18:20', 'B2', '18:50', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:55', '18:55', '18:40', 'B1', '19:10', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:15', '19:15', '19:00', 'B2', '19:25', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:35', '19:35', '19:20', 'B1', '19:45', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:05', '20:05', '19:50', 'B2', '20:15', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:25', '20:25', '20:10', 'B1', '20:35', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:45', '20:45', '20:30', 'B2', '20:55', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:55', '20:55', '20:40', 'B1', '21:05', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:25', '21:25', '21:10', 'B2', '21:40', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:45', '21:45', '21:30', 'B1', '21:55', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:05', '22:05', '21:45', 'B2', '22:15', NULL, NULL, NULL, 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:15', '22:15', '22:00', 'B1', '22:40', NULL, NULL, NULL, 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:35', '22:35', '22:20', 'B2', '23:00', NULL, NULL, NULL, 2)");

// Horarios Inverso
        db.execSQL("INSERT INTO Horarios values (NULL, '06:30', '06:30', '00:00', 'A1', '06:45', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:40', '06:40', '06:30', 'A2', '07:00', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:00', '07:00', '06:50', 'A1', '07:20', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:20', '07:20', '07:10', 'A2', '07:40', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:40', '07:40', '07:30', 'A1', '08:00', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:00', '08:00', '07:50', 'A2', '08:20', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:20', '08:20', '08:10', 'A1', '08:40', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:50', '08:50', '08:40', 'A2', '09:10', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:20', '09:20', '09:10', 'A2', '09:40', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:50', '09:50', '09:40', 'A1', '10:05', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:20', '10:20', '10:10', 'A1', '10:40', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:50', '10:50', '10:40', 'A2', '11:10', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:10', '11:10', '11:00', 'A1', '11:30', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:30', '11:30', '11:20', 'A2', '11:50', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:50', '11:50', '11:40', 'A1', '12:10', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:10', '12:10', '12:00', 'A2', '12:30', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:30', '12:30', '12:15', 'A1', '12:45', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:50', '12:50', '12:35', 'A2', '13:05', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:00', '13:00', '12:50', 'A1', '13:20', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:20', '13:20', '13:10', 'A2', '13:37', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:40', '13:40', '13:30', 'A1', '14:00', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:50', '13:50', '13:40', 'A2', '14:10', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:30', '14:30', '14:20', 'B1', '14:50', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:50', '14:50', '14:40', 'B2', '15:10', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:10', '15:10', '15:00', 'B1', '15:30', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:40', '15:40', '15:30', 'B2', '16:00', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:10', '16:10', '16:00', 'B1', '16:30', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:40', '16:40', '16:30', 'B2', '17:00', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:00', '17:00', '16:50', 'B1', '17:20', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:20', '17:20', '17:10', 'B2', '17:40', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:40', '17:40', '17:30', 'B1', '18:00', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:00', '18:00', '17:50', 'B2', '18:20', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:20', '18:20', '18:10', 'B1', '18:40', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:40', '18:40', '18:30', 'B2', '19:00', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:00', '19:00', '18:50', 'B1', '19:20', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:40', '19:40', '19:30', 'B1', '20:00', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:20', '20:20', '20:10', 'B2', '20:35', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:50', '20:50', '20:40', 'B2', '21:10', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:20', '21:20', '21:10', 'B1', '21:35', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:30', '21:30', '21:20', 'B2', '21:45', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:50', '21:50', '21:40', 'B1', '22:05', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:05', '22:05', '21:50', 'B2', '22:20', NULL, NULL, NULL, 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:20', '22:20', '22:10', 'B1', '22:35', NULL, NULL, NULL, 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:30', '22:30', '22:20', 'B2', '00:00', NULL, NULL, NULL, 4)");

// Horarios Expresso C&T
        db.execSQL("INSERT INTO Horarios values (NULL, '06:50', '06:35', NULL, 'A1', NULL,'06:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:00', '06:45', NULL, 'A2', NULL,'06:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:10', '06:55', NULL, 'A1', NULL,'07:00', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:20', '07:05', NULL, 'A2', NULL,'07:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:30', '07:15', NULL, 'A1', NULL,'07:20', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:40', '07:25', NULL, 'A2', NULL,'07:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:00', '07:45', NULL, 'A1', NULL,'07:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:20', '08:05', NULL, 'A2', NULL,'08:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:30', '08:15', NULL, 'A1', NULL,'08:20', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:40', '08:25', NULL, 'A2', NULL,'08:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:50', '08:35', NULL, 'A1', NULL,'08:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:00', '08:45', NULL, 'A2', NULL,'08:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '08:55', NULL, 'A1','09:10', '09:00', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:20', '09:05', NULL, 'A2', NULL,'09:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:40', '09:25', NULL, 'A2', NULL,'09:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:00', '09:45', NULL, 'A2', NULL,'09:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '10:05', NULL, 'A2','10:20', '10:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:30', NULL, '10:15', 'A1', NULL,'10:20', NULL, NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:50', '10:35', NULL, 'A1', NULL,'10:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:10', '10:55', NULL, 'A1', NULL,'11:00', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '11:15', NULL, 'A1','11:30', '11:20', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:40', NULL, '11:25', 'A2', NULL,'11:30', NULL, NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:00', '11:45', NULL, 'A2', NULL,'11:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:10', NULL, '11:55', 'A1', NULL,'12:00', NULL, NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:20', '12:05', NULL, 'A2', NULL,'12:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:30', '12:15', NULL, 'A1', NULL,'12:20', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '12:25', NULL, 'A2','12:40', '12:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:50', '12:35', NULL, 'A1', NULL,'12:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:00', NULL, '12:45', 'A2', NULL,'12:50', NULL, NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '12:55', NULL, 'A1','13:10', '13:00', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:20', '13:05', NULL, 'A2', NULL,'13:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:30', NULL, '13:15', 'A1', NULL,'13:20', NULL, NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:40', '13:25', NULL, 'A2', NULL,'13:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:00', '13:45', NULL, 'A1', NULL,'13:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:20', '14:05', NULL, 'A2', NULL,'14:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:40', '14:25', NULL, 'B1', NULL,'14:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:50', '14:35', NULL, 'B2', NULL,'14:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:00', '14:45', NULL, 'B1', NULL,'14:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:20', '15:05', NULL, 'B2', NULL,'15:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:40', '15:25', NULL, 'B1', NULL,'15:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '15:35', NULL, 'B2','15:50', '15:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:00', '15:45', NULL, 'B1', NULL,'15:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:20', '16:05', NULL, 'B1', NULL,'16:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:40', '16:25', NULL, 'B1', NULL,'16:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:00', '16:45', NULL, 'B1', NULL,'16:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:10', NULL, '16:55', 'B2', NULL,'17:00', NULL, NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:20', '17:05', NULL, 'B1', NULL,'17:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:30', '17:15', NULL, 'B2', NULL,'17:20', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:40', '17:25', NULL, 'B1', NULL,'17:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:50', '17:35', NULL, 'B2', NULL,'17:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:00', '17:45', NULL, 'B1', NULL,'17:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:10', '17:55', NULL, 'B2', NULL,'18:00', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:20', '18:05', NULL, 'B1', NULL,'18:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:30', '18:15', NULL, 'B2', NULL,'18:20', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:40', '18:25', NULL, 'B1', NULL,'18:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:50', '18:35', NULL, 'B2', NULL,'18:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:00', '18:45', NULL, 'B1', NULL,'18:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '18:55', NULL, 'B2','19:10', '19:00', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:20', '19:05', NULL, 'B1', NULL,'19:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '19:25', NULL, 'B1','19:40', '19:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:50', NULL, '19:35', 'B2', NULL,'19:40', NULL, NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:10', '19:55', NULL, 'B2', NULL,'20:00', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:30', '20:15', NULL, 'B2', NULL,'20:20', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:50', '20:35', NULL, 'B2', NULL,'20:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:00', NULL, '20:45', 'B1', NULL,'20:50', NULL, NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:10', '20:55', NULL, 'B2', NULL,'21:00', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:20', '21:05', NULL, 'B1', NULL,'21:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:30', '21:15', NULL, 'B2', NULL,'21:20', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:40', '21:25', NULL, 'B1', NULL,'21:30', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:50', '21:35', NULL, 'B2', NULL,'21:40', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:00', '21:45', NULL, 'B1', NULL,'21:50', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:10', '21:55', NULL, 'B2', NULL,'22:00', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:20', '22:05', NULL, 'B1', NULL,'22:10', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:30', '22:15', NULL, 'B2', NULL,'22:20', NULL, NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:40', '22:25', NULL, 'B1', NULL,'22:30', NULL, NULL, 5)");

// Horarios Expresso Reitoria
        db.execSQL("INSERT INTO Horarios values (NULL, '06:45', '06:30', NULL, 'A1', NULL, NULL, '06:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:55', '06:40', NULL, 'A2', NULL, NULL, '06:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:05', '06:50', NULL, 'A1', NULL, NULL, '06:55', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:15', '07:00', NULL, 'A2', NULL, NULL, '07:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:25', '07:10', NULL, 'A1', NULL, NULL, '07:15', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:35', '07:20', NULL, 'A2', NULL, NULL, '07:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:45', '07:30', NULL, 'A1', NULL, NULL, '07:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:55', '07:40', NULL, 'A2', NULL, NULL, '07:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:05', '07:50', NULL, 'A1', NULL, NULL, '07:55', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:15', '08:00', NULL, 'A2', NULL, NULL, '08:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:25', '08:10', NULL, 'A1', NULL, NULL, '08:15', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:35', '08:20', NULL, 'A2', NULL, NULL, '08:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:45', '08:30', NULL, 'A1', NULL, NULL, '08:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:55', '08:40', NULL, 'A2', NULL, NULL, '08:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '08:50', NULL, 'A1','09:00', NULL, '08:55', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:15', '09:00', NULL, 'A2', NULL, NULL, '09:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:35', '09:20', NULL, 'A2', NULL, NULL, '09:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:55', '09:40', NULL, 'A2', NULL, NULL, '09:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '10:00', NULL, 'A2','10:10', NULL, '10:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:10', NULL, '10:00', 'A1', NULL, NULL, '10:05', NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:35', '10:20', NULL, 'A1', NULL, NULL, '10:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:55', '10:40', NULL, 'A1', NULL, NULL, '10:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:15', '11:00', NULL, 'A1', NULL, NULL, '11:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:30', NULL, '11:20', 'A2', NULL, NULL, '11:25', NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '11:20', NULL, 'A1','11:30', NULL, '11:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:55', '11:40', NULL, 'A2', NULL, NULL, '11:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:05', NULL, '11:55', 'A1', NULL, NULL, '12:00', NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:15', '12:00', NULL, 'A2', NULL, NULL, '12:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '12:10', NULL, 'A1','12:20', NULL, '12:15', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:35', '12:20', NULL, 'A2', NULL, NULL, '12:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:40', NULL, '12:30', 'A1', NULL, NULL, '12:35', NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:55', '12:40', NULL, 'A2', NULL, NULL, '12:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '12:50', NULL, 'A1','13:00', NULL, '12:55', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:15', '13:00', NULL, 'A2', NULL, NULL, '13:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:20', NULL, '13:10', 'A1', NULL, NULL, '13:15', NULL,5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:35', '13:20', NULL, 'A2', NULL, NULL, '13:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:45', '13:30', NULL, 'A1', NULL, NULL, '13:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:55', '13:40', NULL, 'A2', NULL, NULL, '13:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:05', '13:50', NULL, 'A1', NULL, NULL, '13:55', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:15', '14:00', NULL, 'A2', NULL, NULL, '14:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:35', '14:20', NULL, 'A1', NULL, NULL, '14:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:55', '14:40', NULL, 'A2', NULL, NULL, '14:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:15', '15:00', NULL, 'B1', NULL, NULL, '15:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:25', '15:10', NULL, 'B2', NULL, NULL, '15:15', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:35', '15:20', NULL, 'B1', NULL, NULL, '15:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:45', '15:30', NULL, 'B2', NULL, NULL, '15:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:05', '15:50', NULL, 'B1', NULL, NULL, '15:55', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:25', '16:10', NULL, 'B2', NULL, NULL, '16:15', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:45', '16:30', NULL, 'B1', NULL, NULL, '16:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:55', '16:40', NULL, 'B2', NULL, NULL, '16:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:05', '16:50', NULL, 'B1', NULL, NULL, '16:55', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:15', '17:00', NULL, 'B2', NULL, NULL, '17:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:25', '17:10', NULL, 'B1', NULL, NULL, '17:15', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:35', '17:20', NULL, 'B2', NULL, NULL, '17:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:45', '17:30', NULL, 'B1', NULL, NULL, '17:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:55', '17:40', NULL, 'B2', NULL, NULL, '17:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:05', '17:50', NULL, 'B1', NULL, NULL, '17:55', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:15', '18:00', NULL, 'B2', NULL, NULL, '18:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:25', '18:10', NULL, 'B1', NULL, NULL, '18:15', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:35', '18:20', NULL, 'B2', NULL, NULL, '18:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:45', '18:30', NULL, 'B1', NULL, NULL, '18:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:55', '18:40', NULL, 'B2', NULL, NULL, '18:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:05', '18:50', NULL, 'B1', NULL, NULL, '18:55', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:15', '19:00', NULL, 'B2', NULL, NULL, '19:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:35', '19:20', NULL, 'B1', NULL, NULL, '19:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:45', '19:30', NULL, 'B2', NULL, NULL, '19:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:55', '19:40', NULL, 'B1', NULL, NULL, '19:45', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:15', '20:00', NULL, 'B2', NULL, NULL, '20:05', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:25', '20:10', NULL, 'B1', NULL, NULL, '20:15', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:35', '20:20', NULL, 'B2', NULL, NULL, '20:25', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:45', '20:30', NULL, 'B1', NULL, NULL, '20:35', NULL, 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:55', '20:40', NULL, 'B2', NULL, NULL, '20:45', NULL, 5)");
    
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA1);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA2);
        onCreate(db);
    }
}

