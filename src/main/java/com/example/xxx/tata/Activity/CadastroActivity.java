package com.example.xxx.tata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xxx.tata.API.API;
import com.example.xxx.tata.Models.Usuario;
import com.example.xxx.tata.R;
import com.example.xxx.tata.Utils.JsonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity {

    private EditText et_nome;
    private EditText et_email;
    private EditText et_senha;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        cadastrar = (Button) findViewById(R.id.btnCadastrar);
        et_nome = (EditText)findViewById(R.id.txtNome_cadastro);
        et_email = (EditText)findViewById(R.id.txtEmail_cadastro);
        et_senha = (EditText)findViewById(R.id.txtSenha_cadastro);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cadastrar();
            }
        });

    }

    private Usuario getUser(){
        Usuario user = new Usuario();
        user.nome = et_nome.getText().toString();
        user.email = et_email.getText().toString();
        user.senha = et_senha.getText().toString();

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        user.data_created = dataFormatada;
        user.data_login = dataFormatada;

        return user;
    }



    private void cadastrar() {

        Usuario user = getUser();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-senai-tcs.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getGson(Usuario.class)))
                .build();

        API api = retrofit.create(API.class);
        Call<HashMap<String, String>> call = api.registrarCadastro(user);

        call.enqueue(new Callback<HashMap<String, String>>() {

            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(response.isSuccessful()){
                    Toast.makeText
                            (
                                CadastroActivity.this,
                                    response.body().get("msg"),
                                    Toast.LENGTH_LONG
                            ).show();
                    Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(CadastroActivity.this, t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
