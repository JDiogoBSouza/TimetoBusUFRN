package com.apps.diogo.timetobusufrn.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.Classes.Database.Facade;
import com.apps.diogo.timetobusufrn.Classes.Modelos.Usuario;
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
    
        // Pega o valor de "logado" no SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean logado = sharedPreferences.getBoolean("logado",false);
        
        // Caso o usuário ja tenha logado, fecha-se a tela de login e inicia a aplicação na tela principal
        if( logado )
        {
            //Toast.makeText(getApplicationContext(), "Ja Logado", Toast.LENGTH_SHORT).show();
            iniciaApp();
            finish();
        }
        else
        {
            final Context contexto = getApplicationContext();
    
            // Set up the login form.
            mMatriculaView = (AutoCompleteTextView) findViewById(R.id.email);
            mPasswordView = (EditText) findViewById(R.id.password);
    
            Button botaoEntrar = (Button) findViewById(R.id.botaoEntrar);
    
    
            // Atribuição do método onClick ao botão de "Entrar" na tela de Login.
            botaoEntrar.setOnClickListener(new OnClickListener()
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
                    
                            // Ao logar corretamente no app, as informações do usuário são armazenadas no SharedPreferences
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(contexto).edit();
                    
                            editor.putBoolean( "logado" , true );
                            editor.putString("nomeexibicao_text" , usuario.getNome() );
                            editor.putString("matricula" , usuario.getSMatricula() );
                            editor.putString("senha" , usuario.getSenha() );
                            editor.putInt("matriculaI", usuario.getMatricula());
                            //editor.putString("foto", usuario.getFoto());
                    
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
    }
    
    /**
     * Método chamado para iniciar a aplicação
     * @param user : Objeto Usuario a ser enviado na forma de Intent para a proxima tela do aplicativo.
     */
    private void iniciaApp(Usuario user)
    {
        Intent intentTimline = new Intent(LoginActivity.this, TimelineActivity.class);
    
        if( user != null )
            intentTimline.putExtra(Usuario.USER_INFO, user);
        
        startActivity(intentTimline);
    }
    
    /**
     * Método sem parametros para iniciar a aplicação.
     */
    private void iniciaApp()
    {
        iniciaApp( null );
    }
}

