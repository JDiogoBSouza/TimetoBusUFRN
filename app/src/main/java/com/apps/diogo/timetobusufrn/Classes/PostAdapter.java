package com.apps.diogo.timetobusufrn.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        
        
        View rowView = inflater.inflate(R.layout.postlayout, parent, false);
    
        TextView textAutor = (TextView) rowView.findViewById(R.id.usuario);
        TextView textMatricula = (TextView) rowView.findViewById(R.id.matricula);
    
        TextView textHora = (TextView) rowView.findViewById(R.id.hora);
        TextView textSegundos = (TextView) rowView.findViewById(R.id.segundos);
        
        TextView textOnibus = (TextView) rowView.findViewById(R.id.onibus);
        TextView textParada = (TextView) rowView.findViewById(R.id.parada);
    
        ImageView imagemPost = (ImageView) rowView.findViewById(R.id.fotoAutor);
        
        Post post = posts.get(position);
        final String comentario = post.getComentario();
        
        Button botaoComent = (Button) rowView.findViewById(R.id.botaoComent);
        
        botaoComent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, comentario, Toast.LENGTH_SHORT).show();
            }
        });
        
        
        textAutor.setText( post.getUsuario().getNome() );
        textMatricula.setText( post.getUsuario().getSMatricula() );
        
        textOnibus.setText( post.getOnibus() );
        textParada.setText( post.getParada() );
        
        textHora.setText( post.getHora() );
        textSegundos.setText( post.getSegundos() );
    
        buscaImagemPerfil( imagemPost, context, post.getUsuario() );
        
        return rowView;
    }
    
    public static void buscaImagemPerfil( ImageView ibageView, Context contexto, Usuario usuario )
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
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}