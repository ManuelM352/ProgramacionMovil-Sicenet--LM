package com.example.appsicenet.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.appsicenet.SicenetApplication
import com.example.appsicenet.data.database.entities.PerfilEntities
import kotlin.jvm.Throws

class FetchDataWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val sicenetRepository = (applicationContext as SicenetApplication).container.sicenetRepository
        val localDataSource = (applicationContext as SicenetApplication).container.localDataSource
        // Lógica para autenticarse en Sicenet y obtener el perfil
        try {
            // Autenticación
            val matricula = inputData.getString("matricula")
            val contrasenia = inputData.getString("contrasenia")
            val loginResult = sicenetRepository.getLoginResult(matricula ?: "", contrasenia ?: "")


            // Obtener perfil
            val academicProfile = sicenetRepository.getAcademicProfile()

            // Almacenar en la base de datos local
            localDataSource.saveCredentials(
                matricula =  loginResult.matricula?:"",
                contrasenia =  loginResult.contrasenia?:"")

            localDataSource.insertPerfil(PerfilEntities(
                nombre = academicProfile.nombre?: "",
                carrera = academicProfile.carrera?: "",
                especialidad = academicProfile.especialidad ?: "",
                matricula = academicProfile.matricula?: ""

            ))

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}
