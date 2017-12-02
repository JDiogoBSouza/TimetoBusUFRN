package com.apps.diogo.timetobusufrn.Classes.Adapters.Timeline;

/**
 * Created by Diogo on 07/11/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apps.diogo.timetobusufrn.Classes.Modelos.OnibusSpinner;
import com.apps.diogo.timetobusufrn.R;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<OnibusSpinner>
{
    int groupid;
    Activity context;
    ArrayList<OnibusSpinner> list;
    LayoutInflater inflater;
    
    public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<OnibusSpinner> list)
    {
        super(context,id,list);
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
    }
    
    public View getView(int position, View convertView, ViewGroup parent )
    {
        View itemView = inflater.inflate(groupid,parent,false);
        
        TextView textView = (TextView)itemView.findViewById(R.id.txt);
        textView.setText(list.get(position).getNomeEmpresa());
        
        return itemView;
    }
    
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getView(position,convertView,parent);
    }
}