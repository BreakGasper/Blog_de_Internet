package com.example.blogdeinternet.system.configuration

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()
        // Loguea la URL completa
        println("Request URL: $url")

        val response = chain.proceed(request)
        val responseBody = response.body
        val jsonResponse = responseBody?.string()

        // Loguea el JSON de la respuesta
        println("Response JSON: $jsonResponse")

        // Reconstruct the response body to prevent issues with subsequent readers
        val newResponseBody = jsonResponse?.toResponseBody(responseBody?.contentType())

        // Crea una nueva respuesta con el JSON modificado en el cuerpo
        return response.newBuilder()
            .body(newResponseBody)
            .build()
    }
}
