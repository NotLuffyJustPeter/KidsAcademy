package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Ciencias : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulo_ciencia)

        dbHelper = DBHelper(this)

        // Verificar si las actividades están desbloqueadas
        checkAndUpdateActivities()
    }

    private fun checkAndUpdateActivities() {
        val actividades = listOf(
            "actividad2ciencias" to R.id.actividad2ciencias,
            "actividad3ciencias" to R.id.actividad3ciencias,
            "actividad4ciencias" to R.id.actividad4ciencias,
            "actividad5ciencias" to R.id.actividad5ciencias,
            "actividad6ciencias" to R.id.actividad6ciencias
        )

        actividades.forEach { (actividad, id) ->
            val button = findViewById<LinearLayout>(id)
            if (dbHelper.isCienciasActivityUnlocked(actividad)) {
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
        val intent = Intent(this, Actividad1Ciencias::class.java)
        startActivityForResult(intent, 1)
    }

    fun onActividad2Click(view: View) {
        val intent = Intent(this, Actividad2Ciencias::class.java)
        startActivityForResult(intent, 2)
    }

    fun onActividad3Click(view: View) {
        val intent = Intent(this, Actividad3Ciencias::class.java)
        startActivityForResult(intent, 3)
    }

    fun onActividad4Click(view: View) {
        val intent = Intent(this, Actividad4Ciencias::class.java)
        startActivityForResult(intent, 4)
    }

    fun onActividad5Click(view: View) {
        val intent = Intent(this, Actividad5Ciencias::class.java)
        startActivityForResult(intent, 5)
    }

    fun onActividad6Click(view: View) {
        val intent = Intent(this, Actividad6Ciencias::class.java)
        startActivityForResult(intent, 6)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> dbHelper.desbloquearSiguienteCienciasActividad("actividad1ciencias")
                2 -> dbHelper.desbloquearSiguienteCienciasActividad("actividad2ciencias")
                3 -> dbHelper.desbloquearSiguienteCienciasActividad("actividad3ciencias")
                4 -> dbHelper.desbloquearSiguienteCienciasActividad("actividad4ciencias")
                5 -> dbHelper.desbloquearSiguienteCienciasActividad("actividad5ciencias")
                6 -> dbHelper.desbloquearSiguienteCienciasActividad("actividad6ciencias")
            }
            checkAndUpdateActivities() // Actualizar los botones
        }
    }
}
