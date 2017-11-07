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

import com.apps.diogo.timetobusufrn.Classes.Database.Geral.CriaBanco;
import com.apps.diogo.timetobusufrn.Classes.Database.Geral.Facade;
import com.apps.diogo.timetobusufrn.Classes.Database.Timeline.PostDAO;
import com.apps.diogo.timetobusufrn.Classes.Database.Timeline.UsuarioDAO;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;
import com.apps.diogo.timetobusufrn.Classes.Adapters.PostAdapter;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Usuario;
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
    private ArrayList<Post> posts = new ArrayList<Post>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    
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
        Facade fac = new Facade(context);
        
        adaptadorLista.clear();
        fac.getUltimosPosts(posts);
        adaptadorLista.notifyDataSetChanged();
    }
}
