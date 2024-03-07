package com.example.appsicenet.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.WorkerParameters
import com.example.appsicenet.SicenetApplication
import com.example.appsicenet.data.database.entities.PerfilEntities

class LoginWorker(
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


            // Obtener perfil
            val academicProfile = sicenetRepository.getAcademicProfile()

            // Almacenar en la base de datos local
            localDataSource.saveCredentials(
                matricula =  matricula ?: "",
                contrasenia =  contrasenia ?: "")

            localDataSource.insertPerfil(PerfilEntities(
                nombre = academicProfile.nombre ?: "",
                carrera = academicProfile.carrera ?: "",
                especialidad = academicProfile.especialidad ?: "",
                matricula = academicProfile.matricula ?: ""
            ))

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    companion object {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }
}
