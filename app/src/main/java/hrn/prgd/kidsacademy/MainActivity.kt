package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_seleccion)

        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, PantallaLogin::class.java)
            startActivity(intent)
        }

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, PantallaRegistro::class.java)
            startActivity(intent)
        }
    }
}
