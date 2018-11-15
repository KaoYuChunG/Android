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
import com.example.xxx.tata.R;
import com.example.xxx.tata.Utils.ServiceGenerator;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayFaturaActivity extends AppCompatActivity {


    private Toolbar mToolbar;

    private EditText txtIdentificacao;
    private Button pagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setup();

        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pagarFatura();
            }
        });

    }

    private void setup(){
        setContentView(R.layout.activity_pay_fatura);
        pagar = (Button) findViewById(R.id.btnCadastrar_fatura_pay);
        txtIdentificacao = (EditText)findViewById(R.id.txtIdentificacao_fatura_pay);


        mToolbar = (Toolbar) findViewById(R.id.toolbarFaturaPay);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private String getId(){

        String id  = txtIdentificacao.getText().toString();

        return id;
    }

    private void descontaSaldo(){

    }


    private void pagarFatura() {
        String id = getId();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-senai-tcs.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        Call<HashMap<String, String>> call = api.pagarFatura(ServiceGenerator.TOKEN, id);
        call.enqueue(new Callback<HashMap<String, String>>() {

            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(response.isSuccessful()){
                    Toast.makeText
                            (
                                    PayFaturaActivity.this,
                                    response.body().get("msg"),
                                    Toast.LENGTH_LONG
                            ).show();
                    Toast.makeText
                            (
                                    PayFaturaActivity.this,
                                    "kao",
                                    Toast.LENGTH_LONG
                            ).show();
                }

            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(PayFaturaActivity.this, t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                Toast.makeText
                        (
                                PayFaturaActivity.this,
                                "kkkkao",
                                Toast.LENGTH_LONG
                        ).show();
            }
        });
    }
}
