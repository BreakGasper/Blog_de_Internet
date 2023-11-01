package com.breakapp.apv2.retrofit.pojos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import okhttp3.ResponseBody
import retrofit2.Response

@Parcelize
data class Entry(
    @SerializedName("id_blog")
    val id_blog: String = "",
    @SerializedName("titulo")
    val titulo: String = "",
    @SerializedName("autor")
    val autor: String = "",
    @SerializedName("fecha_publicacion")
    val fechaPublicacion: String = "",
    @SerializedName("contenido")
    val contenido: String = ""
) : Parcelable

data class EntryList(
    @SerializedName("entrys")
    val entryList: List<Entry>
)
