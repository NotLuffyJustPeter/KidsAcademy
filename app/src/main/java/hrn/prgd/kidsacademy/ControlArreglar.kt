package hrn.prgd.kidsacademy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class ControlArreglar : LinearLayout {
    private var tituloPregunta: TextView
    private var resulText: TextView
    private lateinit var mensaje: TextView
    private var botones: LinearLayout
    private var pantallaResultado: LinearLayout
    private var pantallaExamen: LinearLayout
    private lateinit var opcion1: Button
    private lateinit var opcion2: Button
    private lateinit var opcion3: Button
    private var btnReiniciar: Button
    private var btnSalir: Button

    private var caso: Int = 0  // Índice de la pregunta actual
    private var puntaje: Int = 0  // Contador de respuestas correctas
    private var contador: Int = 0 // Contador de preguntas respondidas
    private val totalPreguntas = 7  // Número de preguntas total

    private var playPauseButton: Button
    private var mediaPlayer: MediaPlayer

    // Lista de preguntas y respuestas
    private val preguntas = listOf(
        Pregunta("¿Por qué el científico estaba preocupado?", listOf("Por los problemas del mundo.", "Porque no sabía cómo cuidar a su hijo.", "Por los problemas de su laboratorio."), 0),
        Pregunta("¿Qué hizo el científico para distraer a su hijo?", listOf("Le pidió que jugara con sus juguetes.", "Le dio un mapa roto para que lo arreglara.", "Le dio un libro para que leyera."), 1),
        Pregunta("¿Cuánto tiempo pensaba el científico que le llevaría a su hijo arreglar el mapa?", listOf("Unas horas.", "Todo un día.", "Solo unos minutos."), 1),
        Pregunta("¿Cómo reaccionó el científico cuando su hijo dijo que había terminado el rompecabezas?", listOf("No le creyó al principio.", "Estaba enojado porque el mapa estaba mal hecho.", "Se alegró inmediatamente."), 0),
        Pregunta("¿Qué descubrió el niño al intentar arreglar el mapa del mundo?", listOf("Que no sabía cómo era el mundo.", "Que había la figura de un hombre en el reverso del mapa.", "Que era fácil colocar las piezas del mapa."), 1),
        Pregunta("¿Cómo logró el niño arreglar el mapa del mundo?", listOf("Adivinando dónde iba cada pedazo.", "Arreglando primero la figura del hombre en el reverso.", "Pidiendo ayuda a su padre."), 1),
        Pregunta("¿Cuál es la moraleja del cuento?", listOf("Si arreglas al hombre, también arreglas el mundo.", "Los rompecabezas son divertidos.", "Los niños siempre hacen las cosas más rápido que los adultos."), 0)
    )

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.plantilla_etica, this, true)

        // Obtener referencias de los elementos del layout
        tituloPregunta = findViewById(R.id.tituloPregunta)
        resulText = findViewById(R.id.resultadoTexto)
        mensaje = findViewById(R.id.mensaje)
        botones = findViewById(R.id.Botones)
        pantallaResultado = findViewById(R.id.pantallaResultado)
        pantallaExamen = findViewById(R.id.pantallaExamen)
        opcion1 = findViewById(R.id.Opcion1)
        opcion2 = findViewById(R.id.Opcion2)
        opcion3 = findViewById(R.id.Opcion3)
        btnReiniciar = findViewById(R.id.btnReiniciar)
        btnSalir = findViewById(R.id.btnSalir)
        playPauseButton = findViewById(R.id.playPauseButton)
        mediaPlayer = MediaPlayer.create(context, R.raw.arreglar_el_mundo)

        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                // Si está reproduciendo, pausar
                mediaPlayer.pause()
                playPauseButton.text = "Reproducir"  // Cambiar el texto a "Reproducir"
            } else {
                // Si no está reproduciendo, iniciar
                mediaPlayer.start()
                playPauseButton.text = "Pausar"  // Cambiar el texto a "Pausar"
            }
        }


        // Asignar los listeners de los botones
        AsignarEventos()

        // Generar la primera pregunta
        GenerarNueva()

        // Configurar acciones de reinicio y salida
        btnReiniciar.setOnClickListener { reiniciar() }
        btnSalir.setOnClickListener { salir() }


    }

    private fun AsignarEventos() {
        opcion1.setOnClickListener(evento)
        opcion2.setOnClickListener(evento)
        opcion3.setOnClickListener(evento)
    }


    private fun GenerarNueva() {
        if (contador < totalPreguntas) {
            val pregunta = preguntas[contador]

            // Actualizar el TextView con la pregunta
            tituloPregunta.text = pregunta.texto

            // Generar las opciones incorrectas (elige 2 respuestas aleatorias diferentes de la correcta)
            val opcionesIncorrectas = generarOpciones(pregunta.respuestas[pregunta.respuestaCorrecta])

            // Crear una lista de las opciones correctas e incorrectas
            val opciones = mutableListOf(pregunta.respuestas[pregunta.respuestaCorrecta])
            opciones.addAll(opcionesIncorrectas)
            opciones.shuffle()

            // Asignar las opciones a los botones de manera aleatoria
            opcion1.text = opciones[0]
            opcion2.text = opciones[1]
            opcion3.text = opciones[2]
        } else {
            // Si el contador excede el número total de preguntas, mostrar los resultados
            mostrarPantallaResultados()
        }
    }

    private val evento = OnClickListener { v ->
        // Deshabilitar botones después de la selección
        opcion1.isEnabled = false
        opcion2.isEnabled = false
        opcion3.isEnabled = false

        val respuestaSeleccionada = when (v) {
            opcion1 -> opcion1.text
            opcion2 -> opcion2.text
            opcion3 -> opcion3.text
            else -> ""
        }

        // Verificar si la respuesta es correcta
        val preguntaActual = preguntas[contador]
        if (respuestaSeleccionada == preguntaActual.respuestas[preguntaActual.respuestaCorrecta]) {
            v.setBackgroundColor(Color.parseColor("#7ed554")) // Correcto
            puntaje++
            mensaje.text = "Correcta"
            mensaje.setBackgroundColor(Color.parseColor("#7ed554"))
            mensaje.setTextColor(Color.parseColor("#1d5f2e"))
        } else {
            v.setBackgroundColor(Color.parseColor("#f56d51")) // Incorrecto
            mensaje.text = "Incorrecta"
            mensaje.setBackgroundColor(Color.parseColor("#f56d51"))
            mensaje.setTextColor(Color.parseColor("#d00505"))
        }

        contador++

        // Si se han respondido todas las preguntas
        if (contador >= totalPreguntas) {
            postDelayed({
                mostrarPantallaResultados()
            }, 1000)
        } else {
            // Si no, generar nueva pregunta
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
        val intent = Intent(context, Etica::class.java)
        context.startActivity(intent)
    }

    private fun resetBotones() {
        opcion1.isEnabled = true
        opcion2.isEnabled = true
        opcion3.isEnabled = true
        opcion1.setBackgroundColor(Color.parseColor("#C5E1A5"))
        opcion2.setBackgroundColor(Color.parseColor("#C5E1A5"))
        opcion3.setBackgroundColor(Color.parseColor("#C5E1A5"))
    }

    // Función para generar opciones incorrectas aleatorias
    private fun generarOpciones(respuestaCorrecta: String): List<String> {
        // Generar una lista de respuestas incorrectas
        val respuestasIncorrectas = preguntas[contador].respuestas.filterNot { it == respuestaCorrecta }
        // Seleccionar dos respuestas incorrectas aleatorias
        return respuestasIncorrectas.shuffled().take(2)
    }


    data class Pregunta(
        val texto: String,  // El texto de la pregunta
        val respuestas: List<String>,  // Las opciones de respuesta
        val respuestaCorrecta: Int  // El índice de la respuesta correcta
    )

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mediaPlayer.release()
    }

}
