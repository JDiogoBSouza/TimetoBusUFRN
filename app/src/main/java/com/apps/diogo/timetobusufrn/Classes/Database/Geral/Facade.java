package com.apps.diogo.timetobusufrn.Classes.Database.Geral;

import android.content.Context;
import android.database.Cursor;

import com.apps.diogo.timetobusufrn.Classes.Database.Horarios.HorariosDAO;
import com.apps.diogo.timetobusufrn.Classes.Database.Horarios.OnibusDAO;
import com.apps.diogo.timetobusufrn.Classes.Database.Timeline.PostDAO;
import com.apps.diogo.timetobusufrn.Classes.Database.Timeline.UsuarioDAO;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus.Horario;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus.HorarioComEmpresa;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

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
            byte[] foto = cursorUser.getBlob(cursorUser.getColumnIndex(nomeCamposUser[3]));
            
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
            byte[] foto = cursorUser.getBlob(cursorUser.getColumnIndex(nomeCamposUser[3]));
            
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
            byte[] foto = cursorUser.getBlob(cursorUser.getColumnIndex(nomeCamposUser[2]));
    
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
            byte[] foto = cursorUser.getBlob(cursorUser.getColumnIndex(nomeCamposUser[2]));
            
            return new Usuario(matricula, "", nome, foto);
        }
        
        return null;
    }
    
    public List<HorarioComEmpresa> getHorariosPorTipo(int tipo)
    {
        List<HorarioComEmpresa> lstHorarios = new ArrayList<>();;
        
        HorariosDAO dao = new HorariosDAO(contexto);
    
        String[] camposHorarios = CriaBanco.getStringsHorarios();
    
        Cursor cursor;
        cursor = dao.selectHorarioByTipo(tipo);
        
        if( cursor.getCount() < 0 )
        {
            return null;
        }
    
        do{
            int id = cursor.getInt(cursor.getColumnIndex(camposHorarios[0]));
            String saida = cursor.getString(cursor.getColumnIndex(camposHorarios[1]));
            String destino = cursor.getString(cursor.getColumnIndex(camposHorarios[2]));
            String chegada = cursor.getString(cursor.getColumnIndex(camposHorarios[3]));
            int idonibus = cursor.getInt(cursor.getColumnIndex(camposHorarios[4]));
            
            // TODO: Consultar nome da empresa no banco
            String empresa = getNomeEmpresa(1);
            
            lstHorarios.add( new HorarioComEmpresa(id, saida, destino, chegada, idonibus, empresa) );
        }while( cursor.moveToNext() );
        
        return lstHorarios;
    }
    
    public void getUltimosPosts(ArrayList<Post> posts)
    {
        PostDAO dao = new PostDAO( contexto );
        UsuarioDAO daoUser = new UsuarioDAO( contexto );
    
        Cursor cursor = dao.selectAllPosts();
        Cursor cursorUser;
    
        String[] nomeCampos = new String[] { CriaBanco.ID, CriaBanco.PARADA, CriaBanco.ONIBUS, CriaBanco.HORA, CriaBanco.SEGUNDOS, CriaBanco.COMENTARIO, CriaBanco.MATRIUSUARIO };
        String[] nomeCamposUser = new String[] {CriaBanco.MATRICULA, CriaBanco.NOME, CriaBanco.FOTO};
    
        if( cursor.getCount() < 0 )
        {
            return;
        }
    
        do{
            int id = cursor.getInt( cursor.getColumnIndex( nomeCampos[0] ) );
            String parada = cursor.getString( cursor.getColumnIndex(nomeCampos[1]) );
            String onibus = cursor.getString( cursor.getColumnIndex(nomeCampos[2]) );
            String hora = cursor.getString( cursor.getColumnIndex(nomeCampos[3]) );
            String segundos = cursor.getString( cursor.getColumnIndex(nomeCampos[4]) );
            String comentario = cursor.getString( cursor.getColumnIndex(nomeCampos[5]) );
            int matricuser = cursor.getInt( cursor.getColumnIndex(nomeCampos[6]) );
        
            //Toast.makeText(context, "ID: " + id, Toast.LENGTH_SHORT).show();
        
            cursorUser = daoUser.selectUsuarioNoPass(matricuser);
        
            int matricula = cursorUser.getInt( cursorUser.getColumnIndex(nomeCamposUser[0]) );
            String nome   = cursorUser.getString( cursorUser.getColumnIndex(nomeCamposUser[1]) );
            byte[] foto   = cursorUser.getBlob( cursorUser.getColumnIndex(nomeCamposUser[2]) );
        
            Usuario user = new Usuario(matricula, "", nome, foto);
            Post p = new Post( user, parada , onibus, hora, segundos, comentario );
    
            posts.add(p);
        
        }while(cursor.moveToNext());
    }
    
    public void getHorariosPorTipoeHora(int tipo, List<HorarioComEmpresa> lstHorarios)
    {
        lstHorarios.clear();
        
        HorariosDAO dao = new HorariosDAO(contexto);
    
        String[] camposHorarios = CriaBanco.getStringsHorarios();
        String[] camposOnibus = CriaBanco.getStringsOnibus();
    
        Cursor cursor;
        
        cursor = dao.selectHorarioAnterior(tipo);
    
        if( cursor.getCount() > 0 )
        {
            int id = cursor.getInt(cursor.getColumnIndex(camposHorarios[0]));
            String saida = cursor.getString(cursor.getColumnIndex(camposHorarios[1]));
            String destino = cursor.getString(cursor.getColumnIndex(camposHorarios[2]));
            String chegada = cursor.getString(cursor.getColumnIndex(camposHorarios[3]));
            int idonibus = cursor.getInt(cursor.getColumnIndex(camposHorarios[4]));
            String empresa = getNomeEmpresa( getIdEmpresa( idonibus ) );
    
            lstHorarios.add( new HorarioComEmpresa(id, saida, destino, chegada, idonibus, empresa) );
        }
        else
            return;
    
        Cursor cursor1;
        cursor1 = dao.selectHorarioProximos(tipo);
        
        if( cursor1.getCount() <= 0 )
        {
            return;
        }
        
        do{
            int id = cursor1.getInt(cursor1.getColumnIndex(camposHorarios[0]));
            String saida = cursor1.getString(cursor1.getColumnIndex(camposHorarios[1]));
            String destino = cursor1.getString(cursor1.getColumnIndex(camposHorarios[2]));
            String chegada = cursor1.getString(cursor1.getColumnIndex(camposHorarios[3]));
            int idonibus = cursor1.getInt(cursor1.getColumnIndex(camposHorarios[4]));
            String empresa = getNomeEmpresa( getIdEmpresa( idonibus ) );
            
            
            lstHorarios.add( new HorarioComEmpresa(id, saida, destino, chegada, idonibus, empresa) );
        }while( cursor1.moveToNext() );
    }
    
    private int getIdEmpresa(int idonibus)
    {
        OnibusDAO daoOnibus = new OnibusDAO(contexto);
    
        Cursor busCursor = daoOnibus.selectOnibusByID( idonibus );
    
        if( busCursor.getCount() > 0 )
        {
            return busCursor.getInt(busCursor.getColumnIndex( CriaBanco.ONIBUS_ID_EMPRESA ));
        }
        else
            return -1;
    }
    
    private String getNomeEmpresa(int id)
    {
        String nome = "";
        
        switch( id )
        {
            case 1:
                nome = "Guanabara";
                break;
            
            case 2:
                nome = "Via Sul";
                break;
            
            case 3:
                nome = "Conceição";
                break;
            
            case 4:
                nome = "Cid. do Natal";
                break;
            
            case 5:
                nome = "Santa Maria";
                break;
            
            case 6:
                nome = "Reunidas";
                break;
            
            default:
                nome = "Default";
            break;
        }
        
        return nome;
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