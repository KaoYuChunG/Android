package com.example.xxx.tata.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public class Fatura  implements Serializable {

    @SerializedName("codigo")
    public String id;


    @SerializedName("valor")
    public double saldo;

    @SerializedName("descricao")
    public String descricao;
}
