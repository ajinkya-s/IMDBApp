package com.example.networkmodule.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    fun <T> createService(serviceClass: Class<T>): T {
        var client: OkHttpClient = OkHttpClient.Builder().build()
        var retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
        return retrofit.create(serviceClass)
    }
}