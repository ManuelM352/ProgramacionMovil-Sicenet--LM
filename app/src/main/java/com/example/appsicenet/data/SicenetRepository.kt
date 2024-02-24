package com.example.appsicenet.data

import com.example.appsicenet.models.Envelope
import com.example.appsicenet.network.SICENETApiService
import com.example.appsicenet.network.profileRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call


interface SicenetRepository {
    suspend fun getAcademicProfile(): Call<Envelope>
}

class NetworkSicenetRepository(
    private val sicenetApiService: SICENETApiService
) : SicenetRepository {
    override suspend fun getAcademicProfile(): Call<Envelope> = sicenetApiService.getAcademicProfile(profileRequestBody())
}

