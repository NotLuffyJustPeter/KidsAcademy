package hrn.prgd.kidsacademy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Actividad3Math : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private var correctas: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad3_math)
        dbHelper = DBHelper(this)
        val ControlMulti = findViewById<ControlMulti>(R.id.ControlMulti)
        correctas = ControlMulti.correctas
        val btnFinalizar = findViewById<Button>(R.id.btnSalir)
        btnFinalizar.setOnClickListener {
            val isApproved = correctas >= 7
            if (isApproved) {
                dbHelper.desbloquearSiguienteActividad("actividad3math")
                Toast.makeText(this, "¡Actividad 3 completada! Actividad 4 desbloqueada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡No aprobaste la actividad 3, intenta nuevamente!", Toast.LENGTH_SHORT).show()
            }
            val resultIntent = Intent()
            resultIntent.putExtra("isApproved", isApproved)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}