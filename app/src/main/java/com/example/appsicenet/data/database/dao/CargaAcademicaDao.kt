package com.example.appsicenet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsicenet.data.database.entities.cargaAcademicaEnties

@Dao
interface CargaAcademicaDao {
    @Query("SELECT * FROM cargaAcademica_table ORDER BY materia DESC")
    suspend fun getAllCargaAcademica(): List<cargaAcademicaEnties>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCargaAcademica(cargaAcademica:List<cargaAcademicaEnties>)

    @Query("DELETE FROM calificacionUnidad_table")
    suspend fun deleteAllFromCargaAcademica()

}