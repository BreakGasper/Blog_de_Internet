package com.breakapp.apv2.ui.configs.viewmodel.model

import com.breakapp.apv2.retrofit.pojos.Entry
import com.breakapp.apv2.retrofit.vo.Resource
import com.breakapp.apv2.retrofit.vo.RetrofitClien
import okhttp3.ResponseBody

import retrofit2.Response
import javax.inject.Inject

class DataSource@Inject constructor(){
    suspend fun getEntry():Resource<List<Entry>>{
        return Resource.Success(RetrofitClien.APServices.getEntry().entryList)
    }

    suspend fun addEntry(params: HashMap<String?, String?>):Response<ResponseBody> {
        return  RetrofitClien.APServices.insertEntry(params)
    }

    suspend fun searchEntry(search:String):Resource<List<Entry>>{
        return Resource.Success(RetrofitClien.APServices.searchEntry(search).entryList)
    }


}