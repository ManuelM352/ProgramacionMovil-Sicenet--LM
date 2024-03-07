package com.example.appsicenet.data


import com.example.appsicenet.data.database.LocalDataSource
import com.example.appsicenet.models.Login
import com.example.appsicenet.models.CalificacionUnidades
import com.example.appsicenet.models.CalificacionesFinales
import com.example.appsicenet.models.CargaAcademica
import com.example.appsicenet.models.Kardex
import com.example.appsicenet.models.LoginResult
import com.example.appsicenet.network.SICENETApiService
import com.example.appsicenet.network.califUnidadesRequestBody
import com.example.appsicenet.network.califfinalRequestBody
import com.example.appsicenet.network.cargaAcademicaRequestBody
import com.example.appsicenet.network.kardexRequestBody
import com.example.appsicenet.network.loginRequestBody
import com.example.appsicenet.network.profileRequestBody
import kotlinx.serialization.json.Json
import java.io.IOException


interface SicenetRepository {
    suspend fun getLoginResult(matricula: String, contrasenia: String): LoginResult
    suspend fun getAcademicProfile(): Login
    suspend fun getCalificacionesFinales(): List<CalificacionesFinales>
    suspend fun getCalificacionesUnidades(): List<CalificacionUnidades>
    suspend fun getKardex(): Kardex
    suspend fun getCargaAcademica(): List<CargaAcademica>
}

class NetworkSicenetRepository(
    private val sicenetApiService: SICENETApiService,
    private val localDataSource: LocalDataSource
) : SicenetRepository {
    override suspend fun getLoginResult(matricula: String, contrasenia: String): LoginResult {
        val response = sicenetApiService.login(loginRequestBody(matricula, contrasenia)).execute()
        if (response.isSuccessful) {
            val envelope = response.body()
            val alumnoResultJson: String? =
                envelope?.bodyLogin?.accesoLoginResponse?.accesoLoginResult
            val json = Json { ignoreUnknownKeys = true }
            return alumnoResultJson?.let { json.decodeFromString(it) } ?: LoginResult()
        } else {
            throw IOException("Error en la obtencion del perfil código: ${response.code()}")
        }
    }

    override suspend fun getAcademicProfile(): Login {
        val response = sicenetApiService.getAcademicProfile(profileRequestBody()).execute()
        if (response.isSuccessful) {
            val envelope = response.body()
            val alumnoResultJson: String? =
                envelope?.body?.getAlumnoAcademicoWithLineamientoResponse?.getAlumnoAcademicoWithLineamientoResult
            val json = Json { ignoreUnknownKeys = true }
            val academicProfile = alumnoResultJson?.let { json.decodeFromString(it) } ?: Login()

            return academicProfile
        } else {
            throw IOException("Error en la obtencion del perfil código: ${response.code()}")
        }
    }


    override suspend fun getCalificacionesFinales(): List<CalificacionesFinales> {
        val response = sicenetApiService.getCalifFinal(califfinalRequestBody()).execute()
        if (response.isSuccessful) {
            val envelope = response.body()
            val alumnoResultJson: String? =
                envelope?.bodyCalf?.getAllCalifFinalByAlumnosResponse?.getAllCalifFinalByAlumnosResult
            val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
            val carga: List<CalificacionesFinales> = json.decodeFromString(alumnoResultJson ?: "")
            return carga
        } else {
            throw IOException("Error en la obtencion del perfil código: ${response.code()}")
        }
    }


    override suspend fun getCalificacionesUnidades(): List<CalificacionUnidades> {
        val response = sicenetApiService.getCalifUnidades(califUnidadesRequestBody()).execute()
        if (response.isSuccessful) {
            val envelope = response.body()
            val alumnoResultJson: String? =
                envelope?.bodyCalfUni?.getCalifUnidadesByAlumnoResponse?.getCalifUnidadesByAlumnoResult
            val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
            val carga: List<CalificacionUnidades> = json.decodeFromString(alumnoResultJson ?: "")
            return carga
        } else {
            throw IOException("Error en la obtencion del perfil código: ${response.code()}")
        }
    }

    override suspend fun getKardex(): Kardex {
        val response = sicenetApiService.getKardex(kardexRequestBody()).execute()
        if (response.isSuccessful) {
            val envelope = response.body()
            val alumnoResultJson: String? =
                envelope?.bodyKardex?.getAllKardexConPromedioByAlumnoResponse?.getAllKardexConPromedioByAlumnoResult
            val json = Json { ignoreUnknownKeys = true }
            return alumnoResultJson?.let { json.decodeFromString(it) } ?: Kardex()
        } else {

            throw IOException("Error en la obtencion del perfil código: ${response.code()}")
        }
    }

    override suspend fun getCargaAcademica(): List<CargaAcademica> {
        val response = sicenetApiService.getCargaAcademica(cargaAcademicaRequestBody()).execute()
        if (response.isSuccessful) {
            val envelope = response.body()
            val alumnoResultJson: String? =
                envelope?.bodyCargaAcademica?.getCargaAcademicaByAlumnoResponse?.getCargaAcademicaByAlumnoResult
            val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
            val carga: List<CargaAcademica> = json.decodeFromString(alumnoResultJson ?: "")
            return carga
        } else {
            throw IOException("Error en la obtencion del perfil código: ${response.code()}")
        }
    }


}
