package com.example.blogdeinternet.database.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val id_blog: String = "",
    val titulo: String = "",
    val autor: String = "",
    val fechaPublicacion: String = "",
    val contenido: String = ""
)
