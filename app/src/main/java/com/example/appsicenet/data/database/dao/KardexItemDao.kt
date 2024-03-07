package com.example.appsicenet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsicenet.data.database.entities.KardexEntities


@Dao
interface KardexItemDao {
    @Query("SELECT * FROM kardex_table ORDER BY materia DESC")
    suspend fun getAllKardex(): List<KardexEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKardex(kardex:List<KardexEntities>)

    @Query("DELETE FROM kardex_table")
    suspend fun deleteAllFromKardex()
}