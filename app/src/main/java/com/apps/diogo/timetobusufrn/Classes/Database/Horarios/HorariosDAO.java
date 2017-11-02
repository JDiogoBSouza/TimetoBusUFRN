package com.apps.diogo.timetobusufrn.Classes.Database.Horarios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.Classes.Database.Geral.CriaBanco;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Usuario;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Diogo on 01/11/2017.
 */

public class HorariosDAO
{
    private SQLiteDatabase db;
    private CriaBanco banco;
    
    public HorariosDAO(Context context)
    {
        banco = new CriaBanco(context);
    }
    
    public Cursor selectHorarioByTipo(int tipo)
    {
        Cursor cursor;
        
        String[] campos =  { banco.ID_HORARIO, banco.SAIDA, banco.DESTINO, banco.CHEGADA, banco.IDONIBUS };
    
        String where = banco.IDONIBUS + " in ( SELECT " + banco.ID_ONIBUS + " FROM " + banco.TABELAONIBUS + " WHERE " + banco.ONIBUS_ID_TIPO + " = " + tipo + " )";
        
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELAHORARIOS, campos, where, null, null, null, banco.SAIDA, null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
        
        db.close();
        
        return cursor;
    }
    
    public Cursor selectHorarioAnterior(int tipo)
    {
        Cursor cursor;
        
        String hora = getHorarioStr();
        
        String[] campos =  { banco.ID_HORARIO, banco.SAIDA, banco.DESTINO, banco.CHEGADA, banco.IDONIBUS };
        
        String where = "( " + banco.SAIDA + " < '" + hora + "' ) and " + banco.IDONIBUS + " in ( SELECT " + banco.ID_ONIBUS + " FROM " + banco.TABELAONIBUS + " WHERE " + banco.ONIBUS_ID_TIPO + " = " + tipo + " )";
         
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELAHORARIOS, campos, where, null, null, null, banco.SAIDA + " desc ", "1");
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
        
        db.close();
        
        return cursor;
    }
    
    public Cursor selectHorarioProximos(int tipo)
    {
        Cursor cursor;
    
        String hora = getHorarioStr();
    
        String[] campos =  { banco.ID_HORARIO, banco.SAIDA, banco.DESTINO, banco.CHEGADA, banco.IDONIBUS };
    
        String where = "( " + banco.SAIDA + " >= '" + hora + "' ) and " + banco.IDONIBUS + " in ( SELECT " + banco.ID_ONIBUS + " FROM " + banco.TABELAONIBUS + " WHERE " + banco.ONIBUS_ID_TIPO + " = " + tipo + " )";
    
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELAHORARIOS, campos, where, null, null, null, banco.SAIDA + " asc ", "2");
    
        if(cursor!=null){
            cursor.moveToFirst();
        }
    
        db.close();
    
        return cursor;
    }
    
    public String getHorarioStr()
    {
        Calendar c = getCalendar();
        
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);
        
        return ((hora < 10 ? "0" : "") + String.valueOf(hora))
                + ":"
                + ((minuto < 10 ? "0" : "") + String.valueOf(minuto));
    }
    
    private Calendar getCalendar()
    {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }
}
