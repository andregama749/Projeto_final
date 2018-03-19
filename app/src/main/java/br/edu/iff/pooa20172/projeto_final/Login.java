package br.edu.iff.pooa20172.projeto_final;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Login extends RealmObject{
    @PrimaryKey
    private String email;
    private String senha;

    public Login(){}

    public Login(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}
}
