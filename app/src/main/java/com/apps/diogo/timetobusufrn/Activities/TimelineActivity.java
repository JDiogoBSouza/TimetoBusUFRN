package com.apps.diogo.timetobusufrn.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.Classes.Database.Geral.Facade;
import com.apps.diogo.timetobusufrn.Classes.Database.Timeline.PostDAO;
import com.apps.diogo.timetobusufrn.Classes.Adapters.PostAdapter;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Usuario;
import com.apps.diogo.timetobusufrn.Fragmentos.FragmentoNotificacoes;
import com.apps.diogo.timetobusufrn.Fragmentos.FragmentoTabs;
import com.apps.diogo.timetobusufrn.Fragmentos.TimelineFragment;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;
import com.apps.diogo.timetobusufrn.R;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    
    protected static final int LOGADO = 0;
    AlertDialog dialog;
    Fragment fragmentoAtual;
    FloatingActionButton fab;
    Usuario usuario;
    String profileImage;
    
    Context context;
    
    Facade fac;
    
    protected static final int FOTO = 0;
    private static final String IMAGEM ="imagem" ;
    
    private Bitmap bitmap;
    CircleImageView imgView;
    
    // Ciclo de Vida Importante
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        
        if( verificaLogado() )
        {
    
            fac = new Facade(context);
    
            Intent intent = getIntent();
    
            if (intent.getExtras() != null) {
                //Toast.makeText(getApplicationContext(), "TINHA EXTRAAAS", Toast.LENGTH_SHORT).show();
                usuario = (Usuario) intent.getSerializableExtra(Usuario.USER_INFO);
            } else {
                //Toast.makeText(getApplicationContext(), "NAO TINHA EXTRAAAS", Toast.LENGTH_SHORT).show();
                usuario = criaUsuario();
            }
    
            instanciaItens();
            atualizaNavHeader();
    
            fragmentoAtual = new FragmentoTabs();
            setFragment(fragmentoAtual);
            
        }
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        atualizaNavHeader();
        PostAdapter.buscaImagemPerfil( imgView, getApplicationContext(), usuario );
    }
    
    // -------------------------
    
    private Usuario criaUsuario()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        
        int matricula = sharedPreferences.getInt("matriculaI", 0);
        String nome = sharedPreferences.getString("nomeexibicao_text", "Nome de Exibicao");
        String senha = sharedPreferences.getString("senha", "...");
        //String foto = sharedPreferences.getString("foto", "...");
        byte[] foto = null;
        
        Usuario user = new Usuario(matricula, senha, nome, foto);
        
        return user;
    }
    
    private void instanciaItens()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    
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
        
        initFloatingActionButton();
    }
    
    private Boolean verificaLogado()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean logado = sharedPreferences.getBoolean("logado",false);
    
        if( !logado )
        {
            Toast.makeText(getApplicationContext(), "NAO Logado", Toast.LENGTH_SHORT).show();
            
            Intent intentLogin = new Intent(TimelineActivity.this, LoginActivity.class);
            startActivity(intentLogin);
            
            finish();
            
            return false;
        }
        
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
        String matSalva  = sharedPreferences.getString("matricula", defaultValueMt);
        
        nomeExibicao.setText(nomeSalvo);
        matExibicao.setText(matSalva);
    }
    
    private void initFloatingActionButton()
    {
        fab = (FloatingActionButton) findViewById(R.id.fab);
    
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog = createCustomDialog();
                dialog.show();
            }
        });
    }
    
    private AlertDialog createCustomDialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TimelineActivity.this);
        
        View mView = getLayoutInflater().inflate(R.layout.dialog_post, null);
        
        // Preenche o Spinner das Paradas
        Spinner spinner = (Spinner) mView.findViewById(R.id.DLparada);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.paradas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        // Preenche o Spinner dos Onibus
        Spinner spinner2 = (Spinner) mView.findViewById(R.id.DLonibus);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context, R.array.onibus, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        
        // TODO: Criar e preencher o Spinner das cores.
        
        mBuilder.setView(mView);
        
        return mBuilder.create();
    }
    
    public void trocarImagem(View v)
    {
        Intent intent = new Intent();
        
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        
        startActivityForResult(Intent.createChooser(intent,"Selecionar Foto"), FOTO);
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
            
            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG,5,saida);
            
            byte[] img = saida.toByteArray();
            usuario.setFoto(img);
            
            saida.close();
            outputStream.close();
    
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(contexto).edit();
            editor.putString("foto", local);
            editor.commit();
            
            fac.updateUsuario( usuario, contexto );
        }
        catch(Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        imgView.setImageURI( selectedImageUri );
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
    
    // Métodos do Ciclo de Vida
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }
    
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
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putBoolean( "logado" , false );
            editor.commit();
            
            Intent intentLogin = new Intent(TimelineActivity.this, LoginActivity.class);
            startActivity(intentLogin);
            
            
            finish();
        }
        else if (id == R.id.nav_fechar)
        {
            finish();
        }
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        
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
    
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    
}