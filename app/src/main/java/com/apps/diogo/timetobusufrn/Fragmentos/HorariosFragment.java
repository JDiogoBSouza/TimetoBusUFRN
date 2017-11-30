package com.apps.diogo.timetobusufrn.Fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.apps.diogo.timetobusufrn.Classes.Adapters.AdapterExpandableLV;
import com.apps.diogo.timetobusufrn.Classes.Database.Facade;
import com.apps.diogo.timetobusufrn.R;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus.Horario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Diogo on 17/10/2017.
 */

public class HorariosFragment extends Fragment
{
    private View view;
    private Context context;
    private int lastExpandedPosition = -1;
    
    private ExpandableListView elvHorarios;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    
    List<Horario> lstDiretos = new ArrayList<>();
    List<Horario> lstInversos = new ArrayList<>();
    List<Horario> lstExpressosCET = new ArrayList<>();
    List<Horario> lstExpressosREI = new ArrayList<>();
    
    AdapterExpandableLV adaptador;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.content_horarios,container,false);
    
        elvHorarios = (ExpandableListView) view.findViewById(R.id.elvHorarios);
    
        elvHorarios.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()
        {
            @Override
            public void onGroupExpand(int groupPosition)
            {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition)
                {
                    elvHorarios.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        
        // cria os grupos
        List<String> lstGrupos = new ArrayList<>();
        lstGrupos.add("Direto");
        lstGrupos.add("Inverso");
        lstGrupos.add("Expresso C&T");
        lstGrupos.add("Expresso Reitoria");
    
        // cria o "relacionamento" dos grupos com seus itens
        HashMap<String, List<Horario>> lstItensGrupo = new HashMap<>();
        lstItensGrupo.put(lstGrupos.get(0), lstDiretos);
        lstItensGrupo.put(lstGrupos.get(1), lstInversos);
        lstItensGrupo.put(lstGrupos.get(2), lstExpressosCET);
        lstItensGrupo.put(lstGrupos.get(3), lstExpressosREI);
    
        // cria um adaptador (BaseExpandableListAdapter) com os dados acima
        adaptador = new AdapterExpandableLV(context, lstGrupos, lstItensGrupo);
        // define o apadtador do ExpandableListView
        
        elvHorarios.setAdapter(adaptador);
    
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_refreshHorarios);
    
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                atualizaListas();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    
        atualizaListas();
        return view;
    }
    
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        //Toast.makeText(context, "Pegando Contexto do Pai" , Toast.LENGTH_SHORT).show();
    }
    
    private void atualizaListas()
    {
        Facade fac = new Facade(context);
        
        fac.getHorariosPorTipoeHora(1,lstDiretos );
        fac.getHorariosPorTipoeHora(2,lstInversos );
        fac.getHorariosPorTipoeHora(3,lstExpressosCET);
        fac.getHorariosPorTipoeHora(6,lstExpressosREI );
    
        adaptador.notifyDataSetChanged();
    }
}
