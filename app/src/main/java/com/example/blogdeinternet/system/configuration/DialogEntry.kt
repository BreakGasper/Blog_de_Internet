import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.blogdeinternet.databinding.ItemListEntryBinding
import com.breakapp.apv2.retrofit.pojos.Entry
import com.breakapp.apv2.ui.configs.viewmodel.EntryViewModel
import com.example.blogdeinternet.database.entitys.EntryEntity

class DialogEntry(context: Context, private val entry: Entry?, private val entryViewModel: EntryViewModel, private val entryentity: EntryEntity?, private val tipo: String) : AppCompatDialog(context) {

    private lateinit var binding: ItemListEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = ItemListEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var di_str = "dialog"

        if (tipo.equals("online")){
            OnLine(di_str)
        }else OffLine()

        binding.btnDialog.setOnClickListener{
            dismiss()
            binding.llDialog.visibility = View.GONE
            binding.ivDownload.visibility = View.GONE

        }
    }

    private fun OffLine(){
        binding.llDialog.visibility = View.VISIBLE
        // Usar el objeto `entry` para establecer el texto en los TextViews
        binding.tvApTitle.text = entryentity!!.titulo
        binding.tvApAutor.text = entryentity!!.autor
        binding.tvApDate.text = entryentity!!.fechaPublicacion
        binding.tvApContent.text = entryentity!!.contenido

    }

    private fun OnLine(di_str:String){
        if (di_str.equals("dialog")){
            binding.llDialog.visibility = View.VISIBLE
            binding.ivDownload.visibility = View.VISIBLE
        }
        // Usar el objeto `entry` para establecer el texto en los TextViews
        binding.tvApTitle.text = entry!!.titulo
        binding.tvApAutor.text = entry!!.autor
        binding.tvApDate.text = entry!!.fechaPublicacion
        binding.tvApContent.text = entry!!.contenido

        binding.ivDownload.setOnClickListener{
            try {
                val entryEntity = EntryEntity(0, entry.id_blog, entry.titulo, entry.autor, entry.fechaPublicacion, entry.contenido)
                entryViewModel.insertEntry(entryEntity)
                Toast.makeText(context, "Descargado!!!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                // Maneja la excepción aquí
                Toast.makeText(context, "Error al descargar: $e", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
            dismiss()
        }

    }
}
