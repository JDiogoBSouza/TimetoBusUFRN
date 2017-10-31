package com.apps.diogo.timetobusufrn.Activities;

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
                
                UsuarioDAO dao = new UsuarioDAO( getApplicationContext() );
    
                String[] nomeCamposUser = new String[] {CriaBanco.MATRICULA, CriaBanco.SENHA, CriaBanco.NOME, CriaBanco.FOTO};
    
    
                // TODO: verificar conversao de string
                int iMatricula = Integer.parseInt( mMatriculaView.getText().toString() );
    
                Cursor cursorUser = dao.selectUsuarioByMatricula(iMatricula);
    
                int matricula = cursorUser.getInt( cursorUser.getColumnIndex(nomeCamposUser[0]) );
                String senha = cursorUser.getString( cursorUser.getColumnIndex(nomeCamposUser[1]) );
                String nome   = cursorUser.getString( cursorUser.getColumnIndex(nomeCamposUser[2]) );
                String foto   = cursorUser.getString( cursorUser.getColumnIndex(nomeCamposUser[3]) );
    
                Usuario usuario = new Usuario(matricula, senha, nome, foto);
    
                if( senhaView.equals( usuario.getSenha() ) )
                {
                    Toast.makeText(getApplicationContext(),"Login Bem Sucedido !", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Login Mal Sucedido !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

