package com.apps.diogo.timetobusufrn.Classes.Database.DAO.Horarios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.diogo.timetobusufrn.Classes.Database.BancoHorarios;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Diogo on 01/11/2017.
 */

public class HorariosDAO
{
    private SQLiteDatabase db;
    private BancoHorarios banco;
    
    public HorariosDAO(Context context)
    {
        this.banco = new BancoHorarios(context);
    }
    
    public Cursor selectHorarioByTipo(int tipo)
    {
        Cursor cursor;
        
        String[] campos =  { this.banco.ID_HORARIO, this.banco.SAIDA, this.banco.DESTINO, this.banco.CHEGADA, this.banco.IDONIBUS };
    
        String where = this.banco.IDONIBUS + " in ( SELECT " + this.banco.ID_ONIBUS + " FROM " + this.banco.TABELAONIBUS + " WHERE " + this.banco.ONIBUS_ID_TIPO + " = " + tipo + " )";
    
        this.db = this.banco.getReadableDatabase();
        cursor = db.query(this.banco.TABELAHORARIOS, campos, where, null, null, null, this.banco.SAIDA, null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
    
        this.db.close();
        
        return cursor;
    }
    
    public Cursor selectHorarioAnterior(int tipo)
    {
        Cursor cursor;
        
        String hora = getHorarioStr();
        
        String[] campos =  { this.banco.ID_HORARIO, this.banco.SAIDA, this.banco.DESTINO, this.banco.CHEGADA, this.banco.IDONIBUS };
        
        String where = "( " + this.banco.SAIDA + " < '" + hora + "' ) and " + this.banco.IDONIBUS + " in ( SELECT " + this.banco.ID_ONIBUS + " FROM " + this.banco.TABELAONIBUS + " WHERE " + this.banco.ONIBUS_ID_TIPO + " = " + tipo + " )";
         
        db = this.banco.getReadableDatabase();
        cursor = db.query(this.banco.TABELAHORARIOS, campos, where, null, null, null, this.banco.SAIDA + " desc ", "1");
        
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
    
        String[] campos =  { this.banco.ID_HORARIO, this.banco.SAIDA, this.banco.DESTINO, this.banco.CHEGADA, this.banco.IDONIBUS };
    
        String where = "( " + this.banco.SAIDA + " >= '" + hora + "' ) and " + this.banco.IDONIBUS + " in ( SELECT " + this.banco.ID_ONIBUS + " FROM " + this.banco.TABELAONIBUS + " WHERE " + this.banco.ONIBUS_ID_TIPO + " = " + tipo + " )";
    
        db = this.banco.getReadableDatabase();
        cursor = db.query(this.banco.TABELAHORARIOS, campos, where, null, null, null, this.banco.SAIDA + " asc ", "2");
    
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
