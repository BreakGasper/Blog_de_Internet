package com.example.blogdeinternet

import DialogEntry
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.breakapp.apv2.retrofit.pojos.Entry
import com.breakapp.apv2.retrofit.vo.Resource
import com.breakapp.apv2.ui.configs.viewmodel.EntryViewModel
import com.example.blogdeinternet.database.entitys.EntryEntity
import com.example.blogdeinternet.databinding.ActivityMainBinding
import com.example.blogdeinternet.system.configuration.GlobalConfig.Companion.WS_ACTION_INSERT
import com.example.blogdeinternet.system.configuration.InternetConnectivityChecker
import com.example.blogdeinternet.ui.adapters.AdapterList
import com.example.blogdeinternet.ui.adapters.AdapterListOffline
import dagger.hilt.android.AndroidEntryPoint
import android.widget.EditText as EditText1

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterList.OnUserClickListener, AdapterListOffline.OnUserClickListener {
    private lateinit var b: ActivityMainBinding
    var list_EditText = ArrayList<EditText1>()
    private val vm by viewModels<EntryViewModel>()
    private var option: String = "form"
    private lateinit var internetChecker: InternetConnectivityChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        setSupportActionBar(b.toolbar)


        list_EditText.add(b.etAutor)
        list_EditText.add(b.etTitle)
        list_EditText.add(b.etContent)

        b.ivBtnOption.setOnClickListener {
            if (option.equals("form")) {
                ShowListView()

                setUpEntry(b.rvListEntry)
            } else {
                b.llViewForms.visibility = View.VISIBLE
                b.llViewList.visibility = View.GONE
                option = "form"
                b.ivBtnOption.setImageResource(R.drawable.baseline_list_24)
            }

        }

        b.btnGuardar.setOnClickListener {

            if (validarVacios()) {
                insertEntry(
                    b.etTitle.text.toString(),
                    b.etAutor.text.toString(), b.etContent.text.toString(), WS_ACTION_INSERT
                )
            }

        }

        b.swipeRefreshLayout.setOnRefreshListener {
            // actualización de datos
            setUpEntry(b.rvListEntry)
            b.swipeRefreshLayout.isRefreshing = false
        }

        internetChecker = InternetConnectivityChecker(this)
        internetChecker.startCheckingConnectivity { isConnected ->
            if (isConnected) {
                // El dispositivo tiene conexión a Internet
                // Toast.makeText(this, "Conectado",Toast.LENGTH_SHORT).show()
                print("conected")
                b.ivBtnOption.visibility = View.VISIBLE
                b.tvtema.text = " Blog de Internet."
            } else {
                // El dispositivo no tiene conexión a Internet
                Toast.makeText(this, "Sin conexion a internet", Toast.LENGTH_SHORT).show()
                ShowListView()
                b.ivBtnOption.visibility = View.GONE
                b.tvtema.text = " Blog de Internet. Offline"
                //muestra la lista offline
                OffLine(b.rvListEntry)
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        internetChecker.stopCheckingConnectivity()
    }

    fun setUpEntry(recyclerView: RecyclerView) {
        try {

            vm.fetchEntry.observe(this) { res ->

                when (res) {
                    is Resource.Loading -> {
                        Log.d("Loading", "${res}")
                    }

                    is Resource.Success -> {
                        Log.d("Entryres", "${res.data}")
                        setUpEntryRecycler(res.data, recyclerView)
                    }

                    is Resource.Failure -> {
                        Log.e("Entryres", "${res.exception}")
                    }

                    else -> {
                        Log.d("Entryres", "${res}")
                    }
                }

            }
        } catch (e: Exception) {
            println("ErrrorFra: " + e)
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show()
        }
    }

    fun ShowListView() {
        b.llViewForms.visibility = View.GONE
        b.llViewList.visibility = View.VISIBLE
        option = "list"
        b.ivBtnOption.setImageResource(R.drawable.baseline_edit_24)
    }

    fun searchEntry(recyclerView: RecyclerView, search: String) {

        ShowListView()

        setUpEntry(b.rvListEntry)

        vm.setSearchEntry(search)
        vm.searchEntry.observe(this) { res ->

            when (res) {
                is Resource.Loading -> {
                    Log.d("Loading", "${res}")
                }

                is Resource.Success -> {
                    // Supongamos que tienes un MutableLiveData llamado liveDataList

                    Log.d("Entryres", "${res.data}")

                    setUpEntryRecycler(res.data, recyclerView)
                }

                is Resource.Failure -> {
                    Log.e("Entryres", "${res.exception}")
                }

                else -> {
                    Log.d("Entryres", "${res}")
                }
            }

        }
    }


    fun setUpEntryRecycler(le: List<Entry>, recyclerView: RecyclerView) {
        val adapter = AdapterList(this, le, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun OfflinesetUpEntryRecycler(le: List<EntryEntity>, recyclerView: RecyclerView) {
        val adapter = AdapterListOffline(this, le,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun insertEntry(
        title: String,
        autor: String,
        contenido: String,
        action: String
    ) {

        val params = HashMap<String?, String?>()
        params["action"] = action
        params["titulo"] = title
        params["autor"] = autor
        params["contenido"] = contenido


        vm.addEntry(params)
        vm.cleanBox(list_EditText)

    }


    fun validarVacios(): Boolean {
        var bol = vm.validationBox(list_EditText)
        return bol
    }

    fun OffLine(recyclerView: RecyclerView) {

        vm.entryList.observe(this, Observer { res ->
            OfflinesetUpEntryRecycler(res, recyclerView)
        })

        // Llamar para obtener las entradas cuando sea necesario
        vm.loadEntries()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_entry, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Lógica cuando se envía el formulario de búsqueda
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Lógica mientras se escribe en el campo de búsqueda
                searchEntry(b.rvListEntry, newText!!)
                return true
            }
        })

        return true
    }

    override fun onUserClick(ing: Entry, position: Int) {
        print(position)
        val customDialog = DialogEntry(this, ing, vm,null,"online")
        customDialog.show()

    }

    override fun onUserClick(ing: EntryEntity, position: Int) {
        val customDialog = DialogEntry(this, null, vm,ing,"offline" )
        customDialog.show()
    }


}