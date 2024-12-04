package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.Locale
import java.util.Random

class ControlCuerpo1 : LinearLayout {
    var resulText: TextView? = null
    var mensaje: TextView? = null
    var generadorCuerpo: GeneradorCuerpo? = null
    var botones: LinearLayout? = null
    var pantallaResultado: LinearLayout? = null
    var pantallaExamen: LinearLayout? = null
    var opcion1: Button? = null
    var opcion2: Button? = null
    var opcion3: Button? = null
    var opcion4: Button? = null
    private lateinit var btnReiniciar: Button
    private lateinit var btnSalir: Button
    var caso: Int = 0
    var correctas: Int = 0
    var contador: Int = 0
    val opciones = listOf("Cabeza", "Oreja", "Nariz", "Boca", "Mano", "Ojos", "Labios","Brazo","Piernas","Pie")

    constructor(context: Context?) : super(context) {
        inicializar()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        inicializar()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        inicializar()
    }

    fun inicializar() {
        val li = LayoutInflater.from(context)
        li.inflate(R.layout.plantilla2_ciencia, this, true)
        resulText = findViewById<TextView>(R.id.resultadoTexto)
        generadorCuerpo = findViewById(R.id.GeneradorCuerpo)
        botones = findViewById<LinearLayout>(R.id.Botones)
        pantallaExamen = findViewById<LinearLayout>(R.id.pantallaExamen)
        opcion1 = findViewById<Button>(R.id.Opcion1)
        opcion2 = findViewById<Button>(R.id.Opcion2)
        opcion3 = findViewById<Button>(R.id.Opcion3)
        opcion4 = findViewById<Button>(R.id.Opcion4)
        mensaje = findViewById<TextView>(R.id.mensaje)

        pantallaResultado = findViewById<LinearLayout>(R.id.pantallaResultado)
        btnReiniciar = findViewById<Button>(R.id.btnReiniciar)
        btnSalir = findViewById<Button>(R.id.btnSalir)

        btnReiniciar.setOnClickListener(OnClickListener { v: View? -> reiniciar() })
        btnSalir.setOnClickListener(OnClickListener { v: View? -> salir() })

        GenerarNueva()
        AsignarEventos()
    }

    private fun AsignarEventos() {
        opcion1!!.setOnClickListener(evento)
        opcion2!!.setOnClickListener(evento)
        opcion3!!.setOnClickListener(evento)
        opcion4!!.setOnClickListener(evento)
    }

    private fun GenerarNueva() {
        generadorCuerpo!!.cambiarCuerpo(contador)
        val resultCadena: String = generadorCuerpo!!.result.toString()
        caso = Random().nextInt(4)
        resetBotonoes()
        when (caso) {
            0 -> {
                val opcionesIncorrectas = generarOpciones(opciones, resultCadena)
                opcion1!!.text = resultCadena
                opcion2!!.text = opcionesIncorrectas[0]
                opcion3!!.text = opcionesIncorrectas[1]
                opcion4!!.text = opcionesIncorrectas[2]
            }

            1 -> {
                val opcionesIncorrectas = generarOpciones(opciones, resultCadena)
                opcion2!!.text = resultCadena
                opcion1!!.text = opcionesIncorrectas[0]
                opcion3!!.text = opcionesIncorrectas[1]
                opcion4!!.text = opcionesIncorrectas[2]
            }

            2 -> {
                val opcionesIncorrectas = generarOpciones(opciones, resultCadena)
                opcion3!!.text = resultCadena
                opcion2!!.text = opcionesIncorrectas[0]
                opcion1!!.text = opcionesIncorrectas[1]
                opcion4!!.text = opcionesIncorrectas[2]
            }

            3 -> {
                val opcionesIncorrectas = generarOpciones(opciones, resultCadena)
                opcion4!!.text = resultCadena
                opcion2!!.text = opcionesIncorrectas[0]
                opcion3!!.text = opcionesIncorrectas[1]
                opcion1!!.text = opcionesIncorrectas[2]
            }
        }
    }

    private fun resetBotonoes() {
        opcion1!!.isEnabled = true
        opcion2!!.isEnabled = true
        opcion3!!.isEnabled = true
        opcion4!!.isEnabled = true
        opcion1!!.setBackgroundColor(Color.parseColor("#C5E1A5"))
        opcion2!!.setBackgroundColor(Color.parseColor("#C5E1A5"))
        opcion3!!.setBackgroundColor(Color.parseColor("#C5E1A5"))
        opcion4!!.setBackgroundColor(Color.parseColor("#C5E1A5"))
    }

    private val evento = OnClickListener { v ->
        opcion1!!.isEnabled = false
        opcion2!!.isEnabled = false
        opcion3!!.isEnabled = false
        opcion4!!.isEnabled = false

        if (v === opcion1 && caso == 0 || v === opcion2 && caso == 1 || v === opcion3 && caso == 2 || v === opcion4 && caso == 3) {
            v.setBackgroundColor(Color.parseColor("#7ed554"))
            correctas++
            mensaje!!.visibility = VISIBLE
            mensaje!!.text = "Correcta"
            mensaje!!.setBackgroundColor(Color.parseColor("#7ed554"))
            mensaje!!.setTextColor(Color.parseColor("#1d5f2e"))
        } else {
            v.setBackgroundColor(Color.parseColor("#f56d51"))
            mensaje!!.visibility = VISIBLE
            mensaje!!.text = "Incorrecta"
            mensaje!!.setBackgroundColor(Color.parseColor("#f56d51"))
            mensaje!!.setTextColor(Color.parseColor("#d00505"))
        }

        contador++
        if (contador >= 10) {
            postDelayed({
                mostrarPantallaResultados()
                mensaje!!.visibility = INVISIBLE
            }, 1000)
        } else {
            postDelayed({
                GenerarNueva()
                mensaje!!.visibility = INVISIBLE
            }, 1000)
        }
    }

    private fun mostrarPantallaResultados() {
        pantallaExamen!!.visibility = GONE
        resulText!!.text =
            String.format(Locale.getDefault(), "Resultados:\n %d/10 correctas", correctas)
        pantallaResultado!!.visibility = VISIBLE

        val sharedPreferences = context.getSharedPreferences("Puntajes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("ciepuntaje4", correctas)
        editor.apply()
        val intent = Intent(context, Puntajes::class.java)
        context.startActivity(intent)
    }

    private fun reiniciar() {
        contador = 0
        correctas = 0
        pantallaResultado!!.visibility = GONE
        pantallaExamen!!.visibility = VISIBLE
        GenerarNueva()
        resetBotonoes()
    }

    private fun salir() {
        val intent = Intent(context, Ciencias::class.java)
        context.startActivity(intent)
        (context as Activity).finish()
    }
    fun generarOpciones(opciones: List<String>, original: String): List<String> {
        val opcionesFiltradas = opciones.filter { it != original }
        return opcionesFiltradas.shuffled().take(3)
    }
}