package com.example.xxx.tata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xxx.tata.API.API;
import com.example.xxx.tata.Models.Usuario;
import com.example.xxx.tata.R;
import com.example.xxx.tata.Utils.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SreenActivity extends AppCompatActivity {

    private LinearLayout btnNotificacao;
    private LinearLayout btnPerfil;
    private LinearLayout btnPessoas;
    private LinearLayout btnAnotacao;
    private TextView lblUsuario;
    private TextView lblSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sreen);


        btnNotificacao = (LinearLayout) findViewById(R.id.btn_gerar_fatura);
        btnPerfil = (LinearLayout) findViewById(R.id.btn_perfil);
        btnPessoas = (LinearLayout) findViewById(R.id.btn_pessoas);
        btnAnotacao = (LinearLayout) findViewById(R.id.btn_paga_fatura);
        lblUsuario = (TextView) findViewById(R.id.lblUsuario);
        lblSaldo = (TextView) findViewById(R.id.lbl_saldoSreen);




        if(ServiceGenerator.USUARIO.saldo == 0){
            lblSaldo.setText("R$ 0,00");
        }else{
            lblSaldo.setText("R$ "+ServiceGenerator.USUARIO.saldo);
        }

        lblUsuario.setText(ServiceGenerator.USUARIO.nome);





        btnPessoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(SreenActivity.this, PessoasActivity.class);
                startActivity(i);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SreenActivity.this, PerfilActivity.class);
                startActivity(i);

            }
        });

        btnNotificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SreenActivity.this, FaturaActivity.class);
                startActivity(i);

            }
        });

        btnAnotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SreenActivity.this, PayFaturaActivity.class);
                startActivity(i);


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        loginUsuario();
    }

    private void loginUsuario(){

        Retrofit retrofit =  new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api-senai-tcs.herokuapp.com")
                .build();

        API api = retrofit.create(API.class);
        Call<Usuario> call = api.getUser(ServiceGenerator.TOKEN);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){

                    ServiceGenerator.USUARIO = response.body();
                    lblSaldo.setText("R$ "+ServiceGenerator.USUARIO.saldo);

                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
}
