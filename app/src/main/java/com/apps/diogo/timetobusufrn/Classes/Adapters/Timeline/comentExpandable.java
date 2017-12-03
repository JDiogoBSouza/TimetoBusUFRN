package com.apps.diogo.timetobusufrn.Classes.Adapters.Timeline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.apps.diogo.timetobusufrn.R;

/**
 * Created by Diogo on 30/11/2017.
 */

public class comentExpandable extends BaseExpandableListAdapter
{
    private String comentario;
    private Context context;
    
    public comentExpandable(Context context, String comentario)
    {
        // inicializa as variáveis da classe
        this.context = context;
        this.comentario = comentario;
    }
    
    @Override
    public int getGroupCount()
    {
        return 1;
    }
    
    @Override
    public int getChildrenCount(int groupPosition)
    {
        return 1;
    }
    
    @Override
    public Object getGroup(int groupPosition)
    {
        return null;
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }
    
    @Override
    public long getGroupId(int groupPosition)
    {
        return 1;
    }
    
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 1;
    }
    
    @Override
    public boolean hasStableIds()
    {
        return false;
    }
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grupo_coment, null);
        }
    
        return convertView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.coment_post, null);
        }
    
        TextView tvComent = (TextView) convertView.findViewById(R.id.tvComentario);
    
        tvComent.setText( this.comentario );
    
        return convertView;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
