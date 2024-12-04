package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad6Spanish : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad6_spanish)

        dbHelper = DBHelper(this)

        val correctCount = 0
        val btnFinalizar = findViewById<Button>(R.id.btnSalir)

        btnFinalizar.setOnClickListener {
            val isApproved = correctCount >= 7  // Considera aprobado si la mitad o más son correctas

            // Si aprobó, desbloquear la siguiente actividad (Actividad4)
            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad6")
                Toast.makeText(this, "¡Actividad 6 completada!", Toast.LENGTH_SHORT).show()
            }

            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }
    }
}