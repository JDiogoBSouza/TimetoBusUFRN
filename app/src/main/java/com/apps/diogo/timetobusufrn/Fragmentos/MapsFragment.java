package com.apps.diogo.timetobusufrn.Fragmentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.diogo.timetobusufrn.Classes.Adapters.Maps.CustomInfoWindowAdapter;
import com.apps.diogo.timetobusufrn.Classes.Database.Facade;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;
import com.apps.diogo.timetobusufrn.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Diogo on 17/10/2017.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback
{
    Context context;
    MapView mapView;
    GoogleMap mMap;
    View mView;
    LayoutInflater infl;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        infl = inflater;
        mView = inflater.inflate(R.layout.content_map, container, false);
    
        return mView;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) mView.findViewById(R.id.mapView);
        
        if( mapView != null )
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }
    
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        MapsInitializer.initialize( context );
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        //mMap.getUiSettings().setMyLocationButtonEnabled(false);
        //if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
        //mMap.setMyLocationEnabled(true);
        
        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(infl, context);
        mMap.setInfoWindowAdapter(adapter);
    
        LatLng local = new LatLng(-5.837020, -35.203532);
        //mMap.addMarker(new MarkerOptions().position( local ).title("Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( local , 15));
        
        inserirPostsNoMapa();
    }
    
    private void inserirPostsNoMapa()
    {
        Facade fac = new Facade(context);
        
        ArrayList<Post> posts = new ArrayList<Post>();
        fac.getUltimosPosts(posts);
    
        int height = 120;
        int width = 120;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable( R.drawable.busico );
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
    
        Map<Integer, Marker> mMarkers = new HashMap<>();
        
        for(Post post : posts)
        {
            Marker marcador = mMarkers.get( post.getIdParada() );
            
            if( marcador == null  )
            {
                // Adiciona um novo marcador.
                ArrayList<Post> listaPosts = new ArrayList<Post>();

                marcador = mMap.addMarker(new MarkerOptions()
                        .position( buscaLocalizacao( post.getIdParada() ) )
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        .title( post.getUsuario().getNome() )
                        .snippet( post.toString() ));
                
                listaPosts.add(post);
                marcador.setTag(listaPosts);
                
                mMarkers.put( post.getIdParada(), marcador);
            }
            else
            {
                // Atualiza o marcador existente.
                ArrayList<Post> listaExistente = (ArrayList<Post>) marcador.getTag();
                listaExistente.add(post);
            }
        }
        
        
    }
    
    private LatLng buscaLocalizacao( int idLocal )
    {
        // Apenas para testes, Amazenar coordenadas no banco de dados depois
        
        LatLng local = null;
        
        switch(idLocal)
        {
            case 0:
                local = new LatLng(-5.833208, -35.202948);
                break;
            case 1:
                local = new LatLng(-5.834443,-35.2013296);
                break;
            case 2:
                local = new LatLng(-5.837442,-35.197231999999985);
                break;
            case 3:
                local = new LatLng(-5.839731,-35.19566099999997);
                break;
            case 4:
                local = new LatLng(-5.841738,-35.19520899999998);
                break;
            case 5:
                local = new LatLng(-5.84386,-35.19710700000002);
                break;
            case 6:
                local = new LatLng(-5.843595,-35.199810000000014);
                break;
            case 7:
                local = new LatLng(-5.842091,-35.203193);
                break;
            case 8:
                local = new LatLng(-5.839287,-35.20465200000001);
                break;
            case 9:
                local = new LatLng(-5.838308,-35.20543199999997);
                break;
            case 10:
                local = new LatLng(-5.840961,-35.20401300000003);
                break;
            case 11:
                local = new LatLng(-5.842246,-35.205940999999996);
                break;
            case 12:
                local = new LatLng(-5.843065,-35.207464000000016);
                break;
            case 13:
                local = new LatLng(-5.84192,-35.209554000000026);
                break;
            case 14:
                local = new LatLng(-5.83592,-35.21109000000001);
                break;
            case 15:
                local = new LatLng(-5.837918,-35.206316000000015);
                break;
            case 16:
                local = new LatLng(-5.838626,-35.20163400000001);
                break;
            case 17:
                local = new LatLng(-5.836503,-35.20188400000001);
                break;
            case 18:
                local = new LatLng(-5.836573,-35.201910999999996);
                break;
            case 19:
                local = new LatLng(-5.838502,-35.20141100000001);
                break;
            case 20:
                local = new LatLng(-5.84242,-35.203236000000004);
                break;
            case 21:
                local = new LatLng(-5.843799,-35.19986);
                break;
            case 22:
                local = new LatLng(-5.844047,-35.19737199999997);
                break;
            case 23:
                local = new LatLng(-5.841584,-35.19498799999997);
                break;
            case 24:
                local = new LatLng(-5.839508,-35.19548800000001);
                break;
            case 25:
                local = new LatLng(-5.837252,-35.197069);
                break;
            case 26:
                local = new LatLng(-5.83408,-35.20089300000001);
                break;
            case 27:
                local = new LatLng(-5.832006,-35.20429200000001);
                break;
            case 28:
                local = new LatLng(-5.832112,-35.20453199999997);
                break;
            default:
                local = new LatLng(-5.833208, -35.202948);
                break;
        }
        
        return local;
    }
    
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
    }
    
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    
}
