package com.example.appsicenet.data

import android.content.Context
import android.util.Log
import com.example.appsicenet.data.database.LocalDataSource
import com.example.appsicenet.data.database.SicenetDatabase
import com.example.appsicenet.network.AddCookiesInterceptor
import com.example.appsicenet.network.ReceivedCookiesInterceptor
import com.example.appsicenet.network.SICENETApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

interface AppContainer {
    val sicenetRepository : SicenetRepository
    val localDataSource: LocalDataSource // Agregar LocalDataSource al AppContainer
}
class DefaultAppContainer(context: Context): AppContainer {
    private val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx"
    //Obtención de cookies para la obtención de información
    private val client = OkHttpClient.Builder()
        .addInterceptor(AddCookiesInterceptor(context))
        .addInterceptor(ReceivedCookiesInterceptor(context))
        //.addInterceptor(DeleteSessionCookies(context))
        .addInterceptor(createLoggingInterceptor())
        .build()

    private fun createLoggingInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val requestHeaders = request.headers
            for (i in 0 until requestHeaders.size) {
                Log.d("HEADER", "${requestHeaders.name(i)}: ${requestHeaders.value(i)}")
            }
            Log.d("Solicitud", "URL: ${request.url}")
            Log.d("Solicitud", "Método: ${request.method}")
            Log.d("Solicitud", "Cuerpo: ${request.body}")
            val response = chain.proceed(request)
            val responseBody = response.body?.string()
            Log.d("Respuesta", "Código: ${response.code}")
            //Log.d("Respuesta", "Cuerpo: ${response.body?.string()}")

            Log.d("Respuesta", "Cuerpo: $responseBody")

            response.newBuilder()
                .body(responseBody?.toResponseBody(response.body?.contentType()))
                .build()
        }
    }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy())))
        .client(client)
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: SICENETApiService by lazy {
        retrofit.create(SICENETApiService::class.java)
    }
    override val localDataSource: LocalDataSource by lazy {
        val database = SicenetDatabase.getDatabase(context)
        LocalDataSource(database.getCalfFinal(), database.getPerfilDao(), database.getLogin(), database.getCalfUnidad(),
            database.getCargaAcademica(), database.getKardex(), database.getPromedio())
    }
    override val sicenetRepository: SicenetRepository by lazy {
        NetworkSicenetRepository(retrofitService, localDataSource)
    }
}