package hrn.prgd.kidsacademy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad3Etica : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad3_etica)
        val correctCount = 0
        val btnFinalizar = findViewById<Button>(R.id.btnSalir)
        btnFinalizar.setOnClickListener {
            val isApproved = correctCount >= 4
            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad3etica")
                Toast.makeText(this, "¡No aprobaste la actividad 3, intenta nuevamente!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡Actividad 3 completada! Actividad 4 desbloqueada.", Toast.LENGTH_SHORT).show()
            }
            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}