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
import com.example.xxx.tata.Models.Usuario;
import com.example.xxx.tata.R;
import com.example.xxx.tata.Utils.JsonUtils;
import com.example.xxx.tata.Utils.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText et_nome;
    private EditText et_email;
    private EditText et_senha;
    private EditText et_fone;
    private EditText et_cpf;
    private EditText et_saldo;
    private EditText et_descricao;
    private CircleImageView image;


    private Button atualizar;


    private void setup(){

        setContentView(R.layout.activity_perfil);
        atualizar = (Button) findViewById(R.id.btnCadastrar);
        et_nome = (EditText)findViewById(R.id.txtnome_perfil);
        et_email = (EditText)findViewById(R.id.txtemail_perfil);
        et_senha = (EditText)findViewById(R.id.txtsenha_perfil);
        et_fone = (EditText)findViewById(R.id.txttelefone_perfil);
        et_saldo = (EditText)findViewById(R.id.txtsaldo_perfil);
        et_cpf = (EditText)findViewById(R.id.txtcpf_perfil);
        et_descricao = (EditText)findViewById(R.id.txtdescricao_perfil);
        atualizar = (Button)findViewById(R.id.btnAtualizar);
        image = (CircleImageView)findViewById(R.id.CirImagemPessoa);

        mToolbar = (Toolbar) findViewById(R.id.toolbarPerfil);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    private void setPessoa(){
        Usuario pessoa = ServiceGenerator.USUARIO;
        et_nome.setText(pessoa.nome);
        et_email.setText(pessoa.email);
        et_senha.setText(pessoa.senha);
        et_fone.setText(pessoa.telefone);
        et_saldo.setText(Double.toString(pessoa.saldo));
        et_cpf.setText(pessoa.cpf);
        et_descricao.setText(pessoa.descricao);
        Picasso.get().load(pessoa.caminho).into(image);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();

        setPessoa();

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                atualizarPerfil();
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

    private Usuario getPessoa() {
        Usuario pessoa = new Usuario();

        pessoa.nome = et_nome.getText().toString();
        pessoa.email = et_email.getText().toString();
        pessoa.senha = et_senha.getText().toString();
        pessoa.descricao = et_descricao.getText().toString();
        pessoa.telefone = et_fone.getText().toString();
        pessoa.cpf = et_cpf.getText().toString();
        String p = et_saldo.getText().toString();
        pessoa.saldo = Double.valueOf(p);

        return pessoa;
    }


    private void atualizarPerfil() {
        Usuario pessoa = getPessoa();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-senai-tcs.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getGson(Usuario.class)))
                .build();

        API api = retrofit.create(API.class);

        Call<HashMap<String, String>> call = api.updatePessoa(ServiceGenerator.TOKEN, pessoa);
        call.enqueue(new Callback<HashMap<String, String>>() {

            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText
                            (
                                    PerfilActivity.this,
                                    response.body().get("msg"),
                                    Toast.LENGTH_LONG
                            ).show();

                }

            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(PerfilActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText
                        (
                                PerfilActivity.this,
                                "erro",
                                Toast.LENGTH_LONG
                        ).show();
            }
        });
    }

}
