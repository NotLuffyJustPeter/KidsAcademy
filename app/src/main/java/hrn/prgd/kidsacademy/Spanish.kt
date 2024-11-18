package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class Spanish : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulo_spanish)
    }

    fun onActividad1Click(view: View) {
        val intent = Intent(this, Spanish::class.java)
        startActivity(intent)
    }

    fun onActividad2Click(view: View) {
        val intent = Intent(this, Math::class.java)
        startActivity(intent)
    }

    fun onActividad3Click(view: View) {
        val intent = Intent(this, Etica::class.java)
        startActivity(intent)
    }

    fun onActividad4Click(view: View) {
        val intent = Intent(this, Ciencias::class.java)
        startActivity(intent)
    }

    fun onActividad5Click(view: View) {
        val intent = Intent(this, Etica::class.java)
        startActivity(intent)
    }

    fun onActividad6Click(view: View) {
        val intent = Intent(this, Ciencias::class.java)
        startActivity(intent)
    }
}