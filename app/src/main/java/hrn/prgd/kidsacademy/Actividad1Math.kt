package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class Actividad1Math : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private var correctas: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad1_math)

        dbHelper = DBHelper(this)

        val controlSuma = findViewById<ControlSuma>(R.id.controlSuma)
        val btnFinalizar = findViewById<Button>(R.id.btnSalir)

        btnFinalizar.setOnClickListener {
            correctas = controlSuma.correctas
            val isApproved = correctas >= 7

            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad1math")
                Toast.makeText(this, "¡Actividad 1 completada! Actividad 2 desbloqueada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡No aprobaste la actividad 1, intenta nuevamente!", Toast.LENGTH_SHORT).show()
            }

            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

}

