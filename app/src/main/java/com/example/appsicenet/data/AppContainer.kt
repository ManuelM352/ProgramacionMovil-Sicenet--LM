package com.example.appsicenet.data

import android.content.Context
import com.example.appsicenet.network.SICENETApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class AppContainer(context: Context) {
    object RetrofitClient {
        private const val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx"

        private val client = OkHttpClient.Builder().build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val service: SICENETApiService = retrofit.create(SICENETApiService::class.java)
    }
}