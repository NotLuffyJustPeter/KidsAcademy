package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad1Spanish : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad1_spanish)

        dbHelper = DBHelper(this)

        val correctCount = 0
        val btnFinalizar = findViewById<Button>(R.id.btnSalir)

        btnFinalizar.setOnClickListener {
            val isApproved = correctCount >= 7  // Aprobación si la mitad o más son correctas

            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad1")
                Toast.makeText(this, "¡No aprobaste la actividad 1, intenta nuevamente!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡Actividad 1 completada! Actividad 2 desbloqueada.", Toast.LENGTH_SHORT).show()
            }

            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
