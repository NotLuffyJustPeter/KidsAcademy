package hrn.prgd.kidsacademy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class PantallaLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_login)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val cbxContra = findViewById<CheckBox>(R.id.verContra)

        cbxContra.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etContrasena.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etContrasena.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etContrasena.typeface = ResourcesCompat.getFont(this, R.font.comfy_feeling)
            etContrasena.setSelection(etContrasena.text.length)
        }


        btnIngresar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val contrasena = etContrasena.text.toString()

            // Verifica si los datos coinciden con los almacenados en la base de datos
            if (iniciarSesion(nombre, contrasena)) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PantallaInicio::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Datos incorrectos. Revisa tu información", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun iniciarSesion(nombre: String, contrasena: String): Boolean {
        val dbHelper = DBHelper(this)
        return dbHelper.verificarUsuario(nombre, contrasena)
    }
}

