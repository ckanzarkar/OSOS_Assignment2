package com.chaitanya.osos_assignment2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static Retrofit instance = null;
    private final APIService myApiService;

    private Retrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder().baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApiService = retrofit.create(APIService.class);
    }

    public static synchronized Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit();
        }
        return instance;
    }

    public APIService getMyApiService() {
        return myApiService;
    }
}
