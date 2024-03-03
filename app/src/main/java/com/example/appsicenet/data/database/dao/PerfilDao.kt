package com.example.appsicenet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsicenet.data.database.entities.PerfilEntities
@Dao
interface PerfilDao {
    @Query("SELECT * FROM perfil_table")
    suspend fun getAllPerfil(): PerfilEntities?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(perfil: PerfilEntities)

    @Query("DELETE FROM perfil_table")
    suspend fun deleteAllFromPerfil()
}