package com.example.appsicenet.data

import com.example.appsicenet.models.Envelope
import retrofit2.Call

interface SicenetRepositoryOffline {
    suspend fun getAcademicProfileOffline(): Call<Envelope>
}


