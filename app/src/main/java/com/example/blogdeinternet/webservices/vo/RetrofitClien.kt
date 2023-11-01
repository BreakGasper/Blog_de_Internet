package com.breakapp.apv2.retrofit.vo

import com.breakapp.apv2.retrofit.WebServices
import com.example.blogdeinternet.system.configuration.GlobalConfig.Companion.ws_url
import com.example.blogdeinternet.system.configuration.LoggingInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClien {

    val loggingInterceptor = LoggingInterceptor()

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Añade el interceptor de logging aquí
        .build()

    val APServices by lazy {
        Retrofit.Builder()
            .baseUrl(ws_url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(WebServices::class.java)
    }




}