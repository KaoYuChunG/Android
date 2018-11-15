package com.example.xxx.tata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xxx.tata.API.API;
import com.example.xxx.tata.Models.Autetica;
import com.example.xxx.tata.Models.Usuario;
import com.example.xxx.tata.R;
import com.example.xxx.tata.Utils.JsonUtils;
import com.example.xxx.tata.Utils.ServiceGenerator;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private final int _AUTH = 1234;

    private  EditText txtemail;
    private  EditText txtsenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button acessar = (Button) findViewById(R.id.btnAacessar);
        txtemail = (EditText) findViewById(R.id.txtNome_login);
        txtsenha = (EditText) findViewById(R.id.txtSenha_login);

        acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                logar();
            }
        });

        findViewById(R.id.btnEsquecerSenha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);

            }
        });


    }


    private Autetica getLogin(){
        Autetica login = new Autetica();
        login.email = txtemail.getText().toString();
        login.senha = txtsenha.getText().toString();

        return login;
    }


    //consultar API
    private void logar(){

        Autetica auth = getLogin();
        Retrofit retrofit =  new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getGson(Autetica.class)))
                .baseUrl("https://api-senai-tcs.herokuapp.com/")
                .build();
//
        API api = retrofit.create(API.class);
//
        Call<HashMap<String, String>> call = api.getUsuarioLogin(auth);
        call.enqueue(new Callback<HashMap<String, String>>() {

            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(response.isSuccessful()){
                    Toast.makeText
                            (
                                    LoginActivity.this,
                                    response.body().get("jwt"),
                                    Toast.LENGTH_LONG
                            ).show();
                    ServiceGenerator.TOKEN = response.body().get("jwt");

                    loginUsuario( ServiceGenerator.TOKEN);

                }

            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginUsuario(String auth){

        Retrofit retrofit =  new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api-senai-tcs.herokuapp.com")
                .build();

        API api = retrofit.create(API.class);
        Call<Usuario> call = api.getUser(auth);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){

                    ServiceGenerator.USUARIO = response.body();
                    Intent i = new Intent(LoginActivity.this, SreenActivity.class);

                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
}
