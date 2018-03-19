package br.edu.iff.pooa20172.projeto_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    EditText email, senha;
    Button logar, cadastrar;
    Login login;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);

        logar = (Button) findViewById(R.id.logar);
        cadastrar = (Button) findViewById(R.id.cadastrar);

        realm = Realm.getDefaultInstance();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cadastrar();
            }
        });

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logar();
            }
        });

    }
    public void Cadastrar(){

        realm.beginTransaction();
        Login l = new Login();
        l.setEmail(email.getText().toString());
        l.setSenha(senha.getText().toString());

        realm.copyToRealm(l);
        realm.commitTransaction();
        realm.close();

        Toast.makeText(this, "Usuário " + email.getText().toString() + " Cadastrado!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void Logar(){

        realm.beginTransaction();

        Login res = realm.where(Login.class).equalTo("email", email.getText().toString()).findFirst();

        if(res.getEmail().equals(email.getText().toString())){
            Toast.makeText(this, "Usuário " + email.getText().toString() + " Logado!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        Toast.makeText(this, "Usuário " + email.getText().toString() + " não cadastrado!", Toast.LENGTH_LONG).show();
        this.finish();
    }
}

