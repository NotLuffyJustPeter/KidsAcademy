package hrn.prgd.kidsacademy

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
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
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        etFechaNacimiento.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                etFechaNacimiento.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            }, year, month, day)

            datePickerDialog.show()
        }

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val fechaNacimiento = etFechaNacimiento.text.toString()
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()

            if (validarRegistro(nombre, fechaNacimiento, correo, contrasena)) {
                guardarUsuario(nombre, fechaNacimiento, correo, contrasena)
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PantallaLogin::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error en el registro. Verifica los datos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarRegistro(nombre: String, fechaNacimiento: String, correo: String, contrasena: String): Boolean {
        return nombre.isNotEmpty() &&
                fechaNacimiento.isNotEmpty() &&
                correo.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() &&
                contrasena.length >= 6
    }

    private fun guardarUsuario(nombre: String, fechaNacimiento: String, correo: String, contrasena: String) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nombre", nombre)
        editor.putString("fechaNacimiento", fechaNacimiento)
        editor.putString("correo", correo)
        editor.putString("contrasena", contrasena)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }
}

