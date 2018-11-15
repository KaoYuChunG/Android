package com.example.xxx.tata.Atapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xxx.tata.Interface.AdapterPositionOnClickListener;
import com.example.xxx.tata.Models.Pessoa;
import com.example.xxx.tata.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.viewHolder> {
    private Context mContext;
    private AdapterPositionOnClickListener adapterPositionOnClickListener;
    public List<Pessoa> mList;

    public UsuarioAdapter(Context context, List<Pessoa> list){
        this.mContext = context;
        this.mList = list;
    }

    public void setAdapterPositionOnClickListener(AdapterPositionOnClickListener click){
        adapterPositionOnClickListener = click;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View holder = inflater.inflate(R.layout.item_usuario, parent, false);
        viewHolder ViewHolder = new viewHolder(holder);

        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Pessoa pessoa = mList.get(position);

        String nome = String.valueOf(pessoa.nome);
        String email = String.valueOf(pessoa.email);
        String foto = String.valueOf(pessoa.caminho);



        holder.lbl_nome.setText(nome);
        holder.lbl_email.setText(email);

//        imagem

        Picasso.get().load(foto).into(holder.img_foto);



        ;
    }

    public Pessoa getPessoa(int positon){

        return  mList.get(positon);
    }


    public void deleteItem(int positon){
        mList.remove(positon);
        notifyItemRemoved(positon);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView lbl_nome;
        public TextView lbl_email;
        public CircleImageView img_foto;

//        public ImageButton btn_lixeira;

        public viewHolder(View itemView) {
            super(itemView);

            lbl_nome = itemView.findViewById(R.id.lblNome);
            lbl_email = itemView.findViewById(R.id.lblEmail);
            img_foto = itemView.findViewById(R.id.ImgPessoa);
//            btn_lixeira = itemView.findViewById(R.id.lixeira);

            //Aplica a função do click na botao lixeira
//            btn_lixeira.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }





        //Passar os parametros que vai ser executado na HistoryActivity
        @Override
        public void onClick(View view) {

            //Verificar se a ponte de contato entre o activity e o adapter foi passado
            if(adapterPositionOnClickListener != null){

//                final View v = view;
//                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                builder.setTitle("Confirmação")
//                        .setMessage("Tem certeza que deseja excluir este Histórico?")
//                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {

                //passar os parametros que vai ser exibido lá na history activity
                adapterPositionOnClickListener.setAdapterPositionOnClickListener(view, getAdapterPosition());
//                                 Snackbar.make(v, "Excluiu!", Snackbar.LENGTH_LONG)
//                                            .setAction("Action", null).show();
//                            }
//                        })
//                        .setNegativeButton("Cancelar", null)
//                        .create()
//                        .show();




            }
        }
    }


}
