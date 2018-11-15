package com.example.xxx.tata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.xxx.tata.API.API;
import com.example.xxx.tata.Atapter.UsuarioAdapter;
import com.example.xxx.tata.Interface.AdapterPositionOnClickListener;
import com.example.xxx.tata.Models.Pessoa;
import com.example.xxx.tata.R;
import com.example.xxx.tata.Utils.ServiceGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PessoasActivity extends AppCompatActivity implements AdapterPositionOnClickListener {

    private Toolbar mToolbar;
    private final int _HISTORICORESULTADO = 1234;
    private List<Pessoa> mList = new ArrayList<>();
    private UsuarioAdapter mAdapter;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        // toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


//        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mRecycler = findViewById(R.id.rv_usuarios);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(manager);
        mRecycler.setHasFixedSize(true);

        loadData();

    }
    private void validar(){
        if(mList == null || mList.isEmpty()){
            findViewById(R.id.lbl_message).setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
        }
        else {
            findViewById(R.id.lbl_message).setVisibility(View.INVISIBLE);
            mRecycler.setVisibility(View.VISIBLE);
        }
    }

    private void loadData(){
        Retrofit retrofit =  new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api-senai-tcs.herokuapp.com")
                .build();

        API api = retrofit.create(API.class);

        Call<List<Pessoa>> call = api.getUsuarios(ServiceGenerator.TOKEN);
        call.enqueue(new Callback<List<Pessoa>>() {
            @Override
            public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                if(response.isSuccessful()){
                    mList = response.body();
                    mAdapter = new UsuarioAdapter(PessoasActivity.this, mList);
                    mAdapter.setAdapterPositionOnClickListener(PessoasActivity.this);
                    mRecycler.setAdapter(mAdapter);



                }else{
                    Toast.makeText(PessoasActivity.this, response.message(),Toast.LENGTH_LONG).show();
                }

                //validar a lista se esta vazia
                validar();
            }

            @Override
            public void onFailure(Call<List<Pessoa>> call, Throwable t) {
                Toast.makeText(PessoasActivity.this, t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }

        });
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent();
        i.putExtra("lista", (Serializable) mAdapter.mList);

        //passar a lista atualizada como resultado
        setResult(RESULT_OK,i);
        finish();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
//            finish(); // close this activity and return to preview activity (if there is any)
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void setAdapterPositionOnClickListener(View view, int position) {

        Pessoa pessoa = mAdapter.getPessoa(position);
        Intent i = new Intent(PessoasActivity.this,
                PessoaActivity.class);
        i.putExtra("dado", pessoa);
        startActivity(i);


//        Toast.makeText(PessoasActivity.this, "kaoaooa"+position,Toast.LENGTH_LONG).show();

    }
}
