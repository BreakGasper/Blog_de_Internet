package com.breakapp.apv2.retrofit

import com.breakapp.apv2.retrofit.pojos.EntryList
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface WebServices {


    //obtiene la lista de entradas
    @GET("index.php?action=obtenerEntradas")
    suspend fun getEntry(): EntryList

    //inserta la entrada
    @FormUrlEncoded
    @POST("index.php")
    suspend fun insertEntry(@FieldMap params: HashMap<String?, String?>):
            Response<ResponseBody>

    //obtiene por busqueda
    @GET("index.php?action=buscarEntradas")
    suspend fun searchEntry(@Query("busqueda") searchTerm: String): EntryList
}