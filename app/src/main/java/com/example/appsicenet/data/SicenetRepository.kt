package com.example.appsicenet.data


import com.example.appsicenet.models.Envelope
import com.example.appsicenet.models.EnvelopeCalf
import com.example.appsicenet.models.EnvelopeCalfUni
import com.example.appsicenet.models.EnvelopeCargaAcademica
import com.example.appsicenet.models.EnvelopeKardex
import com.example.appsicenet.network.SICENETApiService
import com.example.appsicenet.network.califUnidadesRequestBody
import com.example.appsicenet.network.califfinalRequestBody
import com.example.appsicenet.network.cargaAcademicaRequestBody
import com.example.appsicenet.network.kardexRequestBody
import com.example.appsicenet.network.profileRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call


interface SicenetRepository {
    suspend fun getAcademicProfile(): Call<Envelope>
    suspend fun getCalificacionesFinales(): Call<EnvelopeCalf>
    suspend fun getCalificacionesUnidades(): Call<EnvelopeCalfUni>
    suspend fun getKardex(): Call<EnvelopeKardex>
    suspend fun getCargaAcademica(): Call<EnvelopeCargaAcademica> // Nuevo método
}

class NetworkSicenetRepository(
    private val sicenetApiService: SICENETApiService
) : SicenetRepository {
    override suspend fun getAcademicProfile(): Call<Envelope> = sicenetApiService.getAcademicProfile(profileRequestBody())
    override suspend fun getCalificacionesFinales(): Call<EnvelopeCalf> = sicenetApiService.getCalifFinal(califfinalRequestBody())
    override suspend fun getCalificacionesUnidades(): Call<EnvelopeCalfUni> = sicenetApiService.getCalifUnidades(califUnidadesRequestBody())
    override suspend fun getKardex(): Call<EnvelopeKardex> = sicenetApiService.getKardex(kardexRequestBody())
    override suspend fun getCargaAcademica(): Call<EnvelopeCargaAcademica> = sicenetApiService.getCargaAcademica(cargaAcademicaRequestBody())
}