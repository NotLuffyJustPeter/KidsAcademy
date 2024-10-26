package hrn.prgd.kidsacademy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PantallaRegistro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_registro)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val edad = etEdad.text.toString()
            val contrasena = etContrasena.text.toString()

            if (validarRegistro(nombre, edad, contrasena)) {
                guardarUsuario(nombre, edad, contrasena)
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PantallaLogin::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error en el registro. Verifica los datos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarRegistro(nombre: String, edad: String, contrasena: String): Boolean {
        return nombre.isNotEmpty() && edad.isNotEmpty() && contrasena.length >= 6
    }

    private fun guardarUsuario(nombre: String, edad: String, contrasena: String) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nombre", nombre)
        editor.putString("edad", edad)
        editor.putString("contrasena", contrasena)
        editor.apply()
    }
}
