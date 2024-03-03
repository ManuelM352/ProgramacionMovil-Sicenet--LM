package com.example.appsicenet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsicenet.data.database.entities.Credentials

@Dao
interface loginDao {
    @Query("SELECT * FROM credentials_table LIMIT 1")
    suspend fun getCredentials(): Credentials?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(credentials: Credentials)

    @Query("DELETE FROM credentials_table")
    suspend fun deleteAllFromcredentials()
}