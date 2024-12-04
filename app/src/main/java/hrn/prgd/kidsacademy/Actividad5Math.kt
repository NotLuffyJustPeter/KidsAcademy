package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad5Math : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    private var correctas: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad5_math)

        dbHelper = DBHelper(this)
        val ControlSumaFrac = findViewById<ControlSumaFrac>(R.id.ControlSumaFrac)
        correctas = ControlSumaFrac.correctas

        val btnFinalizar = findViewById<Button>(R.id.btnSalir)

        btnFinalizar.setOnClickListener {
            val isApproved = correctas >= 7

            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad5math")
                Toast.makeText(this, "¡No aprobaste la actividad 5, intenta nuevamente!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡Actividad 5 completada! Actividad 6 desbloqueada.", Toast.LENGTH_SHORT).show()
            }

            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}