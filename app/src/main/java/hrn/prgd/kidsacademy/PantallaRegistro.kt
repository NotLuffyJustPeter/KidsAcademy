package hrn.prgd.kidsacademy

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class PantallaRegistro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_registro)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val cbxContra = findViewById<CheckBox>(R.id.verContra)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        cbxContra.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etContrasena.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etContrasena.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etContrasena.typeface = ResourcesCompat.getFont(this, R.font.comfy_feeling)
            etContrasena.setSelection(etContrasena.text.length)
        }

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
            val fechaNacimientoStr = etFechaNacimiento.text.toString()
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()
            val fechaNacimiento = Calendar.getInstance()
            val dateParts = fechaNacimientoStr.split("/")

            if (dateParts.size == 3) {
                val day = dateParts[0].toInt()
                val month = dateParts[1].toInt() - 1
                val year = dateParts[2].toInt()
                fechaNacimiento.set(year, month, day)
            } else {
                Toast.makeText(this, "Por favor, ingresa una fecha de nacimiento válida.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (validarRegistro(nombre, fechaNacimiento, correo, contrasena)) {
                guardarUsuario(nombre, fechaNacimientoStr, correo, contrasena)
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PantallaLogin::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error en el registro. Verifica los datos.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validarRegistro(
        nombre: String,
        fechaNacimiento: Calendar,
        correo: String,
        contrasena: String
    ): Boolean {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val birthYear = fechaNacimiento.get(Calendar.YEAR)
        var isValid = true

        if (nombre.isEmpty()) {
            findViewById<EditText>(R.id.etNombre).error = "El nombre no puede estar vacío"
            isValid = false
        }

        val age = currentYear - birthYear
        if (age < 5 || age > 18) {
            findViewById<EditText>(R.id.etFechaNacimiento).error =
                "La aplicación es solo para usuarios entre 5 y 18 años"
            isValid = false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            findViewById<EditText>(R.id.etCorreo).error = "Correo no válido"
            isValid = false
        }

        if (contrasena.length < 6) {
            findViewById<EditText>(R.id.etContrasena).error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }
        return isValid
    }



    private fun guardarUsuario(nombre: String, fechaNacimiento: String, correo: String, contrasena: String) {
        val dbHelper = DBHelper(this)
        val result = dbHelper.insertUsuario(nombre, fechaNacimiento, correo, contrasena)

        if (result != -1L) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PantallaLogin::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
        }
    }

}

