package com.example.appsicenet.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CargaAcademicaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TableCargaAcademica)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TableCargaAcademica>)
    @Update
    suspend fun update(item: TableCargaAcademica)

    @Delete
    suspend fun delete(item: TableCargaAcademica)

    @Query("SELECT * from cargaAcademica WHERE clvOficial = :id")
    fun getItem(id: String): Flow<TableCargaAcademica>

    @Query("SELECT * from cargaAcademica ORDER BY materia ASC")
    fun getAllItems(): Flow<List<TableCargaAcademica>>
}

