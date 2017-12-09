package com.apps.diogo.timetobusufrn.Classes.Adapters.Timeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Usuario;
import com.apps.diogo.timetobusufrn.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Diogo on 19/10/2017.
 */

public class PostAdapter extends ArrayAdapter<Post>
{
    private final Context context;
    ArrayList<Post> posts = new ArrayList<Post>();
    
    public PostAdapter(Context context, ArrayList<Post> posts)
    {
        super(context, R.layout.postlayout, posts);
        this.context = context;
        this.posts = posts;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        final View rowView = inflater.inflate(R.layout.postlayout, parent, false);
    
        TextView textAutor = (TextView) rowView.findViewById(R.id.usuario);
        TextView textMatricula = (TextView) rowView.findViewById(R.id.matricula);
    
        TextView textHora = (TextView) rowView.findViewById(R.id.hora);
        TextView textSegundos = (TextView) rowView.findViewById(R.id.segundos);
        
        TextView textOnibus = (TextView) rowView.findViewById(R.id.onibus);
        TextView textParada = (TextView) rowView.findViewById(R.id.parada);
    
        ImageView imagemPost = (ImageView) rowView.findViewById(R.id.fotoAutor);
        ImageView imagemOnibus = (ImageView) rowView.findViewById(R.id.imagemOnibus);
        
        Post post = posts.get(position);
        final String comentario = post.getComentario();
    
        final ExpandableListView elvComment = (ExpandableListView) rowView.findViewById(R.id.elvComent);
        
        final comentExpandable adapter = new comentExpandable( context, post.getComentario() );
        elvComment.setAdapter(adapter);
        
        textAutor.setText( post.getUsuario().getNome() );
        textMatricula.setText( post.getUsuario().getSMatricula() );
        
        textOnibus.setText( post.getTipoOnibus() );
        textParada.setText( post.getNomeParada() );
        
        textHora.setText( post.getHora() );
        textSegundos.setText( post.getSegundos() );
        
        setImagemEmpresa(post.getEmpresaOnibus(), imagemOnibus);
        
        if( post.getUsuario().getFoto() != null )
        {
            //Toast.makeText(context, "Achou imagem no banco !", Toast.LENGTH_SHORT).show();
            
            byte[] fotoArray = post.getUsuario().getFoto();
            
            Bitmap raw  = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
            
            imagemPost.setImageBitmap(raw);
        }
    
        elvComment.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {
                if( !comentario.isEmpty() )
                    setParentListViewHeight(parent, groupPosition);
                
                return false;
            }
        });
        
        return rowView;
    }
    
    private void setParentListViewHeight(ExpandableListView listView, int group)
    {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        
        for (int i = 0; i < listAdapter.getGroupCount(); i++)
        {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            
            totalHeight += groupItem.getMeasuredHeight();
            
            if (((listView.isGroupExpanded(i)) && (i != group)) || ((!listView.isGroupExpanded(i)) && (i == group)))
            {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++)
                {
                    View listItem = listAdapter.getChildView(i, j, false, null, listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }
        
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        
        if (height < 10)
            height = 200;
        
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    
    private void setImagemEmpresa(int idEmpresa, ImageView imageOnibus)
    {
        String imageName;
        
        switch ( idEmpresa )
        {
            case 0:
                imageName = "guanabaralow";
                break;
            
            case 1:
                imageName = "viasullow";
                break;
            
            case 2:
                imageName = "conceicaolow";
                break;
            
            case 3:
                imageName = "cidadedonatallow";
                break;
            
            case 4:
                imageName = "reunidaslow";
                break;
            
            case 5:
                imageName = "santamarialow";
                break;
            
            default:
                imageName = "guanabaralow";
                break;
        }
        
        int resID = context.getResources().getIdentifier(imageName , "drawable", context.getPackageName());
        imageOnibus.setImageResource(resID);
    }
    
    public static boolean buscaImagemPerfil( ImageView ibageView, Context contexto, Usuario usuario )
    {
        String fileName = "thumb" + usuario.getMatricula();
        String local = contexto.getFilesDir().getPath()+ "/" + fileName + ".jpg";
        
        try
        {
            File f = new File(local);
            
            if( f.exists() )
            {
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                ibageView.setImageBitmap(b);
                return true;
            }
            else
                return false;
            
        }
        catch (FileNotFoundException e)
        {
            //e.printStackTrace();
            return false;
        }
    }
}
