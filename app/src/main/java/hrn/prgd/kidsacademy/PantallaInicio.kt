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

    fun onSpanishClick(view: View) {
        val intent = Intent(this, Spanish::class.java)
        startActivity(intent)
    }

    fun onMathClick(view: View) {
        val intent = Intent(this, Math::class.java)
        startActivity(intent)
    }

    fun onEticaClick(view: View) {
        val intent = Intent(this, Etica::class.java)
        startActivity(intent)
    }

    fun onCienciasClick(view: View) {
        val intent = Intent(this, Ciencias::class.java)
        startActivity(intent)
    }
}