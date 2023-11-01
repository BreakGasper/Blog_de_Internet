package com.example.blogdeinternet

import android.app.Application
import com.example.blogdeinternet.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EntryApplication: Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()
        // Otras inicializaciones si las necesitas
    }
}