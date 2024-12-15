package hrn.prgd.kidsacademy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad4Spanish : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad4_spanish)
        dbHelper = DBHelper(this)
        val correctCount = 0
        val btnFinalizar = findViewById<Button>(R.id.btnSalir)
        btnFinalizar.setOnClickListener {
            val isApproved = correctCount >= 7
            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad4")
                Toast.makeText(this, "¡Actividad 4 completada! Actividad 5 desbloqueada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡No aprobaste la actividad 5, intenta nuevamente!", Toast.LENGTH_SHORT).show()
            }
            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }
}