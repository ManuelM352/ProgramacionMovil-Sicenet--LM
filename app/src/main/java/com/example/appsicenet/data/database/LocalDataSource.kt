package com.example.appsicenet.data.database

import com.example.appsicenet.data.database.dao.CalfFinalDao
import com.example.appsicenet.data.database.dao.CalfUnidadDao
import com.example.appsicenet.data.database.dao.CargaAcademicaDao
import com.example.appsicenet.data.database.dao.PerfilDao
import com.example.appsicenet.data.database.dao.loginDao
import com.example.appsicenet.data.database.entities.CalfUnidadEnties
import com.example.appsicenet.data.database.entities.CargaAcademicaEntities
import com.example.appsicenet.data.database.entities.Credentials
import com.example.appsicenet.data.database.entities.PerfilEntities
import com.example.appsicenet.data.database.entities.cargaAcademicaEnties
import com.example.appsicenet.models.Attributes
import com.example.appsicenet.models.CalificacionUnidades
import com.example.appsicenet.models.CalificacionesFinales
import com.example.appsicenet.models.CargaAcademica

class LocalDataSource(
    private val calfFinalDao: CalfFinalDao,
    private val perfilDao: PerfilDao,
    private val credentialDao: loginDao,
    private val calfUnidadDao: CalfUnidadDao,
    private val cargaAcademicaDao: CargaAcademicaDao
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

    //CREDENCIALES DEL INICIO DE SECIÓN
    suspend fun saveCredentials(matricula: String, contrasenia: String) {
        credentialDao.deleteAllFromcredentials()
        val credentials = Credentials(matricula = matricula, contrasenia =  contrasenia)
        credentialDao.insert(credentials)
    }

    suspend fun insertCalificacionesUnidad(calificaciones: List<CalificacionUnidades>) {
        calfUnidadDao.deleteAllFromCalfUnidad()
        val entities = calificaciones.map { calificacionU ->
            CalfUnidadEnties(
                observaciones = calificacionU.observaciones?: "",
                c5 = calificacionU.c5?: "",
                c4 = calificacionU.c4?: "",
                c3 = calificacionU.c3?: "",
                c2 = calificacionU.c2?: "",
                c1 = calificacionU.c1?: "",
                unidadesActivas = calificacionU.unidadesActivas?: "",
                materia = calificacionU.materia?: "",
                grupo = calificacionU.grupo?: ""

            )
        }
        calfUnidadDao.insertAllCalfUnidad(entities)
    }

    suspend fun getAllCalificacionesUnidad(): List<CalificacionUnidades> {
        val entities = calfUnidadDao.getAllCalfUnidad()
        return entities.map { calificacionU ->
            CalificacionUnidades(
                observaciones = calificacionU.observaciones,
                c5 = calificacionU.c5,
                c4 = calificacionU.c4,
                c3 = calificacionU.c3,
                c2 = calificacionU.c2,
                c1 = calificacionU.c1,
                unidadesActivas = calificacionU.unidadesActivas,
                materia = calificacionU.materia,
                grupo = calificacionU.grupo
            )
        }
    }


    //INSERCIÓN Y OBTENCIÓN DE LA CARGA ACADEMICA
    suspend fun insertCargaAcademica(cargaAcademica: List<CargaAcademica>) {
        cargaAcademicaDao.deleteAllFromCargaAcademica()
        val entities = cargaAcademica.map { enties ->
            cargaAcademicaEnties(
                semipresencial = enties.semipresencial?: "",
                observaciones = enties.observaciones?: "",
                docente = enties.docente?: "",
                clvOficial = enties.clvOficial?: "",
                sabado = enties.sabado?: "",
                viernes = enties.viernes?: "",
                jueves = enties.jueves?: "",
                miercoles = enties.miercoles?: "",
                martes = enties.martes?: "",
                lunes = enties.lunes?: "",
                estadoMateria = enties.estadoMateria?: "",
                creditosMateria = enties.creditosMateria?: 0,
                materia = enties.materia?: "",
                grupo = enties.grupo?: ""

            )
        }
        cargaAcademicaDao.insertAllCargaAcademica(entities)
    }

    suspend fun getAllCargaAcademica(): List<CargaAcademica> {
        val entities = cargaAcademicaDao.getAllCargaAcademica()
        return entities.map { enties ->
            CargaAcademica(
                semipresencial = enties.semipresencial,
                observaciones = enties.observaciones,
                docente = enties.docente,
                clvOficial = enties.clvOficial,
                sabado = enties.sabado,
                viernes = enties.viernes,
                jueves = enties.jueves,
                miercoles = enties.miercoles,
                martes = enties.martes,
                lunes = enties.lunes,
                estadoMateria = enties.estadoMateria,
                creditosMateria = enties.creditosMateria,
                materia = enties.materia,
                grupo = enties.grupo
            )
        }
    }


}
