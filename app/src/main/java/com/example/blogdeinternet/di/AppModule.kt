package com.breakapp.room.di

import android.app.Application
import android.content.Context
import com.example.blogdeinternet.database.AppDatabase
import com.example.blogdeinternet.database.dao.EntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object DaoModule {

        @Provides
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }

        @Provides
        fun provideEntryDao(context: Context): EntryDao {
            return AppDatabase.getDatabase(context).entryDao()
        }
    }


}