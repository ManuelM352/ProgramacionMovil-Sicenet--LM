package com.example.appsicenet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsicenet.data.database.entities.SicenetEntities

//23:34
@Dao
interface SicenetDao {
    @Query("SELECT * FROM calificacionFinal_table ORDER BY materia DESC")
    suspend fun getAllCalFinal(): List<SicenetEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(calfFinal:List<SicenetEntities>)

    @Query("DELETE FROM calificacionFinal_table")
    suspend fun deleteAllFromClafFinal()
}
