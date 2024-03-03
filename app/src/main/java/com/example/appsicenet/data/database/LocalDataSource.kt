package com.example.appsicenet.data.database

import com.example.appsicenet.data.database.dao.SicenetDao
import com.example.appsicenet.data.database.entities.SicenetEntities
import com.example.appsicenet.models.CalificacionesFinales

class LocalDataSource(private val sicenetDao: SicenetDao) {
    suspend fun insertCalificaciones(calificaciones: List<CalificacionesFinales>) {
        sicenetDao.deleteAllFromClafFinal()
        val entities = calificaciones.map { calificacion ->
            SicenetEntities(
                calif = calificacion.calif ?: 0,
                acred = calificacion.acred ?: "",
                grupo = calificacion.grupo ?: "",
                materia = calificacion.materia ?: "",
                Observaciones = calificacion.Observaciones ?: ""
            )
        }
        sicenetDao.insertAll(entities)
    }

    suspend fun getAllCalificaciones(): List<CalificacionesFinales> {
        val entities = sicenetDao.getAllCalFinal()
        return entities.map { entity ->
            CalificacionesFinales(
                calif = entity.calif,
                acred = entity.acred,
                grupo = entity.grupo,
                materia = entity.materia,
                Observaciones = entity.Observaciones
            )
        }
    }
}
