package com.example.appsicenet.data

import com.example.appsicenet.models.Envelope
import com.example.appsicenet.network.SICENETApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call


interface SicenetRepository {
    suspend fun getdatos(): Call<Envelope>
}

class NetworkSicenetRepository(
    private val sicenetApiService: SICENETApiService
) : SicenetRepository {
    override suspend fun getdatos(): Call<Envelope> = sicenetApiService.getAcademicProfile(profileRequestBody())
}

fun profileRequestBody(): RequestBody {
    return """
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
          </soap:Body>
        </soap:Envelope>
    """.trimIndent().toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull())
}
