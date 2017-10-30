package com.apps.diogo.timetobusufrn.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.Classes.Database.PostDAO;
import com.apps.diogo.timetobusufrn.Classes.Database.UsuarioDAO;
import com.apps.diogo.timetobusufrn.Classes.PostAdapter;
import com.apps.diogo.timetobusufrn.Classes.Usuario;
import com.apps.diogo.timetobusufrn.Fragmentos.FragmentoNotificacoes;
import com.apps.diogo.timetobusufrn.Fragmentos.FragmentoTabs;
import com.apps.diogo.timetobusufrn.Fragmentos.TimelineFragment;
import com.apps.diogo.timetobusufrn.Classes.Post;
import com.apps.diogo.timetobusufrn.R;
import com.apps.diogo.timetobusufrn.Classes.Database.CriaBanco;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    
    AlertDialog dialog;
    Fragment fragmentoAtual;
    FloatingActionButton fab;
    Usuario usuario;
    String profileImage;
    
    protected static final int FOTO = 0;
    private static final String IMAGEM ="imagem" ;
    
    private Bitmap bitmap;
    CircleImageView imgView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final Context context = this;
        super.onCreate(savedInstanceState);
        
        usuario = new Usuario(1111, "ghg23213", "Diogo Souza", "enderecoFoto");
        
        setContentView(R.layout.activity_timeline);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    
        fab = (FloatingActionButton) findViewById(R.id.fab);
        
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TimelineActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_post, null);
                
                Spinner spinner = (Spinner) mView.findViewById(R.id.DLparada);
                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.paradas, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
    
                Spinner spinner2 = (Spinner) mView.findViewById(R.id.DLonibus);
                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context, R.array.onibus, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner2.setAdapter(adapter2);
                                
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });
        
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        
        drawer.setDrawerListener(toggle);
        
        toggle.syncState();
        
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        
        imgView = (CircleImageView) header.findViewById( R.id.fotoPerfil );
        
        imgView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                trocarImagem(view);
            }
        });
        
        atualizaNavHeader();
        
        fragmentoAtual = new FragmentoTabs();
        
        setFragment(fragmentoAtual);//init
    }
    
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }
    
    private void atualizaNavHeader()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        View header = nav.getHeaderView(0);
    
        TextView nomeExibicao = (TextView) header.findViewById(R.id.nome_de_exibicao);
        TextView matExibicao  = (TextView) header.findViewById(R.id.matricula_exibicao);
    
        String defaultValue = getResources().getString(R.string.pref_default_display_name);
        String defaultValueMt = getResources().getString(R.string.pref_default_matricula);
    
        String nomeSalvo = sharedPreferences.getString("nomeexibicao_text", defaultValue);
        //String matSalva  = sharedPreferences.getString("matricula_text", defaultValueMt);
    
        nomeExibicao.setText(nomeSalvo);
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        atualizaNavHeader();
        PostAdapter.buscaImagemPerfil( imgView, getApplicationContext(), usuario );
        //buscaPosts();
        //Toast.makeText(getApplicationContext(),"On Resume", Toast.LENGTH_SHORT).show();
    }
    
    public void trocarImagem(View v)
    {
        Intent intent = new Intent();
        
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        
        startActivityForResult(Intent.createChooser(intent,"Selecionar Foto"), FOTO);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case FOTO:
                
                if (resultCode == RESULT_OK)
                {
                    alterarFoto(data);
                }
            
                break;
        
            default:
                break;
        }
    }
    
    private void alterarFoto(Intent data)
    {
        Uri selectedImageUri = data.getData();
        FileOutputStream outputStream;
        Bitmap bitmap;
    
        Context contexto = getApplicationContext();
        
        String fileName = "thumb" + usuario.getMatricula();
        String local = contexto.getFilesDir().getPath() + "/" + fileName + ".jpg";
        
        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            outputStream = new FileOutputStream(local);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);
            outputStream.close();
            
            UsuarioDAO dao = new UsuarioDAO( contexto );
            dao.updateUsuario( usuario );
        }
        catch(Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        imgView.setImageURI( selectedImageUri );
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent sett = new Intent(TimelineActivity.this, SettingsActivity.class);
            startActivity(sett);
            
            return true;
        }
        else if(id == R.id.action_sobre)
        {
            Intent sobre = new Intent(TimelineActivity.this, SobreActivity.class);
            startActivity(sobre);
            
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
    
        if (id == R.id.nav_inicio)
        {
            if (!(fragmentoAtual instanceof FragmentoTabs))
            {
                fragmentoAtual = new FragmentoTabs();
                setFragment(fragmentoAtual);
                fab.setVisibility(View.VISIBLE);
            }
        }
        else if (id == R.id.nav_notificacoes)
        {
            if (!(fragmentoAtual instanceof FragmentoNotificacoes))
            {
                fragmentoAtual = new FragmentoNotificacoes();
                setFragment(fragmentoAtual);
                fab.setVisibility(View.INVISIBLE);
            }
        }
        else if (id == R.id.nav_logoff)
        {
            finish();
        }
    
        return true;
    }
    
    public void setFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_timeline, fragment);
            ft.commit();
        }
        
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    
    private void adicionarPostNoFrag( Post post )
    {
        List<Fragment> a  = fragmentoAtual.getChildFragmentManager().getFragments();
        
        if( a != null )
        {
            TimelineFragment atual = (TimelineFragment) a.get(0);
            atual.adicionarPost(post);
        }
    }
    
    public void postar(View v)
    {
        Spinner spinnerParada = (Spinner) dialog.findViewById(R.id.DLparada);
        Spinner spinnerOnibus = (Spinner) dialog.findViewById(R.id.DLonibus);
        EditText textComentarios = (EditText) dialog.findViewById(R.id.DLcomentarios);
    
        String postContent = textComentarios.getText().toString();
        String parada = spinnerParada.getSelectedItem().toString();
        String onibus = spinnerOnibus.getSelectedItem().toString();
        Post post = new Post(usuario, parada , onibus, postContent);
        
        PostDAO dao = new PostDAO( getApplicationContext() );
        
        if( dao.insertPost(post) != -1 )
        {
            adicionarPostNoFrag(post);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Erro de inserção SQLite", Toast.LENGTH_SHORT).show();
        }
        
        dialog.dismiss();
    }
    
    public void cancelar(View v)
    {
        Toast.makeText(getApplicationContext(), "Cancelando postagem", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
    
    
}