package com.apps.diogo.timetobusufrn.Classes.Database.Geral;

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
    public static final String SAIDA = "saida";
    public static final String DESTINO = "destino";
    public static final String CHEGADA = "chegada";
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
            nomeCamposUser = new String[]{MATRICULA, SENHA, NOME, FOTO};
        }
        else
        {
            nomeCamposUser = new String[]{MATRICULA, NOME, FOTO};
        }
            
        return nomeCamposUser;
    }
    
    public static String[] getStringsHorarios()
    {
        return new String[]{ ID_HORARIO, SAIDA, DESTINO, CHEGADA, IDONIBUS};
    }
    
    public static String[] getStringsOnibus()
    {
        return new String[]{ ID_ONIBUS, ONIBUS_ID_EMPRESA, ONIBUS_ID_TIPO };
    }
    
    
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
                + SAIDA + " text,"
                + DESTINO + " text,"
                + CHEGADA + " text,"
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
        db.execSQL("INSERT INTO Tipos values (NULL,'Ex C&T - RUVD')");
        db.execSQL("INSERT INTO Tipos values (NULL,'Ex C&T - VDRU')");
        db.execSQL("INSERT INTO Tipos values (NULL,'Ex Reitoria')");
        db.execSQL("INSERT INTO Tipos values (NULL,'Ex Reitoria - RUVD')");
        db.execSQL("INSERT INTO Tipos values (NULL,'Ex Reitoria - VDRU')");
    
        db.execSQL("INSERT INTO Onibus values (NULL, 1, 1)"); 	// 1. Direto	- Guanabara
        db.execSQL("INSERT INTO Onibus values (NULL, 2, 1)");	// 2. Direto	- Via Sul
    
        db.execSQL("INSERT INTO Onibus values (NULL, 3, 2)"); 	// 3. Inverso	- Conceição
        db.execSQL("INSERT INTO Onibus values (NULL, 4, 2)");	// 4. Inverso	- Cidade do Natal
    
        db.execSQL("INSERT INTO Onibus values (NULL, 1, 3)");	// 5. Expresso	C&T - Guanabara
        db.execSQL("INSERT INTO Onibus values (NULL, 1, 4)");	// 6. Expresso	C&T - Guanabara
        db.execSQL("INSERT INTO Onibus values (NULL, 1, 5)");	// 7. Expresso	C&T - Guanabara
    
        db.execSQL("INSERT INTO Onibus values (NULL, 5, 6)");	// 8. Expresso Reitoria - Santa Maria
        db.execSQL("INSERT INTO Onibus values (NULL, 5, 7)");	// 9. Expresso Reitoria - Santa Maria
        db.execSQL("INSERT INTO Onibus values (NULL, 5, 8)");	// 10. Expresso Reitoria - Santa Maria
    
        db.execSQL("INSERT INTO Onibus values (NULL, 6, 6)");	// 11. Expresso Reitoria - Reunidas
        db.execSQL("INSERT INTO Onibus values (NULL, 6, 7)");	// 12. Expresso Reitoria - Reunidas
        db.execSQL("INSERT INTO Onibus values (NULL, 6, 8)");	// 13. Expresso Reitoria - Reunidas


        // Horarios Direto
        db.execSQL("INSERT INTO Horarios values (NULL, NULL, '06:15', '06:30', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:20', '06:35', '06:50', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:40', '06:55', '07:10', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:00', '07:15', '07:25', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:20', '07:35', '07:50', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:30', '07:45', '07:55', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:00', '08:15', '08:25', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:30', '08:45', '09:00', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:00', '09:15', '09:25', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:30', '09:45', '09:55', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:00', '10:15', '10:25', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:10', '10:25', '10:35', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:30', '10:45', '10:55', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:00', '11:15', '11:27', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:20', '11:35', '11:47', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:30', '11:45', '11:57', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:50', '12:05', '12:20', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:05', '12:25', '12:40', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:25', '12:45', '13:00', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:50', '13:05', '13:20', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:10', '13:25', '13:40', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:30', '13:45', '14:00', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:00', '14:15', '14:30', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:30', '14:45', '15:00', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:00', '15:15', '15:30', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:30', '15:45', '16:00', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:10', '16:25', '16:40', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:40', '16:55', '17:10', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:20', '17:35', '17:50', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:40', '17:55', '18:10', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:00', '18:15', '18:30', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:20', '18:35', '18:50', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:40', '18:55', '19:10', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:00', '19:15', '19:25', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:20', '19:35', '19:45', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:50', '20:05', '20:15', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:10', '20:25', '20:35', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:30', '20:45', '20:55', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:40', '20:55', '21:05', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:10', '21:25', '21:40', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:30', '21:45', '21:55', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:45', '22:05', '22:15', 2)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:00', '22:15', '22:40', 1)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:20', '22:35', '23:00', 2)");

        // Horarios Inverso
        db.execSQL("INSERT INTO Horarios values (NULL, '00:00', '06:30', '06:45', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:30', '06:40', '07:00', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:50', '07:00', '07:20', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:10', '07:20', '07:40', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:30', '07:40', '08:00', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:50', '08:00', '08:20', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:10', '08:20', '08:40', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:40', '08:50', '09:10', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:10', '09:20', '09:40', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:40', '09:50', '10:05', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:10', '10:20', '10:40', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:40', '10:50', '11:10', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:00', '11:10', '11:30', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:20', '11:30', '11:50', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:40', '11:50', '12:10', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:00', '12:10', '12:30', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:15', '12:30', '12:45', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:35', '12:50', '13:05', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:50', '13:00', '13:20', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:10', '13:20', '13:37', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:30', '13:40', '14:00', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:40', '13:50', '14:10', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:20', '14:30', '14:50', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:40', '14:50', '15:10', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:00', '15:10', '15:30', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:30', '15:40', '16:00', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:00', '16:10', '16:30', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:30', '16:40', '17:00', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:50', '17:00', '17:20', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:10', '17:20', '17:40', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:30', '17:40', '18:00', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:50', '18:00', '18:20', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:10', '18:20', '18:40', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:30', '18:40', '19:00', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:50', '19:00', '19:20', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:30', '19:40', '20:00', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:10', '20:20', '20:35', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:40', '20:50', '21:10', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:10', '21:20', '21:35', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:20', '21:30', '21:45', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:40', '21:50', '22:05', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:50', '22:05', '22:20', 4)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:10', '22:20', '22:35', 3)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:20', '22:30', '00:00', 4)");

        // Horarios Expresso C&T
        db.execSQL("INSERT INTO Horarios values (NULL, '06:35', '06:40', '06:50', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:45', '06:50', '07:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:55', '07:00', '07:10', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:05', '07:10', '07:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:15', '07:20', '07:30', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:25', '07:30', '07:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:45', '07:50', '08:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:05', '08:10', '08:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:15', '08:20', '08:30', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:25', '08:30', '08:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:35', '08:40', '08:50', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:45', '08:50', '09:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:55', '09:00', '09:10', 6)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:05', '09:10', '09:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:25', '09:30', '09:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:45', '09:50', '10:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:05', '10:10', '10:20', 6)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:15', '10:20', '10:30', 7)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:35', '10:40', '10:50', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:55', '11:00', '11:10', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:15', '11:20', '11:30', 6)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:25', '11:30', '11:40', 7)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:45', '11:50', '12:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:55', '12:00', '12:10', 7)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:05', '12:10', '12:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:15', '12:20', '12:30', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:25', '12:30', '12:40', 6)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:35', '12:40', '12:50', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:45', '12:50', '13:00', 7)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:55', '13:00', '13:10', 6)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:05', '13:10', '13:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:15', '13:20', '13:30', 7)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:25', '13:30', '13:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:45', '13:50', '14:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:05', '14:10', '14:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:25', '14:30', '14:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:35', '14:40', '14:50', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:45', '14:50', '15:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:05', '15:10', '15:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:25', '15:30', '15:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:35', '15:40', '15:50', 6)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:45', '15:50', '16:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:05', '16:10', '16:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:25', '16:30', '16:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:45', '16:50', '17:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:55', '17:00', '17:10', 7)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:05', '17:10', '17:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:15', '17:20', '17:30', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:25', '17:30', '17:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:35', '17:40', '17:50', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:45', '17:50', '18:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:55', '18:00', '18:10', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:05', '18:10', '18:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:15', '18:20', '18:30', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:25', '18:30', '18:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:35', '18:40', '18:50', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:45', '18:50', '19:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:55', '19:00', '19:10', 6)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:05', '19:10', '19:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:25', '19:30', '19:40', 6)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:35', '19:40', '19:50', 7)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:55', '20:00', '20:10', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:15', '20:20', '20:30', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:35', '20:40', '20:50', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:45', '20:50', '21:00', 7)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:55', '21:00', '21:10', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:05', '21:10', '21:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:15', '21:20', '21:30', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:25', '21:30', '21:40', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:35', '21:40', '21:50', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:45', '21:50', '22:00', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '21:55', '22:00', '22:10', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:05', '22:10', '22:20', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:15', '22:20', '22:30', 5)");
        db.execSQL("INSERT INTO Horarios values (NULL, '22:25', '22:30', '22:40', 5)");

        // Horarios Expresso Reitoria
        db.execSQL("INSERT INTO Horarios values (NULL, '06:30', '06:35', '06:45', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:40', '06:45', '06:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '06:50', '06:55', '07:05', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:00', '07:05', '07:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:10', '07:15', '07:25', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:20', '07:25', '07:35', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:30', '07:35', '07:45', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:40', '07:45', '07:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '07:50', '07:55', '08:05', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:00', '08:05', '08:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:10', '08:15', '08:25', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:20', '08:25', '08:35', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:30', '08:35', '08:45', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:40', '08:45', '08:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '08:50', '08:55','09:00', 9)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:00', '09:05', '09:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:20', '09:25', '09:35', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '09:40', '09:45', '09:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:00', '10:05','10:10', 12)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:00', '10:05', '10:10', 10)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:20', '10:25', '10:35', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '10:40', '10:45', '10:55', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:00', '11:05', '11:15', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:20', '11:25', '11:30', 13)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:20', '11:25','11:30', 9)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:40', '11:45', '11:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '11:55', '12:00', '12:05', 10)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:00', '12:05', '12:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:10', '12:15','12:20', 9)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:20', '12:25', '12:35', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:30', '12:35', '12:40', 10)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:40', '12:45', '12:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '12:50', '12:55','13:00', 9)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:00', '13:05', '13:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:10', '13:15', '13:20', 10)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:20', '13:25', '13:35', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:30', '13:35', '13:45', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:40', '13:45', '13:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '13:50', '13:55', '14:05', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:00', '14:05', '14:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:20', '14:25', '14:35', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '14:40', '14:45', '14:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:00', '15:05', '15:15', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:10', '15:15', '15:25', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:20', '15:25', '15:35', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:30', '15:35', '15:45', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '15:50', '15:55', '16:05', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:10', '16:15', '16:25', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:30', '16:35', '16:45', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:40', '16:45', '16:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '16:50', '16:55', '17:05', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:00', '17:05', '17:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:10', '17:15', '17:25', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:20', '17:25', '17:35', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:30', '17:35', '17:45', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:40', '17:45', '17:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '17:50', '17:55', '18:05', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:00', '18:05', '18:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:10', '18:15', '18:25', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:20', '18:25', '18:35', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:30', '18:35', '18:45', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:40', '18:45', '18:55', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '18:50', '18:55', '19:05', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:00', '19:05', '19:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:20', '19:25', '19:35', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:30', '19:35', '19:45', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '19:40', '19:45', '19:55', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:00', '20:05', '20:15', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:10', '20:15', '20:25', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:20', '20:25', '20:35', 11)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:30', '20:35', '20:45', 8)");
        db.execSQL("INSERT INTO Horarios values (NULL, '20:40', '20:45', '20:55', 11)");
    
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA1);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA2);
        onCreate(db);
    }
}

