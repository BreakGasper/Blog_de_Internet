package com.breakapp.apv2.retrofit.vo

import LoggingInterceptor
import com.breakapp.apv2.retrofit.WebServices
import com.example.blogdeinternet.system.configuration.GlobalConfig.Companion.ws_url
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object RetrofitClien {

    val loggingInterceptor = LoggingInterceptor()

    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })

    // Configura el TrustManager personalizado en OkHttpClient
    val client = OkHttpClient.Builder()
        .sslSocketFactory(
            SSLContext.getInstance("SSL").apply {
                init(null, trustAllCerts, SecureRandom())
            }.socketFactory,
            trustAllCerts[0] as X509TrustManager
        )
        .addInterceptor(loggingInterceptor)
        .build()

    val APServices by lazy {
        Retrofit.Builder()
            .baseUrl(ws_url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(WebServices::class.java)
    }




}