package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Etica : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulo_etica)
    }

    fun onActividad1Click(view: View) {
        val intent = Intent(this, Actividad1Etica::class.java)
        startActivity(intent)
    }

    fun onActividad2Click(view: View) {
        val intent = Intent(this, Actividad2Etica::class.java)
        startActivity(intent)
    }

    fun onActividad3Click(view: View) {
        val intent = Intent(this, Actividad3Etica::class.java)
        startActivity(intent)
    }

    fun onActividad4Click(view: View) {
        val intent = Intent(this, Actividad4Etica::class.java)
        startActivity(intent)
    }

    fun onActividad5Click(view: View) {
        val intent = Intent(this, Actividad5Etica::class.java)
        startActivity(intent)
    }

    fun onActividad6Click(view: View) {
        val intent = Intent(this, Actividad6Etica::class.java)
        startActivity(intent)
    }
}