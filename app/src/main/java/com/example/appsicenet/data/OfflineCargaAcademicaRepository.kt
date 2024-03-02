package com.example.appsicenet.data

import kotlinx.coroutines.flow.Flow

class OfflineCargaAcademicaRepository(private val itemDao: CargaAcademicaDAO) : CargaAcademicaRepository {
    override fun getAllItemsStream(): Flow<List<TableCargaAcademica>> = itemDao.getAllItems()
    override fun getItemStream(id: String): Flow<TableCargaAcademica?> = itemDao.getItem(id)
    override suspend fun insertItem(item: TableCargaAcademica)= itemDao.insert(item)

    override suspend fun deleteItem(item: TableCargaAcademica)= itemDao.delete(item)

    override suspend fun updateItem(item: TableCargaAcademica) = itemDao.update(item)

}



