package com.example.xxx.tata.Models;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


@JsonRootName("user")
public class Usuario implements Serializable {

    @SerializedName("name")
    public String nome;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String senha;

    @SerializedName("telefone")
    public String telefone;

    @SerializedName("cpf")
    public String cpf;

    @SerializedName("saldo")
    public double saldo;

    @SerializedName("descricao")
    public String descricao;

    @SerializedName("picture")
    public String caminho;

    @SerializedName("created_at")
    public String data_created;

    @SerializedName("last_login")
    public String data_login;

    @SerializedName("faturas")
    public List<Fatura> faturas;


}
