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

class Resultado2Ciencias : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultados2_ciencias)

        dbHelper = DBHelper(this)

        val correctCount = intent.getIntExtra("correct_count", 0)
        val resultText = findViewById<TextView>(R.id.resultText)
        resultText.text = "¡Felicidades! Asignaste correctamente los $correctCount alimentos."

        val ciepuntaje3 = correctCount - 5
        val sharedPreferences = getSharedPreferences("Puntajes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("ciepuntaje3", ciepuntaje3)
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

        // Desbloquea la siguiente actividad automáticamente
        dbHelper.desbloquearSiguienteCienciasActividad("actividad3ciencias")
        Toast.makeText(this, "¡Actividad 3 completada! Actividad 4 desbloqueada.", Toast.LENGTH_SHORT).show()
    }

    private fun reiniciarActividad() {
        val intent = Intent(this, Actividad3Ciencias::class.java)
        startActivity(intent)
        finish()
    }

    private fun salir() {
        val intent = Intent(this, Ciencias::class.java)
        startActivity(intent)
        finish()
    }
}