package com.apps.diogo.timetobusufrn.Classes.Adapters.Maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.diogo.timetobusufrn.Classes.Adapters.Timeline.PostAdapter;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;
import com.apps.diogo.timetobusufrn.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Diogo on 08/12/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
{
    LayoutInflater infl;
    Context context;
    
    public CustomInfoWindowAdapter(LayoutInflater infl, Context context)
    {
        this.infl = infl;
        this.context = context;
    }
    @Override
    public View getInfoWindow(Marker marker)
    {
        return null;
    }
    
    @Override
    public View getInfoContents(Marker marker)
    {
        View view = infl.inflate(R.layout.custom_info_window, null);
    
        ListView listView = (ListView) view.findViewById( R.id.listMap );
    
        ArrayList<Post> listaPosts = (ArrayList<Post>) marker.getTag();
        
        if( !listaPosts.isEmpty() && listaPosts != null )
        {
            InfoWindowListAdapter adaptadorLista = new InfoWindowListAdapter(context, listaPosts);
            listView.setAdapter(adaptadorLista);
        }
        
        return view;
    }
}