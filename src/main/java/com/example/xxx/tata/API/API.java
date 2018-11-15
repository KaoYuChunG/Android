package com.example.xxx.tata.API;

import com.example.xxx.tata.Models.Autetica;
import com.example.xxx.tata.Models.Fatura;
import com.example.xxx.tata.Models.Pessoa;
import com.example.xxx.tata.Models.Usuario;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    @POST("/user_token")
    public Call<HashMap<String, String>> getUsuarioLogin(@Body Autetica user);

    @POST("/users/create")
    public Call<HashMap<String, String>> registrarCadastro(@Body Usuario user);

/////////////////////////////////////
    @GET("/users")
    public Call<List<Pessoa>> getUsuarios(@Header("Authorization") String authorization);

    @GET("/users/current")
    Call<Usuario> getUser(@Header("Authorization") String token);

    @POST("/faturas/create")
    public Call<HashMap<String, String>> criarFatura(@Header("Authorization") String token, @Body Fatura fatura);

    @GET("/faturas/pay/{id}")
    public Call<HashMap<String, String>> pagarFatura(@Header("Authorization") String token, @Path("id") String id);

    @PATCH("/user/update")
    public Call<HashMap<String, String>> updatePessoa(@Header("Authorization") String token, @Body Usuario usuario);


}
