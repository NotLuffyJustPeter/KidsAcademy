package hrn.prgd.kidsacademy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Niveles : AppCompatActivity() {

    private var categoria: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulo_niveles)
        categoria = intent.getStringExtra("categoria") ?: "default_categoria"

        setBackgroundColorByCategoria(categoria)
    }

    private fun setBackgroundColorByCategoria(categoria: String?) {
        val rootLayout = findViewById<ScrollView>(R.id.rootLayout)
        val categoriaText = findViewById<TextView>(R.id.categoria)
        val nivelesText = findViewById<TextView>(R.id.nivelesText)


        when (categoria) {
            "banderas" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#FFCCBC"))
                categoriaText.text = "Banderas"
                categoriaText.setTextColor(Color.parseColor("#D84315"))
                nivelesText.setTextColor(Color.parseColor("#D84315"))
            }
            "series" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#ffdec2"))
                categoriaText.text = "Series"
                categoriaText.setTextColor(Color.parseColor("#ff7703"))
                nivelesText.setTextColor(Color.parseColor("#ff7703"))
            }
            "peliculas" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#FFECB3"))
                categoriaText.text = "Películas"
                categoriaText.setTextColor(Color.parseColor("#FFA000"))
                nivelesText.setTextColor(Color.parseColor("#FFA000"))
            }
            "cultura" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#C5E1A5"))
                categoriaText.text = "Cultura Pop"
                categoriaText.setTextColor(Color.parseColor("#33691E"))
                nivelesText.setTextColor(Color.parseColor("#33691E"))
            }
            "historia" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#adcf86"))
                categoriaText.text = "Historia"
                categoriaText.setTextColor(Color.parseColor("#518910"))
                nivelesText.setTextColor(Color.parseColor("#518910"))
            }
            "arte" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#92ffc7"))
                categoriaText.text = "Arte"
                categoriaText.setTextColor(Color.parseColor("#10cb6b"))
                nivelesText.setTextColor(Color.parseColor("#10cb6b"))
            }
            "geografia" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#80DEEA"))
                categoriaText.text = "Geografía"
                categoriaText.setTextColor(Color.parseColor("#004D40"))
                nivelesText.setTextColor(Color.parseColor("#004D40"))
            }
            "anime" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#3cd7ef"))
                categoriaText.text = "Anime"
                categoriaText.setTextColor(Color.parseColor("#0147a5"))
                nivelesText.setTextColor(Color.parseColor("#0147a5"))
            }
            "ciencias" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#b18dff"))
                categoriaText.text = "Ciencias"
                categoriaText.setTextColor(Color.parseColor("#3f1797"))
                nivelesText.setTextColor(Color.parseColor("#3f1797"))
            }
            "libros" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#a434f6"))
                categoriaText.text = "Libros"
                categoriaText.setTextColor(Color.parseColor("#3e006b"))
                nivelesText.setTextColor(Color.parseColor("#3e006b"))
            }
            "videojuegos" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#ffa8f5"))
                categoriaText.text = "Videojuegos"
                categoriaText.setTextColor(Color.parseColor("#ff00e2"))
                nivelesText.setTextColor(Color.parseColor("#ff00e2"))
            }
            "musica" -> {
                rootLayout.setBackgroundColor(Color.parseColor("#ff80bf"))
                categoriaText.text = "Música"
                categoriaText.setTextColor(Color.parseColor("#da0870"))
                nivelesText.setTextColor(Color.parseColor("#da0870"))
            }
            else -> rootLayout.setBackgroundColor(Color.WHITE) // Color predeterminado
        }
    }

    // Métodos de activación de las actividades
   fun onActividad1Click(view: View) {
        val intent = Intent(this, Actividad3Spanish::class.java)
        intent.putExtra("categoria", categoria)
        startActivity(intent)
    }

   /* fun onActividad2Click(view: View) {
        val intent = Intent(this, Actividad2Spanish::class.java)
        intent.putExtra("categoria", categoria)
        startActivityForResult(intent, 2)
    }

    fun onActividad3Click(view: View) {
        val intent = Intent(this, Actividad3Spanish::class.java)
        intent.putExtra("categoria", categoria)
        startActivityForResult(intent, 3)
    }

    fun onActividad4Click(view: View) {
        val intent = Intent(this, Actividad4Spanish::class.java)
        intent.putExtra("categoria", categoria)
        startActivityForResult(intent, 4)
    }

    fun onActividad5Click(view: View) {
        val intent = Intent(this, Actividad5Spanish::class.java)
        intent.putExtra("categoria", categoria)
        startActivityForResult(intent, 5)
    }

    fun onActividad6Click(view: View) {
        val intent = Intent(this, Actividad6Spanish::class.java)
        intent.putExtra("categoria", categoria)
        startActivityForResult(intent, 6)
    }*/
}
