package com.example.xxx.tata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.xxx.tata.Models.Pessoa;
import com.example.xxx.tata.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PessoaActivity extends AppCompatActivity {

     private Toolbar mToolbar;
     private CircleImageView image;
     private TextView nome_image;
     private TextView saldo;
     private TextView email;
     private TextView cpf;
     private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();

        Intent i = getIntent();
        Pessoa pessoa = (Pessoa) i.getSerializableExtra("dado");
        nome_image.setText(pessoa.nome);
        saldo.setText(Double.toString(pessoa.saldo));
        email.setText(pessoa.email);
        cpf.setText(pessoa.cpf);
        descricao.setText(pessoa.descricao);

        Picasso.get().load(pessoa.caminho).into(image);




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
    private void setup(){
        setContentView(R.layout.activity_pessoa);

//        ligando elementos
        image = (CircleImageView)findViewById(R.id.CirImagemPessoa);
        nome_image = (TextView) findViewById(R.id.lbl_imagem);
        saldo = (TextView) findViewById(R.id.lbl_saldo);
        email =  (TextView) findViewById(R.id.lbl_email);
        cpf =  (TextView) findViewById(R.id.lbl_cpf);
        descricao =  (TextView) findViewById(R.id.lbl_descricao);

        mToolbar = (Toolbar) findViewById(R.id.toolbarPessoa);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }
}
