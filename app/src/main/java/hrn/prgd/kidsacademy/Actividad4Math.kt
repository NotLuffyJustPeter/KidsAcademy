package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.service.controls.Control
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad4Math : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    private var correctas: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad4_math)

        dbHelper = DBHelper(this)
        val ControlDiv = findViewById<ControlSuma>(R.id.ControlDiv)
        correctas = ControlDiv.correctas

        val btnFinalizar = findViewById<Button>(R.id.btnSalir)

        btnFinalizar.setOnClickListener {
            val isApproved = correctas >= 7

            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad4math")
                Toast.makeText(this, "¡Actividad 4 completada! Actividad 5 desbloqueada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡No aprobaste la actividad 4, intenta nuevamente!", Toast.LENGTH_SHORT).show()
            }

            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}