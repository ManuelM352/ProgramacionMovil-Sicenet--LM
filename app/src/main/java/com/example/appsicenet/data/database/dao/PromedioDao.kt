package com.example.appsicenet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsicenet.data.database.entities.PromedioEntities

@Dao
interface PromedioDao {
    @Query("SELECT * FROM promedio_table")
    suspend fun getAllPromedio(): PromedioEntities?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllpromedio(promedio: PromedioEntities)

    @Query("DELETE FROM promedio_table")
    suspend fun deleteAllFromPromedio()

}