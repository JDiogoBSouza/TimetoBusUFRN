package com.apps.diogo.timetobusufrn.Classes.Database.DAO.Horarios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.diogo.timetobusufrn.Classes.Database.BancoHorarios;

/**
 * Created by Diogo on 01/11/2017.
 */

public class OnibusDAO
{
    private SQLiteDatabase db;
    private BancoHorarios banco;
    
    public OnibusDAO(Context context)
    {
        this.banco = new BancoHorarios(context);
    }
    
    public Cursor selectOnibusByID(int id)
    {
        Cursor cursor;
        
        String[] campos =  { this.banco.ID_ONIBUS, this.banco.ONIBUS_ID_EMPRESA, this.banco.ONIBUS_ID_TIPO };
        
        String where = "( " + this.banco.ID_ONIBUS + " = " + id + " )";
    
        this.db = this.banco.getReadableDatabase();
        cursor = this.db.query(this.banco.TABELAONIBUS, campos, where, null, null, null, null, null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
    
        this.db.close();
        
        return cursor;
    }
}
