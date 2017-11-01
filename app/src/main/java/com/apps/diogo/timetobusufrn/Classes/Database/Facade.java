package com.apps.diogo.timetobusufrn.Classes.Database;

import android.content.Context;
import android.database.Cursor;

import com.apps.diogo.timetobusufrn.Classes.Usuario;

/**
 * Created by Diogo on 31/10/2017.
 */

public class Facade
{
    private Context contexto;
    
    public Facade(Context contexto)
    {
        this.contexto = contexto;
    }
    
    public Usuario getUsuarioByMatricula(int matric)
    {
        UsuarioDAO dao = new UsuarioDAO( contexto );
        
        String[] nomeCamposUser = CriaBanco.getStringsUsuario(true);
        
        Cursor cursorUser = dao.selectUsuarioByMatricula(matric);
    
        if( cursorUser.getCount() > 0 ) {
            int matricula = cursorUser.getInt(cursorUser.getColumnIndex(nomeCamposUser[0]));
            String senha = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[1]));
            String nome = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[2]));
            String foto = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[3]));
            
            return new Usuario(matricula, senha, nome, foto);
        }
        
        return null;
    }
    
    public Usuario getUsuarioByMatriculaS(String matric)
    {
        UsuarioDAO dao = new UsuarioDAO( contexto );
        
        String[] nomeCamposUser = CriaBanco.getStringsUsuario(true);
        
        int iMatricula = converteString( matric );
        
        Cursor cursorUser = dao.selectUsuarioByMatricula(iMatricula);
    
        if( cursorUser.getCount() > 0 ) {
            int matricula = cursorUser.getInt(cursorUser.getColumnIndex(nomeCamposUser[0]));
            String senha = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[1]));
            String nome = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[2]));
            String foto = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[3]));
            
            return new Usuario(matricula, senha, nome, foto);
        }
        
        return null;
    }
    
    public Usuario getUsuarioByMatriculaSemSenha(int matric)
    {
        UsuarioDAO dao = new UsuarioDAO( contexto );
        
        String[] nomeCamposUser = CriaBanco.getStringsUsuario(false);
        
        Cursor cursorUser = dao.selectUsuarioByMatricula( matric );
        
        if( cursorUser.getCount() > 0 ) {
            int matricula = cursorUser.getInt(cursorUser.getColumnIndex(nomeCamposUser[0]));
            String nome = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[1]));
            String foto = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[2]));
    
            return new Usuario(matricula, "", nome, foto);
        }
        
        return null;
    }
    
    public Usuario getUsuarioByMatriculaSemSenha(String matric)
    {
        UsuarioDAO dao = new UsuarioDAO( contexto );
        
        String[] nomeCamposUser = CriaBanco.getStringsUsuario(false);
    
        int iMatricula = converteString( matric );
        
        Cursor cursorUser = dao.selectUsuarioByMatricula( iMatricula );
    
        if( cursorUser.getCount() > 0 ) {
            int matricula = cursorUser.getInt(cursorUser.getColumnIndex(nomeCamposUser[0]));
            String nome = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[1]));
            String foto = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[2]));
            
            return new Usuario(matricula, "", nome, foto);
        }
        
        return null;
    }
    
    private int converteString(String s)
    {
        // TODO: Verificar se string contem letras, tratar exceção
        int convertido = Integer.parseInt( s );
        
        return convertido;
    }
    
    public int updateUsuario(Usuario user, Context context)
    {
        UsuarioDAO dao = new UsuarioDAO( context );
        dao.updateUsuario( user );
        
        // TODO: verificar se foi atualizado ou deu erro
        return 1;
    }
    
    private void insertUsuario()
    {
        
    }
}
