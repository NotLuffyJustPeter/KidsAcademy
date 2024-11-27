package hrn.prgd.kidsacademy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

class ControlSilabas(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    private val palabras = listOf(
        "ma-te-ria", "ca-mi-no", "pa-la-bra", "za-pa-to", "la-u-rel", "sol-da-do", "pa-i-ses",
        "te-rre-no", "pe-lo-ta", "pro-fe-sor", "ca-mi-sa", "li-bre-ro", "ju-gue-te", "ca-ri-ño",
        "pla-ye-ra", "co-lo-res", "su-ce-sor", "di-se-ño", "co-ra-zon", "co-di-go", "a-vi-on", "re-bel-de", "a-li-vio"
    )
    private var palabraActual = ""
    private var correctas = 0
    private var contador = 0
    private var palabrasSeleccionadas = mutableListOf<String>()
    private var palabraFormada = ""
    private var intentosFallidos = 0

    // Referencias a las vistas infladas
    private val pantallaExamen: View
    private val pantallaResultado: View
    private val resultadoTexto: TextView
    private val mensaje: TextView
    private val btnReiniciar: Button
    private val btnSalir: Button
    private val botonesSilabas: LinearLayout
    private val palabraFormadaTextView: TextView

    init {
        // Inflar la vista desde el XML
        val inflater = LayoutInflater.from(context)
        val root = inflater.inflate(R.layout.plantilla_spanish, this, true)

        // Inicializar vistas infladas
        pantallaExamen = root.findViewById(R.id.pantallaExamen)
        pantallaResultado = root.findViewById(R.id.pantallaResultado)
        resultadoTexto = root.findViewById(R.id.resultadoTexto)
        mensaje = root.findViewById(R.id.mensaje)
        btnReiniciar = root.findViewById(R.id.btnReiniciar)
        btnSalir = root.findViewById(R.id.btnSalir)
        botonesSilabas = root.findViewById(R.id.botonesSilabas)
        palabraFormadaTextView = root.findViewById(R.id.palabraFormadaTextView)

        // Configurar botones
        btnReiniciar.setOnClickListener { reiniciarJuego() }
        btnSalir.setOnClickListener { salir() }

        // Generar palabras aleatorias
        generarNueva()
    }

    private fun generarNueva() {
        if (contador < 10) {
            // Reiniciar valores
            palabraFormada = ""
            intentosFallidos = 0
            palabraFormadaTextView.text = ""
            mensaje.visibility = View.INVISIBLE

            // Seleccionar palabras aleatorias
            if (palabrasSeleccionadas.isEmpty()) {
                palabrasSeleccionadas.addAll(palabras.shuffled().take(10))
            }

            palabraActual = palabrasSeleccionadas[contador]

            // Mostrar botones con sílabas de la palabra actual
            mostrarSilabas()
        } else {
            mostrarPantallaResultados()
        }
    }

    private fun mostrarSilabas() {
        botonesSilabas.removeAllViews()

        // Separar la palabra en sílabas
        val silabas = palabraActual.split("-")
        val randomSilabas = silabas.shuffled().toMutableList()

        for (silaba in randomSilabas) {
            val btnSilaba = Button(context).apply {
                text = silaba
                setBackgroundColor(Color.parseColor("#C5E1A5"))
                setTextColor(Color.BLACK)
                setOnClickListener {
                    manejarClicSilaba(silaba)
                }
            }
            botonesSilabas.addView(btnSilaba)
        }
    }

    private fun manejarClicSilaba(silaba: String) {
        palabraFormada += silaba
        palabraFormadaTextView.text = palabraFormada

        if (palabraActual.replace("-", "").startsWith(palabraFormada)) {
            // La palabra formada hasta ahora es correcta
            if (palabraFormada == palabraActual.replace("-", "")) {
                mensaje.text = "¡Correcto!"
                mensaje.setBackgroundColor(context.getColor(R.color.correcto))
                mensaje.visibility = View.VISIBLE
                correctas++
                contador++
                postDelayed({ generarNueva() }, 1000)
            }
        } else {
            // La palabra formada es incorrecta
            intentosFallidos++
            mensaje.text = "Intento incorrecto"
            mensaje.setBackgroundColor(context.getColor(R.color.incorrecto))
            mensaje.visibility = View.VISIBLE

            if (intentosFallidos >= 3) {
                mensaje.text = "Incorrecto, la palabra era: ${palabraActual.replace("-", "")}"
                contador++
                postDelayed({ generarNueva() }, 1000)
            }
        }
    }

    private fun mostrarPantallaResultados() {
        pantallaExamen.visibility = View.GONE
        pantallaResultado.visibility = View.VISIBLE
        resultadoTexto.text = String.format(
            Locale.getDefault(),
            "Resultados:\n %d/%d correctas",
            correctas,
            10 // Mostrar solo 10 intentos
        )
    }

    private fun reiniciarJuego() {
        correctas = 0
        contador = 0
        palabrasSeleccionadas.clear()
        pantallaResultado.visibility = View.GONE
        pantallaExamen.visibility = View.VISIBLE
        generarNueva()
    }

    private fun salir() {
        val intent = Intent(context, Spanish::class.java)
        context.startActivity(intent)
    }
}
