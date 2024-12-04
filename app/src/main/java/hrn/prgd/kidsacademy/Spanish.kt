package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Spanish : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulo_spanish)

        dbHelper = DBHelper(this)

        // Verificar si las actividades están desbloqueadas
        checkAndUpdateActivities()
    }

    private fun checkAndUpdateActivities() {
        val actividades = listOf(
            "actividad2" to R.id.actividad2,
            "actividad3" to R.id.actividad3,
            "actividad4" to R.id.actividad4,
            "actividad5" to R.id.actividad5,
            "actividad6" to R.id.actividad6
        )

        actividades.forEach { (actividad, id) ->
            val button = findViewById<LinearLayout>(id)
            if (dbHelper.isActivityUnlocked(actividad)) {
                button.isClickable = true
                button.alpha = 1.0f
            } else {
                button.isClickable = false
                button.alpha = 0.5f
            }
        }
    }

    // Métodos de activación de las actividades
    fun onActividad1Click(view: View) {
        val intent = Intent(this, Actividad1Spanish::class.java)
        startActivityForResult(intent, 1)
    }

    fun onActividad2Click(view: View) {
        val intent = Intent(this, Actividad2Spanish::class.java)
        startActivityForResult(intent, 2)
    }

    fun onActividad3Click(view: View) {
        val intent = Intent(this, Actividad3Spanish::class.java)
        startActivityForResult(intent, 3)
    }

    fun onActividad4Click(view: View) {
        val intent = Intent(this, Actividad4Spanish::class.java)
        startActivityForResult(intent, 4)
    }

    fun onActividad5Click(view: View) {
        val intent = Intent(this, Actividad5Spanish::class.java)
        startActivityForResult(intent, 5)
    }

    fun onActividad6Click(view: View) {
        val intent = Intent(this, Actividad6Spanish::class.java)
        startActivityForResult(intent, 6)
    }

    // Este método es llamado cuando regresa del resultado de la actividad
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> dbHelper.desbloquearSiguienteActividad("actividad1")
                2 -> dbHelper.desbloquearSiguienteActividad("actividad2")
                3 -> dbHelper.desbloquearSiguienteActividad("actividad3")
                4 -> dbHelper.desbloquearSiguienteActividad("actividad4")
                5 -> dbHelper.desbloquearSiguienteActividad("actividad5")
                6 -> dbHelper.desbloquearSiguienteActividad("actividad6")
            }
            checkAndUpdateActivities() // Actualizar los botones
        }
    }
}
