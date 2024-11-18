package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Math : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulo_math)
    }
    fun onActividad1Click(view: View) {
        val intent = Intent(this, Actividad1Math::class.java)
        startActivity(intent)
    }

    fun onActividad2Click(view: View) {
        val intent = Intent(this, Actividad2Math::class.java)
        startActivity(intent)
    }

    fun onActividad3Click(view: View) {
        val intent = Intent(this, Actividad3Math::class.java)
        startActivity(intent)
    }

    fun onActividad4Click(view: View) {
        val intent = Intent(this, Actividad4Math::class.java)
        startActivity(intent)
    }

    fun onActividad5Click(view: View) {
        val intent = Intent(this, Actividad5Math::class.java)
        startActivity(intent)
    }

    fun onActividad6Click(view: View) {
        val intent = Intent(this, Actividad6Math::class.java)
        startActivity(intent)
    }

}