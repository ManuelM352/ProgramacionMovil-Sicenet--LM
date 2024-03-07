package com.example.appsicenet.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.WorkerParameters
import com.example.appsicenet.SicenetApplication
import com.example.appsicenet.data.database.entities.PromedioEntities

class PromedioWorker (
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val sicenetRepository = (applicationContext as SicenetApplication).container.sicenetRepository
        val localDataSource = (applicationContext as SicenetApplication).container.localDataSource
        try {
            val dataType = inputData.getString("dataType")
            when (dataType) {
                "promedio" -> {
                    val promedio = sicenetRepository.getKardex().promedio
                    promedio?.let {
                        val promedioEntities =
                            PromedioEntities(
                            promedioGral = it.promedioGral,
                            cdtsAcum = it.cdtsAcum,
                            cdtsPlan = it.cdtsPlan,
                            matCursadas = it.matCursadas,
                            matAprobadas = it.matAprobadas,
                            avanceCdts = it.avanceCdts,
                            fecha = it.fecha
                        )
                        localDataSource.insertPromedio(promedioEntities)
                    }
                }
            }
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