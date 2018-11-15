package com.example.xxx.tata.Models;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonRootName("auth")
public class Autetica implements Serializable {

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String senha;
}
