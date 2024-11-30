package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Ciencias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulo_ciencia)
    }
    fun onActividad1Click(view: View) {
        val intent = Intent(this, Actividad1Ciencias::class.java)
        startActivity(intent)
    }

    fun onActividad2Click(view: View) {
        val intent = Intent(this, Actividad2Ciencias::class.java)
        startActivity(intent)
    }

    fun onActividad3Click(view: View) {
        val intent = Intent(this, Actividad3Ciencias::class.java)
        startActivity(intent)
    }

    fun onActividad4Click(view: View) {
        val intent = Intent(this, Actividad4Ciencias::class.java)
        startActivity(intent)
    }

    fun onActividad5Click(view: View) {
        val intent = Intent(this, Actividad5Ciencias::class.java)
        startActivity(intent)
    }

    fun onActividad6Click(view: View) {
        val intent = Intent(this, Actividad6Ciencias::class.java)
        startActivity(intent)
    }

}