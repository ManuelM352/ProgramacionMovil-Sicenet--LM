package com.example.appsicenet.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appsicenet.SicenetApplication

class SyncDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        try {
            // Realiza la sincronización de datos aquí
            val repository = (applicationContext as SicenetApplication).container.SicenetRepository
            repository.getAcademicProfile()
            repository.getCalificacionesFinales()

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}
