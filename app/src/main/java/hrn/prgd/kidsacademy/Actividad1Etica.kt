package hrn.prgd.kidsacademy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad1Etica : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad1_etica)
        dbHelper = DBHelper(this)
        val puntaje = intent.getIntExtra("puntaje", 0)
        val btnFinalizar = findViewById<Button>(R.id.btnSalir)
        btnFinalizar.setOnClickListener {
            val isApproved = puntaje >= 4
            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad1etica")
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

