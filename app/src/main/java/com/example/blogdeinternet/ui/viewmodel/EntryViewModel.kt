package com.breakapp.apv2.ui.configs.viewmodel

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.breakapp.apv2.retrofit.pojos.Entry
import com.breakapp.apv2.retrofit.vo.Resource
import com.breakapp.apv2.ui.configs.viewmodel.repository.EntryRepo
import com.example.blogdeinternet.database.entitys.EntryEntity
import com.example.blogdeinternet.ui.viewmodel.repository.EntryRepository
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(private val entryRepo: EntryRepo, private val entryRepository: EntryRepository) : ViewModel() {

    private val entryData = MutableLiveData<String>()

    fun setEntryData(entrData: String) {
        entryData.value = entrData
    }

    init {
        setEntryData("entrys")
    }

    val fetchEntry = entryData.distinctUntilChanged().switchMap { fk ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(entryRepo.getEntry())
            } catch (e: Exception) {
                emit(Resource.Failure(e))
                println("Error fetchEntry: " + e)
            }
        }
    }
    private val _searchEntry = MutableLiveData<String>() // MutableLiveData para permitir observadores
    val searchEntry: LiveData<Resource<List<Entry>>> = _searchEntry.distinctUntilChanged().switchMap { searchTerm ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(entryRepo.searchEntry(searchTerm)) // Utilizar searchTerm, no _searchEntry
            } catch (e: Exception) {
                emit(Resource.Failure(e))
                println("Error searchEntry: $e")
            }
        }
    }

    fun setSearchEntry(search: String) {
        _searchEntry.value = search // Actualizar el valor de _searchEntry con el nuevo término de búsqueda
    }

    fun addEntry(params: HashMap<String?, String?>) {
        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = entryRepo.addEntry(params)//userRepo.addUser(params)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    try {
                        val prettyJson =
                            gson.toJson(
                                JsonParser.parseString(
                                    response.body()
                                        ?.string()
                                )
                            )

                        Log.d("Printed JSON :", prettyJson)

                    } catch (e: Exception) {
                        Log.d("ErorN :", e.toString())
                    }
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

    fun validationBox(tilArray: ArrayList<EditText>): Boolean {
        var bol: Boolean = true

        //validar si esta vacio
        try {
            for (i in 0..tilArray.size - 1) {
                var tie = tilArray.get(i)
                if (tie.text!!.isEmpty()) {
                    tie.setError("Debes llenar el campo")
                    bol = false
                }
            }
        } catch (e: Exception) {
            println("Error Array datos" + e)
            bol = false

        }
        return bol

    }

    fun cleanBox(tilArray: ArrayList<EditText>) {
        try {
            for (i in 0..tilArray.size - 1) {
                var tie = tilArray.get(i)
                tie.setText("")
            }
        } catch (e: Exception) {
            println("Error Array datos" + e)
        }
    }

    fun insertEntry(entry: EntryEntity) {
        viewModelScope.launch {
            entryRepository.insertEntry(entry)
        }
    }

    // LiveData para almacenar la lista de entradas
    private val _entryList = MutableLiveData<List<EntryEntity>>()
    val entryList: LiveData<List<EntryEntity>> get() = _entryList

    // Función para obtener y actualizar la lista de entradas
    fun loadEntries() {
        viewModelScope.launch {
            try {

                val entries = entryRepository.getAllEntries()

                _entryList.postValue(entries)
            } catch (e: Exception) {
                Log.e("Error ROM",e.toString())

            }
        }
    }
}