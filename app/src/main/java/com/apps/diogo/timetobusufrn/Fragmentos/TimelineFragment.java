package com.apps.diogo.timetobusufrn.Fragmentos;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.Classes.Database.CriaBanco;
import com.apps.diogo.timetobusufrn.Classes.Database.PostDAO;
import com.apps.diogo.timetobusufrn.Classes.Database.UsuarioDAO;
import com.apps.diogo.timetobusufrn.Classes.Post;
import com.apps.diogo.timetobusufrn.Classes.PostAdapter;
import com.apps.diogo.timetobusufrn.Classes.Usuario;
import com.apps.diogo.timetobusufrn.R;

import java.util.ArrayList;

/**
 * Created by Diogo on 17/10/2017.
 */

public class TimelineFragment extends Fragment
{
    //Declara um atributo para guardar o context.
    private Context context;
    private PostAdapter adaptadorLista;
    ArrayList<Post> posts = new ArrayList<Post>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.content_timeline,container,false);
        
        ListView lista = (ListView) rootView.findViewById(R.id.listaposts);
        adaptadorLista = new PostAdapter(context, posts);
        lista.setAdapter(adaptadorLista);
        
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.sw_refresh);
        
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                buscaPosts();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                
                Toast.makeText(context, "Sem evento de clique.", Toast.LENGTH_SHORT).show();
            }
        });
    
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                Toast.makeText(context, "Sem evento de clique longo.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    
        buscaPosts();
        return rootView;
    }
    
    public void adicionarPost(Post post)
    {
        posts.add(post);
        adaptadorLista.notifyDataSetChanged();
    }
    
    @Override
    public void onAttach(Context context)
    {
        this.context = context;
        super.onAttach(context);
    }
    
    public void buscaPosts()
    {
        adaptadorLista.clear();
        PostDAO dao = new PostDAO( context );
        UsuarioDAO daoUser = new UsuarioDAO( context );
        
        Cursor cursor = dao.selectAllPosts();
        Cursor cursorUser;
        
        String[] nomeCampos = new String[] { CriaBanco.ID, CriaBanco.PARADA, CriaBanco.ONIBUS, CriaBanco.HORA, CriaBanco.SEGUNDOS, CriaBanco.COMENTARIO, CriaBanco.MATRIUSUARIO };
        String[] nomeCamposUser = new String[] {CriaBanco.MATRICULA, CriaBanco.NOME, CriaBanco.FOTO};
        
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
            String foto   = cursorUser.getString( cursorUser.getColumnIndex(nomeCamposUser[2]) );
            
            Usuario user = new Usuario(matricula, "", nome, foto);
            Post p = new Post( user, parada , onibus, hora, segundos, comentario );
            
            adicionarPost( p );
            
        }while(cursor.moveToNext());
    }
}
