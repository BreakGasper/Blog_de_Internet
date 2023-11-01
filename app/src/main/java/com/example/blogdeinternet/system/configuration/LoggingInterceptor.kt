import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()
        // Loguear la URL completa
        println("\nRequest URL: $url")

        val response = chain.proceed(request)
        val responseBody = response.body
        val jsonResponse = responseBody?.string()

        // Loguear el JSON de la respuesta
        println("\nResponse JSON: $jsonResponse")

        // Reconstruct the response body to prevent issues with subsequent readers
        val newResponseBody = jsonResponse?.toResponseBody(responseBody?.contentType())

        return response.newBuilder()
            .body(newResponseBody)
            .build()
    }
}
