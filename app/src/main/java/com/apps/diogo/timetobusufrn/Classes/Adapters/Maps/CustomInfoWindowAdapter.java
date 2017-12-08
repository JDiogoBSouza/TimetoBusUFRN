package com.apps.diogo.timetobusufrn.Classes.Adapters.Maps;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apps.diogo.timetobusufrn.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Diogo on 08/12/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
{
    LayoutInflater infl;
    
    public CustomInfoWindowAdapter(LayoutInflater infl)
    {
        this.infl = infl;
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
        
        CircleImageView imageUser = (CircleImageView) view.findViewById(R.id.imageUser);
        TextView tvTitulo = (TextView) view.findViewById(R.id.tituloMap);
        TextView tvDados = (TextView) view.findViewById(R.id.dadosMap);
        
        tvTitulo.setText(marker.getTitle());
        tvDados.setText(marker.getSnippet());
        
        return view;
    }
}