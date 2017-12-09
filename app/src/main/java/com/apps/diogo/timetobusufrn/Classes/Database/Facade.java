package com.apps.diogo.timetobusufrn.Classes.Database;

import android.content.Context;
import android.database.Cursor;

import com.apps.diogo.timetobusufrn.Classes.Database.DAO.Horarios.HorariosDAO;
import com.apps.diogo.timetobusufrn.Classes.Database.DAO.Horarios.OnibusDAO;
import com.apps.diogo.timetobusufrn.Classes.Database.DAO.Timeline.PostDAO;
import com.apps.diogo.timetobusufrn.Classes.Database.DAO.Timeline.UsuarioDAO;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus.Onibus;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Usuario;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus.Horario;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diogo on 31/10/2017.
 */

public class Facade
{
    private Context contexto;
    
    /**
     * Construtor padrão da classe.
     * @param contexto
     */
    public Facade(Context contexto)
    {
        this.contexto = contexto;
    }
    
    /**
     * Método para buscar um usuário pela matricula.
     * @param matric : Matricula do usuário que se deseja buscar.
     * @return : Objeto usuário encontrado, ou nulo caso não tenha sido encontrado.
     */
    public Usuario getUsuarioByMatricula(int matric)
    {
        UsuarioDAO dao = new UsuarioDAO( contexto );
        
        String[] nomeCamposUser = BancoTimeline.getStringsUsuario(true, true);
        
        Cursor cursorUser = dao.selectUsuarioByMatricula(matric);
    
        if( cursorUser.getCount() > 0 ) {
            int matricula = cursorUser.getInt(cursorUser.getColumnIndex(nomeCamposUser[0]));
            String nome = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[1]));
            String senha = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[2]));
            byte[] foto = cursorUser.getBlob(cursorUser.getColumnIndex(nomeCamposUser[3]));
            
            return new Usuario(matricula, senha, nome, foto);
        }
        
        return null;
    }
    
    /**
     * Método para buscar um usuário pela matricula no formato String.
     * @param matric : Matricula do usuário que se deseja buscar.
     * @return : Objeto usuário encontrado, ou nulo caso não tenha sido encontrado.
     */
    public Usuario getUsuarioByMatriculaS(String matric, String senha)
    {
        UsuarioDAO dao = new UsuarioDAO( contexto );
        
        String[] nomeCamposUser = BancoTimeline.getStringsUsuario(false, true);
        
        int iMatricula = converteString( matric );
    
        Usuario user = new Usuario(iMatricula, "", "", null);
        Cursor cursorUser;
        
        boolean imagemExiste = verificarImagem( iMatricula, user );
        cursorUser = dao.selectUsuarioValido(iMatricula, senha, !imagemExiste);
    
        if( cursorUser.getCount() > 0 )
        {
            String nome = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[1]));
            user.setNome(nome);
            
            if( !imagemExiste )
            {
                byte[] foto = cursorUser.getBlob(cursorUser.getColumnIndex(nomeCamposUser[2]));
                user.setFoto(foto);
            }
            
            return user;
        }
        
        return null;
    }
    
    /**
     * Método para buscar um usuário pela matricula sem o campo de senha.
     * @param matric : Matricula do usuário que se deseja buscar.
     * @return : Objeto usuário encontrado, ou nulo caso não tenha sido encontrado.
     */
    public Usuario getUsuarioByMatriculaSemSenha(int matric)
    {
        UsuarioDAO dao = new UsuarioDAO( contexto );
        
        String[] nomeCamposUser = BancoTimeline.getStringsUsuario(false, true);
        
        Cursor cursorUser = dao.selectUsuarioByMatricula( matric );
        
        if( cursorUser.getCount() > 0 )
        {
            int matricula = cursorUser.getInt(cursorUser.getColumnIndex(nomeCamposUser[0]));
            String nome = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[1]));
            byte[] foto = cursorUser.getBlob(cursorUser.getColumnIndex(nomeCamposUser[2]));
    
            return new Usuario(matricula, "", nome, foto);
        }
        
        return null;
    }
    
    /**
     * Método para buscar um usuário pela matricula no formato String, sem o campo de senha.
     * @param matric : Matricula do usuário que se deseja buscar.
     * @return : Objeto usuário encontrado, ou nulo caso não tenha sido encontrado.
     */
    public Usuario getUsuarioByMatriculaSemSenha(String matric)
    {
        int iMatricula = converteString( matric );
        
        return getUsuarioByMatriculaSemSenha(iMatricula);
    }
    
    /**
     * Método para buscar um usuário pela matricula sem o campo de senha e sem o campo foto.
     * @param matric : Matricula do usuário que se deseja buscar.
     * @return : Objeto usuário encontrado, ou nulo caso não tenha sido encontrado.
     */
    public Usuario getUsuarioByMatriculaSemSenhaSemFoto(int matric)
    {
        UsuarioDAO dao = new UsuarioDAO( contexto );
        
        String[] nomeCamposUser = BancoTimeline.getStringsUsuario(false, false);
        
        Cursor cursorUser = dao.selectUsuarioNoPassNoPhoto( matric );
        
        if( cursorUser.getCount() > 0 )
        {
            int matricula = cursorUser.getInt(cursorUser.getColumnIndex(nomeCamposUser[0]));
            String nome = cursorUser.getString(cursorUser.getColumnIndex(nomeCamposUser[1]));
            
            return new Usuario(matricula, "", nome, null);
        }
        
        return null;
    }
    
    /**
     * Método para buscar um usuário pela matricula sem o campo de senha e sem o campo foto.
     * @param matric : Matricula do usuário que se deseja buscar.
     * @return : Objeto usuário encontrado, ou nulo caso não tenha sido encontrado.
     */
    public Usuario getUsuarioByMatriculaSemSenhaSemFoto(String matric)
    {
        int iMatricula = converteString( matric );
        
        return getUsuarioByMatriculaSemSenhaSemFoto(iMatricula);
    }
    
    /**
     * Método para buscar os horarios dos onibus com o id da empresa.
     * @param tipo : Tipo do onibus a ser buscado.
     * @return : Lista com os horarios dos onibus.
     */
    public List<Horario> getHorariosPorTipo(int tipo)
    {
        List<Horario> lstHorarios = new ArrayList<>();;
        
        HorariosDAO dao = new HorariosDAO(contexto);
    
        String[] camposHorarios = BancoHorarios.getStringsHorarios();
    
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
            
            Onibus onibus = getOnibusById(idonibus);
            
            lstHorarios.add( new Horario(id, saida, destino, chegada, onibus) );
        }while( cursor.moveToNext() );
        
        return lstHorarios;
    }
    
    /**
     * Método para buscar os posts mais recentes.
     * @param posts : lista para adicionar os posts encontrados.
     * TODO: buscar apenas posts dentro de uma faixa de tempo.
     */
    public void getUltimosPosts(ArrayList<Post> posts)
    {
        PostDAO dao = new PostDAO( contexto );
        UsuarioDAO daoUser = new UsuarioDAO( contexto );
    
        Cursor cursor = dao.selectAllPosts();
        Cursor cursorUser;
    
        String[] nomeCampos = new String[] { BancoTimeline.ID, BancoTimeline.PARADA, BancoTimeline.TIPOONIBUS, BancoTimeline.EMPRESAONIBUS, BancoTimeline.HORA, BancoTimeline.SEGUNDOS, BancoTimeline.COMENTARIO, BancoTimeline.MATRIUSUARIO };
        String[] nomeCamposUser = new String[] {BancoTimeline.MATRICULA, BancoTimeline.NOME, BancoTimeline.FOTO};
    
        if( cursor.getCount() <= 0 )
        {
            return;
        }
    
        do{
            int id = cursor.getInt( cursor.getColumnIndex( nomeCampos[0] ) );
            int parada = cursor.getInt( cursor.getColumnIndex(nomeCampos[1]) );
            String onibus = cursor.getString( cursor.getColumnIndex(nomeCampos[2]) );
            int empresaOnibus = cursor.getInt( cursor.getColumnIndex(nomeCampos[3]) );
            String hora = cursor.getString( cursor.getColumnIndex(nomeCampos[4]) );
            String segundos = cursor.getString( cursor.getColumnIndex(nomeCampos[5]) );
            String comentario = cursor.getString( cursor.getColumnIndex(nomeCampos[6]) );
            int matricuser = cursor.getInt( cursor.getColumnIndex(nomeCampos[7]) );
    
            Usuario user = new Usuario(matricuser, "", "", null);
            
            byte[] foto = null;
            String nome;
    
            // Verificar se o usuário tem foto já na pasta do applicativo, caso ja tenha, não baixa do banco.
            if( verificarImagem( matricuser, user ) )
            {
                cursorUser = daoUser.selectUsuarioNoPassNoPhoto(matricuser);
                
                nome   = cursorUser.getString( cursorUser.getColumnIndex(nomeCamposUser[1]) );
                
                user.setNome(nome);
            }
            else
            {
                cursorUser = daoUser.selectUsuarioNoPass(matricuser);
                
                nome   = cursorUser.getString( cursorUser.getColumnIndex(nomeCamposUser[1]) );
                foto   = cursorUser.getBlob( cursorUser.getColumnIndex(nomeCamposUser[2]) );
                
                user.setNome(nome);
                user.setFoto(foto);
            }
        
            Post p = new Post( id, user, parada , onibus, empresaOnibus, hora, segundos, comentario );
    
            posts.add(p);
        
        }while(cursor.moveToNext());
    }
    
    private boolean verificarImagem(int matricula, Usuario user)
    {
        String fileName = "thumb" + matricula;
        String local = contexto.getFilesDir().getPath()+ "/" + fileName + ".jpg";
    
        File f = new File(local);
        int size = (int) f.length();
        
        byte[] foto = new byte[size];
        
        try
        {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(f));
            buf.read(foto, 0, foto.length);
            buf.close();
            
            user.setFoto(foto);
            
            return true;
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Método para buscar horarios por tipo do onibus e pela hora.
     * Busca um onibus que ja saiu e dois onibus que vão sair.
     * @param tipo : Tipo de onibus que se deseja buscar.
     * @param lstHorarios : Lista para preencher com os dados encontrados.
     */
    public void getHorariosPorTipoeHora(int tipo, List<Horario> lstHorarios)
    {
        lstHorarios.clear();
        
        HorariosDAO dao = new HorariosDAO(contexto);
    
        String[] camposHorarios = BancoHorarios.getStringsHorarios();
    
        Cursor cursor;
        
        cursor = dao.selectHorarioAnterior(tipo);
    
        if( cursor.getCount() > 0 )
        {
            int id = cursor.getInt(cursor.getColumnIndex(camposHorarios[0]));
            String saida = cursor.getString(cursor.getColumnIndex(camposHorarios[1]));
            String destino = cursor.getString(cursor.getColumnIndex(camposHorarios[2]));
            String chegada = cursor.getString(cursor.getColumnIndex(camposHorarios[3]));
            
            int idonibus = cursor.getInt(cursor.getColumnIndex(camposHorarios[4]));
            
            Onibus onibus = getOnibusById( idonibus );
    
            lstHorarios.add( new Horario(id, saida, destino, chegada, onibus ) );
        }
    
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
                
            Onibus onibus = getOnibusById( idonibus );
            
            lstHorarios.add( new Horario(id, saida, destino, chegada, onibus ) );
        }while( cursor1.moveToNext() );
    }
    
    /**
     * Método para buscar horarios por mais de um tipo de onibus e pela hora.
     * Busca um onibus que ja saiu e dois onibus que vão sair.
     * @param tipo : Tipo de onibus que se deseja buscar.
     * @param lstHorarios : Lista para preencher com os dados encontrados.
     */
    public void getHorariosPorTipoeHora(int tipo, int tipo2, List<Horario> lstHorarios)
    {
        lstHorarios.clear();
        
        HorariosDAO dao = new HorariosDAO(contexto);
        
        String[] camposHorarios = BancoHorarios.getStringsHorarios();
        
        Cursor cursor;
        
        cursor = dao.selectHorarioAnterior(tipo, tipo2);
        
        if( cursor.getCount() > 0 )
        {
            int id = cursor.getInt(cursor.getColumnIndex(camposHorarios[0]));
            String saida = cursor.getString(cursor.getColumnIndex(camposHorarios[1]));
            String destino = cursor.getString(cursor.getColumnIndex(camposHorarios[2]));
            String chegada = cursor.getString(cursor.getColumnIndex(camposHorarios[3]));
            
            int idonibus = cursor.getInt(cursor.getColumnIndex(camposHorarios[4]));
            
            Onibus onibus = getOnibusById( idonibus );
            
            lstHorarios.add( new Horario(id, saida, destino, chegada, onibus ) );
        }
        
        Cursor cursor1;
        cursor1 = dao.selectHorarioProximos(tipo, tipo2);
        
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
            
            Onibus onibus = getOnibusById( idonibus );
            
            lstHorarios.add( new Horario(id, saida, destino, chegada, onibus ) );
        }while( cursor1.moveToNext() );
    }
    
    public Onibus getOnibusById(int id)
    {
        OnibusDAO dao = new OnibusDAO( contexto );
    
        String[] nomesCamposOnibus = BancoHorarios.getStringsOnibus();
    
        Cursor cursorOnibus = dao.selectOnibusByID( id );
    
        if( cursorOnibus.getCount() > 0 )
        {
            int empresa_ = cursorOnibus.getInt(cursorOnibus.getColumnIndex(nomesCamposOnibus[1]));
            int tipo_ = cursorOnibus.getInt(cursorOnibus.getColumnIndex(nomesCamposOnibus[2]));
        
            return new Onibus(id, tipo_, empresa_);
        }
    
        return null;
    }
    
    /**
     * Método para inserir um post no banco.
     * @param post : Post a ser inserido no banco.
     * @return : True caso tenha sido inserido, false caso contrario.
     */
    public boolean inserirPost( Post post )
    {
        PostDAO dao = new PostDAO(contexto);
        
        if( dao.insertPost( post ) != -1 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Converte uma string para inteiro.
     * @param s : String a ser convertida para inteiro.
     * @return : Inteiro resultante da conversão.
     */
    private int converteString(String s)
    {
        // TODO: Verificar se string contem letras, tratar exceção
        int convertido = Integer.parseInt( s );
        
        return convertido;
    }
    
    /**
     * Método para atualizar as informações de um usuário no banco.
     * @param user : Usuario a ser atualizado.
     * @param context : Contexto
     * @return : Valor para verificar se a atualização ocorreu com sucesso ou não.
     */
    public int updateUsuario(Usuario user, Context context)
    {
        UsuarioDAO dao = new UsuarioDAO( context );
        dao.updateUsuario( user );
        
        // TODO: verificar se foi atualizado ou deu erro
        return 1;
    }
    
    public boolean deletarPost(int id)
    {
        PostDAO dao = new PostDAO(contexto);
        dao.deletePost(id);
        
        //TODO: Verificar se realmente excluiu
        return true;
    }
    
    public boolean deletarPostsAntigos()
    {
        PostDAO dao = new PostDAO(contexto);
        dao.deletePostsAntigos();
        
        return true;
    }
}
