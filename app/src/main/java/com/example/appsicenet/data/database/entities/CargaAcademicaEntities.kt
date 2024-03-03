package com.example.appsicenet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* val calif: Int? = null,
    val acred: String? = null,
    val grupo: String? = null,
    val materia: String? = null,
    val Observaciones: String? = null
    */
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


