package com.apps.diogo.timetobusufrn.Classes.Database.DAO.Horarios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.diogo.timetobusufrn.Classes.Database.BancoHorarios;

/**
 * Created by Diogo on 01/11/2017.
 */

public class EmpresaOnibusDAO
{
    private SQLiteDatabase db;
    private BancoHorarios banco;
    
    public EmpresaOnibusDAO(Context context)
    {
        this.banco = new BancoHorarios(context);
    }
    
    public Cursor selectOnibusByID(int id)
    {
        Cursor cursor;
        
        String[] campos =  { this.banco.ID_EMPRESA, this.banco.NOME_EMPRESA };
        
        String where = this.banco.ID_EMPRESA + " = " + id + " )";
    
        this.db = this.banco.getReadableDatabase();
        cursor = this.db.query(this.banco.TABELAEMPRESAS, campos, where, null, null, null, null, null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
    
        this.db.close();
        
        return cursor;
    }
}
