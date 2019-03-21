package com.lazday.bukatokoonline.data.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static Retrofit getUrl(){
        return new Retrofit.Builder()
                .baseUrl("http://kursus-online.lazday.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getUrlRajaOngkir(String base_url){
        return new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
