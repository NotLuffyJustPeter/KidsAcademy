package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResultadoSpanish : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultado_spanish)

        dbHelper = DBHelper(this)

        val resultText = findViewById<TextView>(R.id.resultText)
        resultText.text = "¡Felicidades! Encontraste todos los pares correspondientes."

        val btnReiniciar = findViewById<Button>(R.id.btnReiniciar)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        btnReiniciar.setOnClickListener {
            reiniciarActividad()
        }

        btnSalir.setOnClickListener {
            salir()
        }

        // Desbloquea la siguiente actividad automáticamente
        dbHelper.desbloquearSiguienteActividad("actividad5")
        Toast.makeText(this, "¡Actividad 5 completada! Actividad 6 desbloqueada.", Toast.LENGTH_SHORT).show()
    }

    private fun reiniciarActividad() {
        val intent = Intent(this, Actividad5Spanish::class.java)
        startActivity(intent)
        finish() // Finaliza la actividad actual para no dejarla en el stack
    }

    private fun salir() {
        val intent = Intent(this, Spanish::class.java) // O cualquier otra actividad principal
        startActivity(intent)
        finish() // Finaliza la actividad actual
    }
}
