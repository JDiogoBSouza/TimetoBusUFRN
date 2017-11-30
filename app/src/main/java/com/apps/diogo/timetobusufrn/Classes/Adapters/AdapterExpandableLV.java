package com.apps.diogo.timetobusufrn.Classes.Adapters;

import android.widget.BaseExpandableListAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.R;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Onibus.Horario;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Diogo on 01/11/2017.
 */

public class AdapterExpandableLV extends BaseExpandableListAdapter
{
    private List<String> lstGrupos;
    private HashMap<String, List<Horario>> lstItensGrupos;
    private Context context;
    
    public AdapterExpandableLV(Context context, List<String> grupos, HashMap<String, List<Horario>> itensGrupos)
    {
        // inicializa as variáveis da classe
        this.context = context;
        lstGrupos = grupos;
        lstItensGrupos = itensGrupos;
    }
    
    @Override
    public int getGroupCount()
    {
        // retorna a quantidade de grupos
        return lstGrupos.size();
    }
    
    @Override
    public int getChildrenCount(int groupPosition)
    {
        // retorna a quantidade de itens de um grupo
        return lstItensGrupos.get(getGroup(groupPosition)).size();
    }
    
    @Override
    public Object getGroup(int groupPosition)
    {
        // retorna um grupo
        return lstGrupos.get(groupPosition);
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        // retorna um item do grupo
        return lstItensGrupos.get(getGroup(groupPosition)).get(childPosition);
    }
    
    @Override
    public long getGroupId(int groupPosition)
    {
        // retorna o id do grupo, porém como nesse exemplo
        // o grupo não possui um id específico, o retorno
        // será o próprio groupPosition
        return groupPosition;
    }
    
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        // retorna o id do item do grupo, porém como nesse exemplo
        // o item do grupo não possui um id específico, o retorno
        // será o próprio childPosition
        return childPosition;
    }
    
    @Override
    public boolean hasStableIds()
    {
        // retorna se os ids são específicos (únicos para cada
        // grupo ou item) ou relativos
        return false;
    }
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        // cria os itens principais (grupos)
        
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grupo, null);
        }
        
        TextView tvTipo = (TextView) convertView.findViewById(R.id.tvTipo);
        
        tvTipo.setText( lstGrupos.get( (int) getGroupId(groupPosition) ) );
        
        return convertView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        // cria os subitens (itens dos grupos)
        
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_horario, null);
        }
    
        TextView tvHora = (TextView) convertView.findViewById(R.id.tvHora);
        TextView tvOnibus = (TextView) convertView.findViewById(R.id.tvOnibus);
        final ImageView imgInfo = (ImageView) convertView.findViewById(R.id.infoOnibus);
    
        Horario horario = (Horario) getChild(groupPosition, childPosition);
        
        if( horario.getSaida() != null )
        {
            tvHora.setText(horario.getSaida());
        }
        else
        {
            tvHora.setText( horario.getDestino() );
        }
        
        tvOnibus.setText( horario.getOnibus().getNomeEmpresa() );
        
        final int tipoOnibus = horario.getOnibus().getTipo();
        
        if( tipoOnibus > 2 )
        {
            imgInfo.setVisibility( View.VISIBLE );
    
            imgInfo.setOnClickListener( new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    if (v.equals( imgInfo ))
                    {
                        switch( tipoOnibus )
                        {
                            case 3:
                                Toast.makeText(context, "Via Direta / ECT", Toast.LENGTH_SHORT).show();
                                break;
        
                            case 4:
                                Toast.makeText(context, "RU / Via Direta", Toast.LENGTH_SHORT).show();
                                break;
        
                            case 5:
                                Toast.makeText(context, "Via Direta / RU", Toast.LENGTH_SHORT).show();
                                break;
        
                            case 6:
                                Toast.makeText(context, "Via Direta / Reitoria", Toast.LENGTH_SHORT).show();
                                break;
        
                            case 7:
                                Toast.makeText(context, "RU / Via Direta", Toast.LENGTH_SHORT).show();
                                break;
        
                            case 8:
                                Toast.makeText(context, "Via Direta / RU", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
            });
        }
        else
        {
            imgInfo.setVisibility( View.INVISIBLE );
        }
        
        return convertView;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // retorna se o subitem (item do grupo) é selecionável
        return true;
    }
}