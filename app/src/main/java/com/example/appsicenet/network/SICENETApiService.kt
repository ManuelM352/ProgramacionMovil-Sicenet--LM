package com.example.appsicenet.network

import com.example.appsicenet.models.Envelope
import com.example.appsicenet.models.EnvelopeCalf
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SICENETApiService {
    //LOGIN
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/accesoLogin"
    )
    @POST("/ws/wsalumnos.asmx")
    fun login(@Body body: RequestBody): Call<ResponseBody>

    //PERFIL
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getAlumnoAcademicoWithLineamiento"
    )
    @POST("/ws/wsalumnos.asmx")
    fun getAcademicProfile(@Body body: RequestBody): Call<Envelope>

    //CALIFICACIONES FINALES
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getAllCalifFinalByAlumnos"
    )
    @POST("/ws/wsalumnos.asmx")
    fun getCalifFinal(@Body body: RequestBody): Call<EnvelopeCalf>

    //CALIFICACIONES POR UNIDAD
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getCalifUnidadesByAlumno"
    )
    @POST("/ws/wsalumnos.asmx")
    fun getCalifUnidadesByAlumno(@Body body: RequestBody): Call<Envelope>

    //KARDEX
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getAllKardexConPromedioByAlumno"
    )
    @POST("/ws/wsalumnos.asmx")
    fun getAllKardexConPromedioByAlumno(@Body body: RequestBody): Call<Envelope>

    //CARGA ACADEMICA
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getCargaAcademicaByAlumno"
    )
    @POST("/ws/wsalumnos.asmx")
    fun getCargaAcademicaByAlumno(@Body body: RequestBody): Call<Envelope>
}