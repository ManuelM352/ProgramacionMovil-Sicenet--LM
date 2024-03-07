package com.example.appsicenet.data.database

import com.example.appsicenet.data.database.dao.CalfFinalDao
import com.example.appsicenet.data.database.dao.CalfUnidadDao
import com.example.appsicenet.data.database.dao.CargaAcademicaDao
import com.example.appsicenet.data.database.dao.KardexItemDao
import com.example.appsicenet.data.database.dao.PerfilDao
import com.example.appsicenet.data.database.dao.PromedioDao
import com.example.appsicenet.data.database.dao.loginDao
import com.example.appsicenet.data.database.entities.CalfUnidadEnties
import com.example.appsicenet.data.database.entities.CalificacionesFinalesEntities
import com.example.appsicenet.data.database.entities.Credentials
import com.example.appsicenet.data.database.entities.KardexEntities
import com.example.appsicenet.data.database.entities.PerfilEntities
import com.example.appsicenet.data.database.entities.PromedioEntities
import com.example.appsicenet.data.database.entities.cargaAcademicaEnties
import com.example.appsicenet.models.CalificacionUnidades
import com.example.appsicenet.models.CalificacionesFinales
import com.example.appsicenet.models.CargaAcademica
import com.example.appsicenet.models.KardexItem
import java.util.Date

class LocalDataSource(
    private val calfFinalDao: CalfFinalDao,
    private val perfilDao: PerfilDao,
    private val credentialDao: loginDao,
    private val calfUnidadDao: CalfUnidadDao,
    private val cargaAcademicaDao: CargaAcademicaDao,
    private val kardexItemDao: KardexItemDao,
    private val promedioDao: PromedioDao
) {
    val fechaActual = Date().toString()
    //INSERCIÓN Y OBTENCIÓN DE LAS CALIFICACIONES FINALES
    suspend fun insertCalificaciones(calificaciones: List<CalificacionesFinales>) {
        calfFinalDao.deleteAllFromClafFinal()
        val entities = calificaciones.map { calificacion ->
            CalificacionesFinalesEntities(
                calif = calificacion.calif ?: 0,
                acred = calificacion.acred ?: "",
                grupo = calificacion.grupo ?: "",
                materia = calificacion.materia ?: "",
                Observaciones = calificacion.Observaciones ?: "",
                fecha = fechaActual
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
                Observaciones = entity.Observaciones,
                fecha = entity.fecha
            )
        }
    }
    //INSERCIÓN Y OBTENCION DEL PERFIL
    suspend fun insertPerfil(perfil: PerfilEntities) {
        perfilDao.deleteAllFromPerfil()
        perfilDao.insertAll(perfil)
    }

    suspend fun getAllPerfil(): PerfilEntities? {
        val entities = perfilDao.getAllPerfil()
        return entities?.let {
            PerfilEntities(
                especialidad = it.especialidad,
                carrera = it.carrera,
                nombre = it.nombre,
                matricula = it.matricula,
                fecha = it.fecha
            )
        }
    }

    suspend fun getPerfil() {
        perfilDao.getAllPerfil()
    }
    //CREDENCIALES DEL INICIO DE SECIÓN
    suspend fun saveCredentials(matricula: String, contrasenia: String) {
        credentialDao.deleteAllFromcredentials()
        val credentials = Credentials(matricula = matricula, contrasenia =  contrasenia)
        credentialDao.insertCredentials(credentials)
    }
    suspend fun getCredentials(): Credentials? {
        return credentialDao.getCredentials()
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
                grupo = calificacionU.grupo?: "",
                fecha = fechaActual

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
                grupo = calificacionU.grupo,
                fecha = calificacionU.fecha
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
                grupo = enties.grupo?: "",
                fecha = fechaActual

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
                grupo = enties.grupo,
                fecha = enties.fecha
            )
        }
    }

    //INSERCIÓN Y OBTENCIÓN DEL KARDEX
    suspend fun insertKardex(kardex: List<KardexItem>) {
        kardexItemDao.deleteAllFromKardex()
        val entities = kardex.map { enties ->
            KardexEntities(
                s3 = enties.s3,
                p3 = enties.p3,
                a3 = enties.a3,
                clvMat = enties.clvMat,
                clvOfiMat = enties.clvOfiMat,
                materia = enties.materia,
                cdts = enties.cdts,
                calif = enties.calif,
                acred = enties.acred,
                s1 = enties.s1,
                p1 = enties.p1,
                a1 = enties.a1,
                s2 = enties.s2,
                p2 = enties.p2,
                a2 = enties.a2,
                fecha = fechaActual
            )
        }
        kardexItemDao.insertAllKardex(entities)
    }

    suspend fun getAllkardex(): List<KardexItem> {
        val entities = kardexItemDao.getAllKardex()
        return entities.map { enties ->
            KardexItem(
                s3 = enties.s3,
                p3 = enties.p3,
                a3 = enties.a3,
                clvMat = enties.clvMat,
                clvOfiMat = enties.clvOfiMat,
                materia = enties.materia,
                cdts = enties.cdts,
                calif = enties.calif,
                acred = enties.acred,
                s1 = enties.s1,
                p1 = enties.p1,
                a1 = enties.a1,
                s2 = enties.s2,
                p2 = enties.p2,
                a2 = enties.a2,
                fecha = fechaActual
            )
        }
    }

    //INSERCIÓN Y OBTENCION DEL PROMEDIO
    suspend fun insertPromedio(promedio: PromedioEntities) {
        promedioDao.deleteAllFromPromedio()
        promedioDao.insertAllpromedio(promedio)
    }

    suspend fun getAllPromedio(): PromedioEntities? {
        val entities = promedioDao.getAllPromedio()
        return entities?.let {
            PromedioEntities(
                matricula = it.matricula,
                promedioGral = it.promedioGral,
                cdtsAcum = it.cdtsAcum,
                cdtsPlan = it.cdtsPlan,
                matCursadas = it.matCursadas,
                matAprobadas = it.matAprobadas,
                avanceCdts = it.avanceCdts,
                fecha = it.fecha

            )
        }
    }
}
