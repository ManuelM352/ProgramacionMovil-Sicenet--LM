package com.example.appsicenet.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.appsicenet.SicenetApplication

class CalfUnidadesWorker (
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val sicenetRepository = (applicationContext as SicenetApplication).container.sicenetRepository
        val localDataSource = (applicationContext as SicenetApplication).container.localDataSource
        try {
            val dataType = inputData.getString("dataType")
            when (dataType) {
                "calfUnidades" -> {
                    val calfUnidad = sicenetRepository.getCalificacionesUnidades()
                    localDataSource.insertCalificacionesUnidad(calfUnidad)
                }
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}
