package com.example.anew;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://bpj.scf.mybluehost.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final API countryAPI = retrofit.create(API.class);

    public static API getCountryAPI(){
        return countryAPI;
    }

}
