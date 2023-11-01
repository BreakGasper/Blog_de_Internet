package com.example.blogdeinternet.database.dao

import android.content.Context
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.blogdeinternet.database.AppDatabase
import com.example.blogdeinternet.database.entitys.EntryEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Dao
interface EntryDao {

    @Insert
    suspend fun insertEntry(entry: EntryEntity)

    @Query("SELECT * FROM entries")
    suspend fun getAllEntries(): List<EntryEntity>
}


