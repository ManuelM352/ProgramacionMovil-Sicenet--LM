package com.example.appsicenet.data.database

import com.example.appsicenet.data.database.dao.CalfFinalDao
import com.example.appsicenet.data.database.dao.PerfilDao
import com.example.appsicenet.data.database.dao.loginDao
import com.example.appsicenet.data.database.entities.CargaAcademicaEntities
import com.example.appsicenet.data.database.entities.Credentials
import com.example.appsicenet.data.database.entities.PerfilEntities
import com.example.appsicenet.models.Attributes
import com.example.appsicenet.models.CalificacionesFinales

class LocalDataSource(
    private val calfFinalDao: CalfFinalDao,
    private val perfilDao: PerfilDao,
    private val credentialDao: loginDao
) {

    //INSERCIÓN Y OBTENCIÓN DE LAS CALIFICACIONES FINALES
    suspend fun insertCalificaciones(calificaciones: List<CalificacionesFinales>) {
        calfFinalDao.deleteAllFromClafFinal()
        val entities = calificaciones.map { calificacion ->
            CargaAcademicaEntities(
                calif = calificacion.calif ?: 0,
                acred = calificacion.acred ?: "",
                grupo = calificacion.grupo ?: "",
                materia = calificacion.materia ?: "",
                Observaciones = calificacion.Observaciones ?: ""
            )
        }
        calfFinalDao.insertAll(entities)
    }

    suspend fun getAllCalificaciones(): List<CalificacionesFinales> {
        val entities = calfFinalDao.getAllCalFinal()
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

    //INSERCIÓN Y OBTENCION DEL PERFIL
    suspend fun insertPerfil(perfil: PerfilEntities) {
        perfilDao.deleteAllFromPerfil()
        perfilDao.insertAll(perfil)
    }

    suspend fun getAllPerfil(): PerfilEntities? {
        return perfilDao.getAllPerfil()
    }

    //CREDENCIALES DEL INICIO DE SECIÓN
    suspend fun saveCredentials(matricula: String, contrasenia: String) {
        credentialDao.deleteAllFromcredentials()
        val credentials = Credentials(matricula = matricula, contrasenia =  contrasenia)
        credentialDao.insert(credentials)
    }

    suspend fun getCredentials(): Credentials? {
        return credentialDao.getCredentials()
    }

}
