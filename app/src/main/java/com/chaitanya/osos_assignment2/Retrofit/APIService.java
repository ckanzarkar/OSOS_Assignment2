package com.chaitanya.osos_assignment2.Retrofit;

import com.chaitanya.osos_assignment2.Pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    String BASE_URL = "https://jsonplaceholder.typicode.com/";


    @GET("users")
    Call<List<User>> getUsers();
}
