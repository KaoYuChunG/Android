package com.example.xxx.tata.Models;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonRootName("users")
public class Pessoa implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("email")
    public String email;

    @SerializedName("name")
    public String nome;

    @SerializedName("telefone")
    public String telefone;

    @SerializedName("cpf")
    public String cpf;

    @SerializedName("saldo")
    public double saldo;

    @SerializedName("password")
    public String senha;

    @SerializedName("descricao")
    public String descricao;

    @SerializedName("picture")
    public String caminho;

    @SerializedName("created_at")
    public Date data_created;

    @SerializedName("last_login")
    public Date data_login;

    @SerializedName("faturas")
    public List<Fatura> faturas;
}
