package com.example.appsicenet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "calificacionFinal_table")
data class CargaAcademicaEntities (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "calif") val calif: Int,
    @ColumnInfo(name = "acred") val acred: String,
    @ColumnInfo(name = "grupo") val grupo: String,
    @ColumnInfo(name = "materia") val materia: String,
    @ColumnInfo(name = "Observaciones") val Observaciones: String
)


@Entity(tableName = "perfil_table")
data class PerfilEntities (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "especialidad") val especialidad: String,
    @ColumnInfo(name = "carrera") val carrera: String,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "matricula") val matricula: String
)

@Entity(tableName = "credentials_table")
data class Credentials(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")val id: Int = 0,
    @ColumnInfo(name = "matricula") val matricula: String,
    @ColumnInfo(name = "contrasenia") val contrasenia: String
)

@Entity(tableName = "calificacionUnidad_table")
data class CalfUnidadEnties(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")val id: Int = 0,
    @ColumnInfo(name = "observaciones") val observaciones: String,
    @ColumnInfo(name = "c5") val c5: String,
    @ColumnInfo(name = "c4") val c4: String,
    @ColumnInfo(name = "c3") val c3: String,
    @ColumnInfo(name = "c2") val c2: String,
    @ColumnInfo(name = "c1") val c1: String,
    @ColumnInfo(name = "unidadesActivas") val unidadesActivas: String,
    @ColumnInfo(name = "materia") val materia: String,
    @ColumnInfo(name = "grupo") val grupo: String
)

@Entity(tableName = "cargaAcademica_table")
data class cargaAcademicaEnties(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")val id: Int = 0,
    @ColumnInfo(name = "semipresencial") val semipresencial: String,
    @ColumnInfo(name = "observaciones") val observaciones: String,
    @ColumnInfo(name = "docente") val docente: String,
    @ColumnInfo(name = "clvOficial") val clvOficial: String,
    @ColumnInfo(name = "sabado") val sabado: String,
    @ColumnInfo(name = "viernes") val viernes: String,
    @ColumnInfo(name = "jueves") val jueves: String,
    @ColumnInfo(name = "miercoles") val miercoles: String,
    @ColumnInfo(name = "martes") val martes: String,
    @ColumnInfo(name = "lunes") val lunes: String,
    @ColumnInfo(name = "estadoMateria") val estadoMateria: String,
    @ColumnInfo(name = "creditosMateria") val creditosMateria: Int,
    @ColumnInfo(name = "materia") val materia: String,
    @ColumnInfo(name = "grupo") val grupo: String

)

