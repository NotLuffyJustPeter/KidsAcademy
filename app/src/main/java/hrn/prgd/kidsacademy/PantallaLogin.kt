package hrn.prgd.kidsacademy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PantallaLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_login)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val contrasena = etContrasena.text.toString()

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
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val storedNombre = sharedPreferences.getString("nombre", null)
        val storedContrasena = sharedPreferences.getString("contrasena", null)

        return storedNombre == nombre && storedContrasena == contrasena
    }
}

