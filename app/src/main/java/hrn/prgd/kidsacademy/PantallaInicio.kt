package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class PantallaInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_inicio)
    }

    fun onBanderasClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "banderas")
        startActivity(intent)
    }
    fun onSeriesClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "series")
        startActivity(intent)
    }
    fun onPeliculasClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "peliculas")
        startActivity(intent)
    }
    fun onCulturaClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "cultura")
        startActivity(intent)
    }

    fun onHistoriaClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "historia")
        startActivity(intent)
    }

    fun onArteClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "arte")
        startActivity(intent)
    }

    fun onGeografiaClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "geografia")
        startActivity(intent)
    }

    fun onAnimeClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "anime")
        startActivity(intent)
    }
    fun onCienciasClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "ciencias")
        startActivity(intent)
    }
    fun onLibrosClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "libros")
        startActivity(intent)
    }
    fun onVideojuegosClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "videojuegos")
        startActivity(intent)
    }
    fun onMusicaClick(view: View) {
        val intent = Intent(this, Niveles::class.java)
        intent.putExtra("categoria", "musica")
        startActivity(intent)
    }
    fun onPuntajesClick(view: View) {
        val intent = Intent(this, Puntajes::class.java)
        startActivity(intent)
    }
}
