package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PantallaSeleccion : AppCompatActivity() {

    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_seleccion)

        // Inicializa los botones usando findViewById
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnRegistrar = findViewById(R.id.btnRegistrar)

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
