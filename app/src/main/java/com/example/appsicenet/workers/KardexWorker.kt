package com.example.appsicenet.workers

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.WorkerParameters
import com.example.appsicenet.SicenetApplication

class KardexWorker (
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val sicenetRepository = (applicationContext as SicenetApplication).container.sicenetRepository
        val localDataSource = (applicationContext as SicenetApplication).container.localDataSource
        try {
            val dataType = inputData.getString("dataType")
            when (dataType) {
                "kardex" -> {
                    val kardex = sicenetRepository.getKardex().lstKardex ?: emptyList() // Asegúrate de manejar el caso de que lstKardex pueda ser nulo
                    localDataSource.insertKardex(kardex)
                }
            }
            return Result.success()
        } catch (e: Exception) {
            Log.d("a", "Error"+ e)
            return Result.failure()
        }
    }


    companion object {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }
}