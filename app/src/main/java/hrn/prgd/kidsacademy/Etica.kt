package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Etica : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulo_etica)

        dbHelper = DBHelper(this)

        // Verificar si las actividades están desbloqueadas
        checkAndUpdateActivities()
    }

    private fun checkAndUpdateActivities() {
        val actividades = listOf(
            "actividad2etica" to R.id.actividad2etica,
            "actividad3etica" to R.id.actividad3etica,
            "actividad4etica" to R.id.actividad4etica,
            "actividad5etica" to R.id.actividad5etica,
            "actividad6etica" to R.id.actividad6etica
        )

        actividades.forEach { (actividad, id) ->
            val button = findViewById<LinearLayout>(id)
            if (dbHelper.isEticaActivityUnlocked(actividad)) {
                button.isClickable = true
                button.alpha = 1.0f // Hacer el botón visible
            } else {
                button.isClickable = false
                button.alpha = 0.5f // Hacerlo más tenue si está bloqueado
            }
        }
    }


    // Métodos de activación de las actividades
    fun onActividad1Click(view: View) {
        val intent = Intent(this, Actividad1Etica::class.java)
        startActivityForResult(intent, 1)
    }

    fun onActividad2Click(view: View) {
        val intent = Intent(this, Actividad2Etica::class.java)
        startActivityForResult(intent, 2)
    }

    fun onActividad3Click(view: View) {
        val intent = Intent(this, Actividad3Etica::class.java)
        startActivityForResult(intent, 3)
    }

    fun onActividad4Click(view: View) {
        val intent = Intent(this, Actividad4Etica::class.java)
        startActivityForResult(intent, 4)
    }

    fun onActividad5Click(view: View) {
        val intent = Intent(this, Actividad5Etica::class.java)
        startActivityForResult(intent, 5)
    }

    fun onActividad6Click(view: View) {
        val intent = Intent(this, Actividad6Etica::class.java)
        startActivityForResult(intent, 6)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> dbHelper.desbloquearSiguienteEticaActividad("actividad1etica")
                2 -> dbHelper.desbloquearSiguienteEticaActividad("actividad2etica")
                3 -> dbHelper.desbloquearSiguienteEticaActividad("actividad3etica")
                4 -> dbHelper.desbloquearSiguienteEticaActividad("actividad4etica")
                5 -> dbHelper.desbloquearSiguienteEticaActividad("actividad5etica")
                6 -> dbHelper.desbloquearSiguienteEticaActividad("actividad6etica")
            }
            checkAndUpdateActivities()
        }
    }
}