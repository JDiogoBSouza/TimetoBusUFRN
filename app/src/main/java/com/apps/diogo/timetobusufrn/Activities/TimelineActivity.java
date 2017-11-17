package com.apps.diogo.timetobusufrn.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.Classes.Adapters.SpinnerAdapter;
import com.apps.diogo.timetobusufrn.Classes.Database.Facade;
import com.apps.diogo.timetobusufrn.Classes.Adapters.PostAdapter;
import com.apps.diogo.timetobusufrn.Classes.Modelos.OnibusSpinner;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Usuario;
import com.apps.diogo.timetobusufrn.Fragmentos.FragmentoNotificacoes;
import com.apps.diogo.timetobusufrn.Fragmentos.FragmentoTabs;
import com.apps.diogo.timetobusufrn.Fragmentos.TimelineFragment;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Post;
import com.apps.diogo.timetobusufrn.R;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    protected static final int FOTO = 0;
    
    AlertDialog dialog;
    Fragment fragmentoAtual;
    FloatingActionButton fab;
    Usuario usuario;
    
    Context context;
    Facade fac;
    CircleImageView imgView;
    
    // Ciclos de Vida Importantes
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
    
            if (intent.getExtras() != null)
            {
                // Inicializa usuário a partir do Intent
                usuario = (Usuario) intent.getSerializableExtra(Usuario.USER_INFO);
            }
            else
            {
                // Inicializa usuário a partir das informações salvas no SharedPreferences
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
    
    // ---------------------------------------------
    
    /**
     * Metodo para criar um Objeto usuário.
     * @return Usuário criado a partir das informações presentes no SharedPreferences.
     */
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
    
    /**
     * Método que instancia todos os itens presentes na activity da Timeline
     */
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
        
        // Instancia o imageView da foto de perfil, seta metodo onClick do mesmo.
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
    
    /**
     * Método que verifica se o usuário está logado. Caso contrário, finaliza a activity da Timeline
     * e volta para a tela de login.
     * @return : false caso o usuário não esteja logado, true caso o usuário esteja logado.
     */
    @NonNull
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
    
    /**
     * Atualiza os dados 'Nome de Exibição' e 'Matricula' presentes na NavHeader
     * com as informações presentes no SharedPreferences.
     */
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
    
    /**
     * Instancia e seta o método onClick do botão flutuante utilizado para postar.
     */
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
    
    /**
     * Cria uma Janela de Dialogo personalizada onde o usuário irá selecionar
     * as informações da sua postagem.
     * @return : Janela de dialogo personalizada funcionando com layout pre-definido.
     */
    private AlertDialog createCustomDialog()
    {
        final Activity m = this;
        
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TimelineActivity.this);
        
        View mView = getLayoutInflater().inflate(R.layout.dialog_post, null);
        
        // Preenche o Spinner das Paradas
        final Spinner spinner = (Spinner) mView.findViewById(R.id.DLparada);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.paradas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        final Spinner spinner2 = (Spinner) mView.findViewById(R.id.DLonibus);
        final Spinner spinner3 = (Spinner) mView.findViewById(R.id.DLempresas);
    
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                ArrayAdapter<CharSequence> adapter2;
                
                //Toast.makeText(getApplicationContext(), "Arg2 = " + arg2, Toast.LENGTH_SHORT).show();
                
                switch( arg2 )
                {
                    case 0:
                        // Todos os Onibus, exceto o expresso CeT, param na reitoria.
                        adapter2 = ArrayAdapter.createFromResource(context, R.array.onibusReitoria, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);
                        break;
    
                    case 5:
                        // Todos os Onibus param no via direta.
                        adapter2 = ArrayAdapter.createFromResource(context, R.array.onibusViaDireta, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);
                        break;
                    
                    case 9:
                        // Todos os Onibus, exceto o expresso reitoria, param na ECT.
                        adapter2 = ArrayAdapter.createFromResource(context, R.array.onibusCET, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);
                    break;
                    
                    default:
                        // Apenas diretos e inversos param nas outras paradas.
                        adapter2 = ArrayAdapter.createFromResource(context, R.array.onibusComuns, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);
                    break;
                }
                
                /*
                    0 - Reitoria
                    1 - Anel Viario
                    2 - Saida UFRN
                    3 - Bar de Mae
                    4 - Capela
                    5 - Via Direta
                    6 - Deart
                    7 - Esc. Musica
                    8 - CB
                    9 - ECT
                 */
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
            }
        });
        
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                ArrayList<OnibusSpinner> list = new ArrayList<>();
                
                int posicaoSpinner1 = spinner.getSelectedItemPosition();
                
                //Toast.makeText(getApplicationContext(), "Spinner1 = " + posicaoSpinner1 , Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Spinner2 = " + arg2, Toast.LENGTH_SHORT).show();
            
                switch( posicaoSpinner1 )
                {
                    case 0:
                        switch (arg2)
                        {
                            case 0:
                                // Empresas dos Diretos.
                                list.add(new OnibusSpinner("Guanabara",0));
                                list.add(new OnibusSpinner("Via Sul",1));
                                break;
                            
                            case 1:
                                // Empresas dos Inversos.
                                list.add(new OnibusSpinner("Conceição",2));
                                list.add(new OnibusSpinner("Cidade do Natal",3));
                                break;
                            
                            case 2:
                                // Empresas dos Expressos Reitoria.
                                list.add(new OnibusSpinner("Reunidas",4));
                                list.add(new OnibusSpinner("Santa Maria",5));
                                break;
                        }
                    break;
                
                    case 5:
                        switch (arg2)
                        {
                            case 0:
                                // Empresas dos Diretos.
                                list.add(new OnibusSpinner("Guanabara",0));
                                list.add(new OnibusSpinner("Via Sul",1));
                                break;
        
                            case 1:
                                // Empresas dos Inversos.
                                list.add(new OnibusSpinner("Conceição",2));
                                list.add(new OnibusSpinner("Cidade do Natal",3));
                                break;
    
                            case 2:
                                // Empresas dos Expressos CeT.
                                list.add(new OnibusSpinner("Guanabara",0));
                                break;
                            
                            case 3:
                                // Empresas dos Expressos Reitoria.
                                list.add(new OnibusSpinner("Reunidas",4));
                                list.add(new OnibusSpinner("Santa Maria",5));
                                break;
                        }
                    break;
                
                    case 9:
                        switch (arg2)
                        {
                            case 0:
                                // Empresas dos Diretos.
                                list.add(new OnibusSpinner("Guanabara",0));
                                list.add(new OnibusSpinner("Via Sul",1));
                                break;
        
                            case 1:
                                // Empresas dos Inversos.
                                list.add(new OnibusSpinner("Conceição",2));
                                list.add(new OnibusSpinner("Cidade do Natal",3));
                                break;
        
                            case 2:
                                // Empresas dos Expressos CeT.
                                list.add(new OnibusSpinner("Guanabara",0));
                                break;
                        }
                    break;
                
                    default:
                        switch (arg2)
                        {
                            case 0:
                                // Empresas dos Diretos.
                                list.add(new OnibusSpinner("Guanabara",0));
                                list.add(new OnibusSpinner("Via Sul",1));
                                break;
        
                            case 1:
                                // Empresas dos Inversos.
                                list.add(new OnibusSpinner("Conceição",2));
                                list.add(new OnibusSpinner("Cidade do Natal",3));
                                break;
                        }
                    break;
                }
    
                SpinnerAdapter adapter3 = new SpinnerAdapter(m, R.layout.spinner_layout, R.id.txt, list);
                spinner3.setAdapter(adapter3);
                
                /*
                    0 - Reitoria
                    1 - Anel Viario
                    2 - Saida UFRN
                    3 - Bar de Mae
                    4 - Capela
                    5 - Via Direta
                    6 - Deart
                    7 - Esc. Musica
                    8 - CB
                    9 - ECT
                 */
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            
            }
        });
        
        mBuilder.setView(mView);
        
        return mBuilder.create();
    }
    
    /**
     * Inicia uma janela para a seleção de uma imagem.
     * @param v : View que chamou o método.
     */
    public void trocarImagem(View v)
    {
        Intent intent = new Intent();
        
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        
        // Código para poder cortar a imagem
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
            
        startActivityForResult(Intent.createChooser(intent,"Selecionar Foto"), FOTO);
    }
    
    /**
     * Transfere uma cópia comprimida da imagem selecionada para a pasta do aplicativo e
     * atribui a imagem ao imageView da foto de perfil.
     * @param data : Intent retornada pela janela de seleção de imagem.
     */
    private void alterarFoto(Intent data)
    {
        Bundle extras2 = data.getExtras();
        Bitmap photo = null;
        
        if (extras2 != null)
        {
            photo = extras2.getParcelable("data");
        }
        
        FileOutputStream outputStream;
        
        String fileName = "thumb" + usuario.getMatricula();
        String local = context.getFilesDir().getPath() + "/" + fileName + ".jpg";
        
        try
        {
            outputStream = new FileOutputStream(local);
            
            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            
            if( photo != null )
            {
                Bitmap diminuida = Bitmap.createScaledBitmap( photo, 96, 96, true );
                
                diminuida.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
                diminuida.compress(Bitmap.CompressFormat.JPEG,100, saida);
                
                imgView.setImageBitmap(diminuida);
    
                byte[] img = saida.toByteArray();
                usuario.setFoto(img);
    
                saida.close();
                outputStream.close();
    
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString("foto", local);
                editor.commit();
    
                fac.updateUsuario( usuario, context );
                
            }
              
        }
        catch(Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Seta um fragmento para ser exibido dentro da activity.
     * @param fragment : Fragmento a ser exibido.
     */
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
    
    /**
     * Atualiza a lista de postagens na tela inicial.
     */
    private void atualizarPostsFrag()
    {
        List<Fragment> a  = fragmentoAtual.getChildFragmentManager().getFragments();
        
        if( a != null )
        {
            TimelineFragment atual = (TimelineFragment) a.get(0);
            atual.buscaPosts();
        }
    }
    
    /**
     * Insere uma postagem no banco de dados e atualizar timeline.
     * @param v : View que chamou o método.
     */
    public void postar(View v)
    {
        Spinner spinnerParada = (Spinner) dialog.findViewById(R.id.DLparada);
        Spinner spinnerTipoOnibus = (Spinner) dialog.findViewById(R.id.DLonibus);
        Spinner spinnerEmpresaOnibus = (Spinner) dialog.findViewById(R.id.DLempresas);
        
        EditText textComentarios = (EditText) dialog.findViewById(R.id.DLcomentarios);
    
        String postContent = textComentarios.getText().toString();
        String parada = spinnerParada.getSelectedItem().toString();
        
        OnibusSpinner a = (OnibusSpinner) spinnerEmpresaOnibus.getSelectedItem();
        
        String tipoOnibus = spinnerTipoOnibus.getSelectedItem().toString();
        int empresaOnibus = a.getIdEmpresa();
        
        Post post = new Post(usuario, parada , tipoOnibus, empresaOnibus, postContent);
        
        if( fac.inserirPost(post) )
        {
            atualizarPostsFrag();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Falha na Postagem", Toast.LENGTH_SHORT).show();
        }
        
        dialog.dismiss();
    }
    
    /**
     * Método chamado ao clicar no botão cancelar na janela de dialogo personalizada.
     * @param v : View que chamou o método.
     */
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
    
    /**
     * Método que gerencia o comportamento da aplicação ao se clicar em algum dos
     * botões da barra lateral.
     * @param item : Item clicado na barra lateral
     * @return
     */
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
    
    /**
     * Método que gerencia o comportamento da aplicação ao se clicar em algum dos
     * botões do menu na appBar.
     * @param item
     * @return
     */
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
    
    /**
     * Método que gerencia o comportamento da aplicação ao receber o resultado
     * de alguma Activity.
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    
}