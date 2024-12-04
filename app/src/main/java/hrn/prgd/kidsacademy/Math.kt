package hrn.prgd.kidsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Math : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulo_math)

        dbHelper = DBHelper(this)

        // Verificar si las actividades están desbloqueadas
        checkAndUpdateActivities()
    }

    private fun checkAndUpdateActivities() {
        val actividades = listOf(
            "actividad2math" to R.id.actividad2math,
            "actividad3math" to R.id.actividad3math,
            "actividad4math" to R.id.actividad4math,
            "actividad5math" to R.id.actividad5math,
            "actividad6math" to R.id.actividad6math
        )

        actividades.forEach { (actividad, id) ->
            val button = findViewById<LinearLayout>(id)
            if (dbHelper.isMathActivityUnlocked(actividad)) {
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
        val intent = Intent(this, Actividad1Math::class.java)
        startActivityForResult(intent, 1)
    }

    fun onActividad2Click(view: View) {
        val intent = Intent(this, Actividad2Math::class.java)
        startActivityForResult(intent, 2)
    }

    fun onActividad3Click(view: View) {
        val intent = Intent(this, Actividad3Math::class.java)
        startActivityForResult(intent, 3)
    }

    fun onActividad4Click(view: View) {
        val intent = Intent(this, Actividad4Math::class.java)
        startActivityForResult(intent, 4)
    }

    fun onActividad5Click(view: View) {
        val intent = Intent(this, Actividad5Math::class.java)
        startActivityForResult(intent, 5)
    }

    fun onActividad6Click(view: View) {
        val intent = Intent(this, Actividad6Math::class.java)
        startActivityForResult(intent, 6)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> dbHelper.desbloquearSiguienteMathActividad("actividad1math")
                2 -> dbHelper.desbloquearSiguienteMathActividad("actividad2math")
                3 -> dbHelper.desbloquearSiguienteMathActividad("actividad3math")
                4 -> dbHelper.desbloquearSiguienteMathActividad("actividad4math")
                5 -> dbHelper.desbloquearSiguienteMathActividad("actividad5math")
                6 -> dbHelper.desbloquearSiguienteMathActividad("actividad6math")
            }

            checkAndUpdateActivities() // Actualizar los botones
        }
    }
}
