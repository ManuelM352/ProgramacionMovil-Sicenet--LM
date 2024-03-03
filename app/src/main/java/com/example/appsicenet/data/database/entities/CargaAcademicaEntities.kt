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
