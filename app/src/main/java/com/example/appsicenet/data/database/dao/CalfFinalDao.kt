package com.example.appsicenet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsicenet.data.database.entities.CalificacionesFinalesEntities

//
@Dao
interface CalfFinalDao {
    @Query("SELECT * FROM calificacionFinal_table ORDER BY materia DESC")
    suspend fun getAllCalFinal(): List<CalificacionesFinalesEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(calfFinal:List<CalificacionesFinalesEntities>)

    @Query("DELETE FROM calificacionFinal_table")
    suspend fun deleteAllFromClafFinal()
}
