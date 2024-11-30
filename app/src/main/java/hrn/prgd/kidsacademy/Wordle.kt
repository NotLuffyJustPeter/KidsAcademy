package hrn.prgd.kidsacademy

import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class Wordle : AppCompatActivity() {

    // Variable para la palabra que el usuario debe adivinar.
    private val WORD = "dubai"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad6_spanish)
        val singleLetterFilter = InputFilter.LengthFilter(1)

        // Acceder a los EditText por medio de findViewById
        val edt11: EditText = findViewById(R.id.edt11)
        val edt12: EditText = findViewById(R.id.edt12)
        val edt13: EditText = findViewById(R.id.edt13)
        val edt14: EditText = findViewById(R.id.edt14)
        val edt15: EditText = findViewById(R.id.edt15)

        val edt21: EditText = findViewById(R.id.edt21)
        val edt22: EditText = findViewById(R.id.edt22)
        val edt23: EditText = findViewById(R.id.edt23)
        val edt24: EditText = findViewById(R.id.edt24)
        val edt25: EditText = findViewById(R.id.edt25)

        val edt31: EditText = findViewById(R.id.edt31)
        val edt32: EditText = findViewById(R.id.edt32)
        val edt33: EditText = findViewById(R.id.edt33)
        val edt34: EditText = findViewById(R.id.edt34)
        val edt35: EditText = findViewById(R.id.edt35)

        val edt41: EditText = findViewById(R.id.edt41)
        val edt42: EditText = findViewById(R.id.edt42)
        val edt43: EditText = findViewById(R.id.edt43)
        val edt44: EditText = findViewById(R.id.edt44)
        val edt45: EditText = findViewById(R.id.edt45)

        val edt51: EditText = findViewById(R.id.edt51)
        val edt52: EditText = findViewById(R.id.edt52)
        val edt53: EditText = findViewById(R.id.edt53)
        val edt54: EditText = findViewById(R.id.edt54)
        val edt55: EditText = findViewById(R.id.edt55)

        val edt61: EditText = findViewById(R.id.edt61)
        val edt62: EditText = findViewById(R.id.edt62)
        val edt63: EditText = findViewById(R.id.edt63)
        val edt64: EditText = findViewById(R.id.edt64)
        val edt65: EditText = findViewById(R.id.edt65)

        edt11.filters = arrayOf(singleLetterFilter)
        edt12.filters = arrayOf(singleLetterFilter)
        edt13.filters = arrayOf(singleLetterFilter)
        edt14.filters = arrayOf(singleLetterFilter)
        edt15.filters = arrayOf(singleLetterFilter)

        edt21.filters = arrayOf(singleLetterFilter)
        edt22.filters = arrayOf(singleLetterFilter)
        edt23.filters = arrayOf(singleLetterFilter)
        edt24.filters = arrayOf(singleLetterFilter)
        edt25.filters = arrayOf(singleLetterFilter)

        edt31.filters = arrayOf(singleLetterFilter)
        edt32.filters = arrayOf(singleLetterFilter)
        edt33.filters = arrayOf(singleLetterFilter)
        edt34.filters = arrayOf(singleLetterFilter)
        edt35.filters = arrayOf(singleLetterFilter)

        edt41.filters = arrayOf(singleLetterFilter)
        edt42.filters = arrayOf(singleLetterFilter)
        edt43.filters = arrayOf(singleLetterFilter)
        edt44.filters = arrayOf(singleLetterFilter)
        edt45.filters = arrayOf(singleLetterFilter)

        edt51.filters = arrayOf(singleLetterFilter)
        edt52.filters = arrayOf(singleLetterFilter)
        edt53.filters = arrayOf(singleLetterFilter)
        edt54.filters = arrayOf(singleLetterFilter)
        edt55.filters = arrayOf(singleLetterFilter)

        edt61.filters = arrayOf(singleLetterFilter)
        edt62.filters = arrayOf(singleLetterFilter)
        edt63.filters = arrayOf(singleLetterFilter)
        edt64.filters = arrayOf(singleLetterFilter)
        edt65.filters = arrayOf(singleLetterFilter)

        val btnCheckWord: Button = findViewById(R.id.btnCheckWord)
        btnCheckWord.setOnClickListener {
            checkWord(
                edt11, edt12, edt13, edt14, edt15
            )
        }
    }

    // Función para validar la palabra
    private fun checkWord(
        edt11: EditText, edt12: EditText, edt13: EditText, edt14: EditText, edt15: EditText
    ) {
        // Obtener las letras de cada EditText
        val guess = listOf(
            edt11.text.toString(),
            edt12.text.toString(),
            edt13.text.toString(),
            edt14.text.toString(),
            edt15.text.toString()
        )

        // Validar que todas las casillas tengan letras
        if (guess.any { it.isEmpty() }) {
            Toast.makeText(this, "Por favor, completa todas las casillas.", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificar cada letra
        for (i in guess.indices) {
            val currentLetter = guess[i]
            if (currentLetter == WORD[i].toString()) {
                // Si la letra está en la posición correcta, cambia el fondo a verde
                setBackgroundColor(i, R.color.green)
            } else if (WORD.contains(currentLetter)) {
                // Si la letra está en la palabra pero no en la posición correcta, cambia el fondo a amarillo
                setBackgroundColor(i, R.color.yellow)
            } else {
                // Si la letra no está en la palabra, cambia el fondo a rojo
                setBackgroundColor(i, R.color.red)
            }
        }
    }

    // Función para cambiar el fondo de color de los EditText
    private fun setBackgroundColor(position: Int, colorId: Int) {
        val editTexts = listOf(
            findViewById<EditText>(R.id.edt11), findViewById<EditText>(R.id.edt12),
            findViewById<EditText>(R.id.edt13), findViewById<EditText>(R.id.edt14),
            findViewById<EditText>(R.id.edt15)
        )

        val editText = editTexts[position] // Seleccionar el EditText correspondiente
        editText.setBackgroundColor(ContextCompat.getColor(this, colorId)) // Establecer el color
    }
}
