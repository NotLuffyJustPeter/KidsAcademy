package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.LinearLayout.GONE
import android.widget.LinearLayout.VISIBLE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Resultado2Ciencias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultados2_ciencias)

        val correctCount = intent.getIntExtra("correct_count", 0)

        val resultText = findViewById<TextView>(R.id.resultText)
        resultText.text = "¡Felicidades! Asignaste correctamente los $correctCount alimentos"

        val btnReiniciar = findViewById<Button>(R.id.btnReiniciar)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        btnReiniciar.setOnClickListener(OnClickListener { v: View? -> reiniciar() })
        btnSalir.setOnClickListener(OnClickListener { v: View? -> salir() })

    }
    private fun reiniciar() {
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