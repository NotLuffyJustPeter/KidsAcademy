package hrn.prgd.kidsacademy

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class ControlAntonimos : LinearLayout {
    private var tituloPregunta: TextView
    private var resulText: TextView
    private lateinit var mensaje: TextView
    private var botones: LinearLayout
    private var pantallaResultado: LinearLayout
    private var pantallaExamen: LinearLayout
    private lateinit var opcion1: Button
    private lateinit var opcion2: Button
    private lateinit var opcion3: Button
    private lateinit var opcion4: Button // Nuevo botón para la cuarta opción
    private var btnReiniciar: Button
    private var btnSalir: Button

    private var caso: Int = 0
    private var puntaje: Int = 0
    private var contador: Int = 0
    private val totalPreguntas = 10

    private val preguntas = listOf(
        Pregunta("¿Cuál es el antónimo de caliente?", listOf("Frío", "Tibio", "Templado", "Cálido"), 0),
        Pregunta("¿Cuál es el antónimo de alto?", listOf("Bajo", "Elevado", "Grande", "Gigante"), 0),
        Pregunta("¿Cuál es el antónimo de fácil?", listOf("Difícil", "Simple", "Claro", "Sencillo"), 0),
        Pregunta("¿Cuál es el antónimo de abierto?", listOf("Cerrado", "Expuesto", "Amplio", "Extendido"), 0),
        Pregunta("¿Cuál es el antónimo de cerca?", listOf("Lejos", "Próximo", "Cercano", "Vecino"), 0),
        Pregunta("¿Cuál es el antónimo de luz?", listOf("Oscuridad", "Claridad", "Brillo", "Resplandor"), 0),
        Pregunta("¿Cuál es el antónimo de débil?", listOf("Fuerte", "Frágil", "Vulnerable", "Quebradizo"), 0),
        Pregunta("¿Cuál es el antónimo de grueso?", listOf("Delgado", "Ancho", "Robusto", "Pesado"), 0),
        Pregunta("¿Cuál es el antónimo de seco?", listOf("Húmedo", "Mojado", "Seco", "Empapado"), 0),
        Pregunta("¿Cuál es el antónimo de fresco?", listOf("Caluroso", "Frío", "Cálido", "Ventoso"), 0)
    )

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.plantilla_anton, this, true)

        tituloPregunta = findViewById(R.id.tituloPregunta)
        resulText = findViewById(R.id.resultadoTexto)
        mensaje = findViewById(R.id.mensaje)
        botones = findViewById(R.id.Botones)
        pantallaResultado = findViewById(R.id.pantallaResultado)
        pantallaExamen = findViewById(R.id.pantallaExamen)
        opcion1 = findViewById(R.id.Opcion1)
        opcion2 = findViewById(R.id.Opcion2)
        opcion3 = findViewById(R.id.Opcion3)
        opcion4 = findViewById(R.id.Opcion4)
        btnReiniciar = findViewById(R.id.btnReiniciar)
        btnSalir = findViewById(R.id.btnSalir)

        AsignarEventos()
        GenerarNueva()
        btnReiniciar.setOnClickListener { reiniciar() }
        btnSalir.setOnClickListener { salir() }
    }

    private fun AsignarEventos() {
        opcion1.setOnClickListener(evento)
        opcion2.setOnClickListener(evento)
        opcion3.setOnClickListener(evento)
        opcion4.setOnClickListener(evento)
    }

    private fun GenerarNueva() {
        if (contador < totalPreguntas) {
            val pregunta = preguntas[contador]
            tituloPregunta.text = pregunta.texto

            val opcionesIncorrectas = generarOpciones(pregunta.respuestas[pregunta.respuestaCorrecta])
            val opciones = mutableListOf(pregunta.respuestas[pregunta.respuestaCorrecta])
            opciones.addAll(opcionesIncorrectas)
            opciones.shuffle()

            opcion1.text = opciones[0]
            opcion2.text = opciones[1]
            opcion3.text = opciones[2]
            opcion4.text = opciones[3]
        } else {
            mostrarPantallaResultados()
        }
    }

    private val evento = OnClickListener { v ->
        opcion1.isEnabled = false
        opcion2.isEnabled = false
        opcion3.isEnabled = false
        opcion4.isEnabled = false

        val respuestaSeleccionada = when (v) {
            opcion1 -> opcion1.text
            opcion2 -> opcion2.text
            opcion3 -> opcion3.text
            opcion4 -> opcion4.text
            else -> ""
        }

        val preguntaActual = preguntas[contador]
        if (respuestaSeleccionada == preguntaActual.respuestas[preguntaActual.respuestaCorrecta]) {
            v.setBackgroundColor(Color.parseColor("#7ed554"))
            puntaje++
            mensaje.text = "Correcta"
            mensaje.setBackgroundColor(Color.parseColor("#7ed554"))
            mensaje.setTextColor(Color.parseColor("#1d5f2e"))
        } else {
            v.setBackgroundColor(Color.parseColor("#f56d51"))
            mensaje.text = "Incorrecta"
            mensaje.setBackgroundColor(Color.parseColor("#f56d51"))
            mensaje.setTextColor(Color.parseColor("#d00505"))
        }

        contador++
        if (contador >= totalPreguntas) {
            postDelayed({
                mostrarPantallaResultados()
            }, 1000)
        } else {
            postDelayed({
                GenerarNueva()
                resetBotones()
                mensaje.visibility = INVISIBLE
            }, 1000)
        }
    }

    private fun mostrarPantallaResultados() {
        pantallaExamen.visibility = GONE
        resulText.text = "Resultados: $puntaje/$totalPreguntas correctas"
        pantallaResultado.visibility = VISIBLE

        val sharedPreferences = context.getSharedPreferences("Puntajes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("puntaje4", puntaje)
        editor.apply()
        val intent = Intent(context, Puntajes::class.java)
        context.startActivity(intent)
    }

    private fun reiniciar() {
        contador = 0
        puntaje = 0
        pantallaResultado.visibility = GONE
        pantallaExamen.visibility = VISIBLE
        GenerarNueva()
        resetBotones()
    }

    private fun salir() {
        val intent = Intent(context, Spanish::class.java)
        context.startActivity(intent)
    }

    private fun resetBotones() {
        opcion1.isEnabled = true
        opcion2.isEnabled = true
        opcion3.isEnabled = true
        opcion4.isEnabled = true
        opcion1.setBackgroundColor(Color.parseColor("#304FFE"))
        opcion2.setBackgroundColor(Color.parseColor("#304FFE"))
        opcion3.setBackgroundColor(Color.parseColor("#304FFE"))
        opcion4.setBackgroundColor(Color.parseColor("#304FFE"))
    }

    private fun generarOpciones(respuestaCorrecta: String): List<String> {
        val respuestasIncorrectas = preguntas[contador].respuestas.filterNot { it == respuestaCorrecta }
        return respuestasIncorrectas.shuffled().take(3)
    }

    data class Pregunta(
        val texto: String,
        val respuestas: List<String>,
        val respuestaCorrecta: Int
    )
}

