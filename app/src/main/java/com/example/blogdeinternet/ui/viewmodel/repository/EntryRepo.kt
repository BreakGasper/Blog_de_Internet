package com.breakapp.apv2.ui.configs.viewmodel.repository

import com.breakapp.apv2.retrofit.pojos.Entry
import com.breakapp.apv2.retrofit.vo.Resource
import okhttp3.ResponseBody
import retrofit2.Response

interface EntryRepo {
    suspend fun getEntry(): Resource<List<Entry>>
    suspend fun searchEntry(search:String): Resource<List<Entry>>
    suspend fun addEntry(params: HashMap<String?, String?>): Response<ResponseBody>

}