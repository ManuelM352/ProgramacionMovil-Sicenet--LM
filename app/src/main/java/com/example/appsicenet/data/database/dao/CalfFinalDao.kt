package com.example.appsicenet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsicenet.data.database.entities.CargaAcademicaEntities
import com.example.appsicenet.data.database.entities.PerfilEntities

//23:34
@Dao
interface CalfFinalDao {
    @Query("SELECT * FROM calificacionFinal_table ORDER BY materia DESC")
    suspend fun getAllCalFinal(): List<CargaAcademicaEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(calfFinal:List<CargaAcademicaEntities>)

    @Query("DELETE FROM calificacionFinal_table")
    suspend fun deleteAllFromClafFinal()
}
