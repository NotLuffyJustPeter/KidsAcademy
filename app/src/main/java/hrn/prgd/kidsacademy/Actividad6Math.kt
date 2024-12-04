package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad6Math : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    private var correctas: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad6_math)

        dbHelper = DBHelper(this)
        val controlRestaFrac = findViewById<ControlRestaFrac>(R.id.controlRestaFrac)
        correctas = controlRestaFrac.correctas

        val btnFinalizar = findViewById<Button>(R.id.btnSalir)

        btnFinalizar.setOnClickListener {
            val isApproved = correctas >= 7

            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad6math")
                Toast.makeText(this, "¡No aprobaste la actividad 6, intenta nuevamente!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡Actividad 6 completada!", Toast.LENGTH_SHORT).show()
            }

            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}