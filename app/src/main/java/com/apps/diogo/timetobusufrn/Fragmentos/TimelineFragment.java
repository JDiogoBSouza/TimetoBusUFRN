package com.apps.diogo.timetobusufrn.Fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.Classes.Database.Facade;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;
import com.apps.diogo.timetobusufrn.Classes.Adapters.Timeline.PostAdapter;
import com.apps.diogo.timetobusufrn.R;
import com.apps.diogo.timetobusufrn.Classes.Utility;

import java.util.ArrayList;

/**
 * Created by Diogo on 17/10/2017.
 */

public class TimelineFragment extends Fragment
{
    //Declara um atributo para guardar o context.
    private Context context;
    private PostAdapter adaptadorLista;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    int matriculaUsuario;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.content_timeline,container,false);
        
        final ListView lista = (ListView) rootView.findViewById(R.id.listaposts);
        adaptadorLista = new PostAdapter(context, posts);
        lista.setAdapter(adaptadorLista);
    
        Utility.setListViewHeightBasedOnChildren(lista);
    
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
    
        registerForContextMenu(lista);
        
        lista.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener()
        {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
            {
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;
                int position = menuInfo.position;
                
                int mat = posts.get(position).getUsuario().getMatricula();
                
                // Toast.makeText(context, "Matricula = " + mat, Toast.LENGTH_SHORT).show();
                
                if( mat == matriculaUsuario )
                {
                    contextMenu.add(Menu.NONE, 1, Menu.NONE, "Editar");
                    contextMenu.add(Menu.NONE, 2, Menu.NONE, "Deletar");
                }
            }
        });
        
        buscaPosts();
        return rootView;
    }
    
    
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        
        switch (item.getItemId())
        {
            case 1:
                editarPost(position);
            break;
            
            case 2:
                deletarPost(position);
            break;
        }
        
        return super.onContextItemSelected(item);
    }
    
    /**
     * Função para apagar um post do banco e consequentemente, da timeline
     * @param position : posição do post no ArrayList
     */
    private void deletarPost(int position)
    {
        Facade fac = new Facade(context);
        
        Post post = posts.get( position );
        int id = post.getId();
    
        //Toast.makeText(context, "Post ID: " + id + " User: " + post.getUsuario().getNome(), Toast.LENGTH_SHORT).show();
        
        if( fac.deletarPost( id ) )
        {
            Toast.makeText(context, "Post Deletado", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Erro ao Deletar Post !", Toast.LENGTH_SHORT).show();
        }
        
        buscaPosts();
    }
    
    /**
     * Função para editar um post do banco e consequentemente, da timeline
     * @param position : posição do post no ArrayList
     */
    private void editarPost(int position)
    {
        Toast.makeText(context, "Editar Post", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Adicionar um post ao ArrayList de posts.
     * @param post
     */
    public void adicionarPost(Post post)
    {
        posts.add(post);
        adaptadorLista.notifyDataSetChanged();
    }
    
    @Override
    public void onAttach(Context context)
    {
        this.context = context;
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        matriculaUsuario = sharedPreferences.getInt("matriculaI", 0000);
        
        super.onAttach(context);
    }
    
    /**
     * Busca os posts no banco de dados e atualiza a timeline.
     */
    public void buscaPosts()
    {
        Facade fac = new Facade(context);
        fac.deletarPostsAntigos();
        adaptadorLista.clear();
        fac.getUltimosPosts(posts);
        adaptadorLista.notifyDataSetChanged();
    }
}