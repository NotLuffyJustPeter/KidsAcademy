package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.LinearLayout.GONE
import android.widget.LinearLayout.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Resultado3Ciencias : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultados3_ciencias)
        dbHelper = DBHelper(this)
        val resultText = findViewById<TextView>(R.id.resultText)
        resultText.text = "¡Felicidades! Encontraste todos los pares correspondientes."
        val puntaje = 10
        val sharedPreferences = getSharedPreferences("Puntajes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("ciepuntaje5", puntaje)
        editor.apply()
        val intent = Intent(this, Puntajes::class.java)
        startActivity(intent)
        val btnReiniciar = findViewById<Button>(R.id.btnReiniciar)
        val btnSalir = findViewById<Button>(R.id.btnSalir)
        btnReiniciar.setOnClickListener {
            reiniciarActividad()
        }
        btnSalir.setOnClickListener {
            salir()
        }

        dbHelper.desbloquearSiguienteCienciasActividad("actividad5ciencias")
        Toast.makeText(this, "¡Actividad 5 completada! Actividad 6 desbloqueada.", Toast.LENGTH_SHORT).show()
    }

    private fun reiniciarActividad() {
        val intent = Intent(this, Actividad5Ciencias::class.java)
        startActivity(intent)
        finish()
    }

    private fun salir() {
        val intent = Intent(this, Ciencias::class.java)
        startActivity(intent)
        finish()
    }
}