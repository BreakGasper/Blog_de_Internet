package com.example.blogdeinternet.ui.adapters

import DialogEntry
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.breakapp.apv2.retrofit.pojos.Entry
import com.example.blogdeinternet.database.entitys.EntryEntity
import com.example.blogdeinternet.databinding.ItemListEntryBinding

class AdapterListOffline(
    private val context: Context,
    private val agendalist: List<EntryEntity>,
    private val itemClickOffline: AdapterListOffline.OnUserClickListener
) : RecyclerView.Adapter<AdapterListOffline.EntryViewHolder>() {

    interface OnUserClickListener {
        fun onUserClick(ing: EntryEntity, position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemListEntryBinding.inflate(inflater, parent, false)
        return EntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val ing = agendalist[position]
        holder.bind(ing)
    }

    override fun getItemCount(): Int {
        return agendalist.size
    }

    inner class EntryViewHolder(private val binding: ItemListEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: EntryEntity) {
            binding.tvApAutor.text = entry.autor
            binding.tvApTitle.text = entry.titulo
            binding.tvApDate.text = entry.fechaPublicacion

            val contenidoCompleto = entry.contenido // Obtener completo desde tu fuente de datos
            val primerosSetentaCaracteres =
                if (contenidoCompleto.length > 70) contenidoCompleto.substring(0, 70) else contenidoCompleto
            binding.tvApContent.text = primerosSetentaCaracteres

            binding.root.setOnClickListener {
                itemClickOffline.onUserClick(entry, adapterPosition)
            }

        }
    }
}
