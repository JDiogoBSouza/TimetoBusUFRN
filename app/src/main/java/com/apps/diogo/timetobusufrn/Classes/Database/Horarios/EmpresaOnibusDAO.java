package com.apps.diogo.timetobusufrn.Classes.Database.Horarios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.diogo.timetobusufrn.Classes.Database.Geral.CriaBanco;

/**
 * Created by Diogo on 01/11/2017.
 */

public class EmpresaOnibusDAO
{
    private SQLiteDatabase db;
    private CriaBanco banco;
    
    public EmpresaOnibusDAO(Context context)
    {
        banco = new CriaBanco(context);
    }
    
    public Cursor selectOnibusByID(int id)
    {
        Cursor cursor;
        
        String[] campos =  { banco.ID_EMPRESA, banco.NOME_EMPRESA };
        
        String where = banco.ID_EMPRESA + " = " + id + " )";
        
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELAEMPRESAS, campos, where, null, null, null, null, null);
        
        if(cursor!=null){
            cursor.moveToFirst();
        }
        
        db.close();
        
        return cursor;
    }
}
