package com.example.xxx.tata.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xxx.tata.API.API;
import com.example.xxx.tata.Models.Fatura;
import com.example.xxx.tata.R;
import com.example.xxx.tata.Utils.ServiceGenerator;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaturaActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText txtIdentificacao;
    private EditText txtFatura;
    private EditText txtDescricao;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setup();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cadastrarFatura();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setup() {
        setContentView(R.layout.activity_fatura);
        cadastrar = (Button) findViewById(R.id.btnCadastrar_fatura);
        txtIdentificacao = (EditText) findViewById(R.id.txtIdentificacao_fatura);
        txtFatura = (EditText) findViewById(R.id.txtFatura_fatura);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao_fatura);


        mToolbar = (Toolbar) findViewById(R.id.toolbarFatura);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private Fatura getFatura() {
        Fatura fatura = new Fatura();

        fatura.id = txtIdentificacao.getText().toString();
        String p = txtFatura.getText().toString();
        fatura.saldo = Double.valueOf(p);

        fatura.descricao = txtDescricao.getText().toString();

        return fatura;
    }


    private void cadastrarFatura() {
        Fatura fatura = getFatura();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-senai-tcs.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        Call<HashMap<String, String>> call = api.criarFatura(ServiceGenerator.TOKEN, fatura);
        call.enqueue(new Callback<HashMap<String, String>>() {

            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText
                            (
                                    FaturaActivity.this,
                                    response.body().get("msg"),
                                    Toast.LENGTH_LONG
                            ).show();

                }

            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(FaturaActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText
                        (
                                FaturaActivity.this,
                                "erro",
                                Toast.LENGTH_LONG
                        ).show();
            }
        });
    }

}
