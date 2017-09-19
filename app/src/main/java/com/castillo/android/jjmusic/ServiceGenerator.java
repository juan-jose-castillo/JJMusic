package com.castillo.android.jjmusic;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by juanjosecastillo on 13/9/17.
 */

public class ServiceGenerator {
    //https://api.themoviedb.org/3/movie/now_playing?language=es&api_key=ca4ece633f5836fb0383f
//http://ws.audioscrobbler.com/2.0/?method=
    private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}