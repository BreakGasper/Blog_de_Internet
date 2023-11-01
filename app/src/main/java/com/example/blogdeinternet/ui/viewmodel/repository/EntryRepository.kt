package com.example.blogdeinternet.ui.viewmodel.repository

import com.example.blogdeinternet.database.dao.EntryDao
import com.example.blogdeinternet.database.entitys.EntryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EntryRepository@Inject constructor(private val entryDao: EntryDao) {

    suspend fun insertEntry(entry: EntryEntity) {
        withContext(Dispatchers.IO) {
            entryDao.insertEntry(entry)
        }
    }

    suspend fun getAllEntries(): List<EntryEntity> {
        return withContext(Dispatchers.IO) {
            entryDao.getAllEntries()
        }
    }
}
