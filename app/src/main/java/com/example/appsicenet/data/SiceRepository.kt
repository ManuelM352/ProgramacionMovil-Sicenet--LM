package com.example.appsicenet.data

import com.example.appsicenet.models.Envelope
import com.example.appsicenet.network.SICENETApiService
import retrofit2.Call


interface SicenetRepository {
    suspend fun getdatos(): Call<Envelope>
}

class NetworkSicenetRepository(
    private val sicenetApiService: SICENETApiService
) : SicenetRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}