package com.apps.diogo.timetobusufrn.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.database.Cursor;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.Classes.Database.CriaBanco;
import com.apps.diogo.timetobusufrn.Classes.Database.Facade;
import com.apps.diogo.timetobusufrn.Classes.Database.UsuarioDAO;
import com.apps.diogo.timetobusufrn.Classes.Usuario;
import com.apps.diogo.timetobusufrn.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{
    // UI references.
    private AutoCompleteTextView mMatriculaView;
    private EditText mPasswordView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean logado = sharedPreferences.getBoolean("logado",false);
        
        if( logado )
        {
            //Toast.makeText(getApplicationContext(), "Ja Logado", Toast.LENGTH_SHORT).show();
            iniciaApp();
            finish();
        }
        else
        {
            // TODO: SOMETHING
            //Toast.makeText(getApplicationContext(), "Nao logado ainda", Toast.LENGTH_SHORT).show();
        }
        
        final Context contexto = getApplicationContext();
        
        // Set up the login form.
        mMatriculaView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        
        mEmailSignInButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String senhaView = mPasswordView.getText().toString();
                
                Facade fac = new Facade( contexto );
                Usuario usuario = fac.getUsuarioByMatriculaS( mMatriculaView.getText().toString() );
                
                if( usuario != null )
                {
                    if (senhaView.equals(usuario.getSenha()))
                    {
                        Toast.makeText(getApplicationContext(), "Login Bem Sucedido !", Toast.LENGTH_SHORT).show();
                        
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(contexto).edit();
                        
                        editor.putBoolean( "logado" , true );
                        
                        editor.putString("nomeexibicao_text" , usuario.getNome() );
                        editor.putString("matricula" , usuario.getSMatricula() );
                        editor.putString("senha" , usuario.getSenha() );
                        editor.putInt("matriculaI", usuario.getMatricula());
                        editor.putString("foto", usuario.getFoto());
                        
                        editor.commit();
                        
                        iniciaApp(usuario);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Login Mal Sucedido !", Toast.LENGTH_SHORT).show();
                        mPasswordView.setText("");
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Login Mal Sucedido !", Toast.LENGTH_SHORT).show();
                    mPasswordView.setText("");
                }
            }
        });
    }
    
    private void iniciaApp(Usuario user)
    {
        Intent intentTimline = new Intent(LoginActivity.this, TimelineActivity.class);
    
        if( user != null )
            intentTimline.putExtra(Usuario.USER_INFO, user);
        
        startActivity(intentTimline);
    }
    
    private void iniciaApp()
    {
        iniciaApp( null );
    }
}

