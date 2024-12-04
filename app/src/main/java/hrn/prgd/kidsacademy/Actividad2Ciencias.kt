package hrn.prgd.kidsacademy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad2Ciencias : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private var correctas: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad2_ciencia)
        dbHelper = DBHelper(this)
        val controlAnimal1 = findViewById<ControlAnimal1>(R.id.controlAnimal1)
        val btnFinalizar = findViewById<Button>(R.id.btnSalir)
        btnFinalizar.setOnClickListener {
            correctas = controlAnimal1.correctas
            val isApproved = correctas >= 7
            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad2ciencias")
                Toast.makeText(this, "¡No aprobaste la actividad 2, intenta nuevamente!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡Actividad 2 completada! Actividad 3 desbloqueada.", Toast.LENGTH_SHORT).show()
            }
            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}