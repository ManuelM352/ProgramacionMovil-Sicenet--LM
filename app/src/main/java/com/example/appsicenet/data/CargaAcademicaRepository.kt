package com.example.appsicenet.data

import kotlinx.coroutines.flow.Flow

interface CargaAcademicaRepository {
    /**
     * Retrieve all the items from the given data source.
     */
    fun getAllItemsStream(): Flow<List<TableCargaAcademica>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: String): Flow<TableCargaAcademica?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: TableCargaAcademica)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: TableCargaAcademica)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: TableCargaAcademica)

}