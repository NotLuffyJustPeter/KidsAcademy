package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Resultado2Spanish : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultado2_spanish)

        dbHelper = DBHelper(this)

        val correctCount = intent.getIntExtra("correct_count", 0)
        val resultText = findViewById<TextView>(R.id.resultText)
        resultText.text = "¡Felicidades! Asignaste correctamente los $correctCount elementos."

        val btnReiniciar = findViewById<Button>(R.id.btnReiniciar)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        btnReiniciar.setOnClickListener {
            reiniciarActividad()
        }

        btnSalir.setOnClickListener {
            salir()
        }

        // Desbloquea la siguiente actividad automáticamente
        dbHelper.desbloquearSiguienteActividad("actividad2")
        Toast.makeText(this, "¡Actividad 2 completada! Actividad 3 desbloqueada.", Toast.LENGTH_SHORT).show()
    }

    private fun reiniciarActividad() {
        val intent = Intent(this, Actividad2Spanish::class.java)
        startActivity(intent)
        finish() // Finaliza la actividad actual para no dejarla en el stack
    }

    private fun salir() {
        val intent = Intent(this, Spanish::class.java) // O cualquier otra actividad principal
        startActivity(intent)
        finish() // Finaliza la actividad actual
    }
}
