package com.example.appsicenet.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "cargaAcademica")
data class TableCargaAcademica(
    @PrimaryKey
    val clvOficial: String,
    val semipresencial: String,
    val observaciones: String,
    val docente: String,
    val sabado: String,
    val viernes: String,
    val jueves: String,
    val miercoles: String,
    val martes: String,
    val lunes: String,
    val estadoMateria: String,
    val creditosMateria: Int,
    val materia: String,
    val grupo: String
)