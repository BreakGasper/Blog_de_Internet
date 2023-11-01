package com.breakapp.apv2.ui.configs.viewmodel.repository


import com.breakapp.apv2.retrofit.pojos.Entry
import com.breakapp.apv2.retrofit.vo.Resource
import com.breakapp.apv2.ui.configs.viewmodel.model.DataSource
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject


class EntryRepoImpl @Inject constructor(private val dataSource: DataSource): EntryRepo {

    override suspend fun getEntry(): Resource<List<Entry>> {
        return  dataSource.getEntry()
    }

    override suspend fun searchEntry(search:String): Resource<List<Entry>> {
        return  dataSource.searchEntry(search)
    }

    override suspend fun addEntry(params: HashMap<String?, String?>): Response<ResponseBody> {
        return  dataSource.addEntry(params)
    }



}