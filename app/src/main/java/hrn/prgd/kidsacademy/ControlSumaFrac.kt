package hrn.prgd.kidsacademy

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

class ControlSumaFrac : LinearLayout {
    var resulText: TextView? = null
    var mensaje: TextView? = null
    var generadorOperacion: GeneradorOperaciones? = null
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
        li.inflate(R.layout.plantilla_math, this, true)
        resulText = findViewById<TextView>(R.id.resultadoTexto)
        generadorOperacion = findViewById(R.id.GeneradorOperacion)
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
        generadorOperacion!!.generateOperation(5)
        val resultCadena: String = generadorOperacion!!.result
        val numeros = resultCadena.split("/".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val num1 = numeros[0].toInt()
        val num2 = numeros[1].toInt()
        caso = Random().nextInt(4)
        resetBotonoes()
        when (caso) {
            0 -> {
                opcion1!!.text = resultCadena
                opcion2!!.text = (num1 + (Random().nextInt(6) + 1) + (Random().nextInt(6) + 1)).toString() + "/" + (num2 + (Random().nextInt(6) + 1) + (Random().nextInt(6) + 1)).toString()
                opcion3!!.text = (num1 - (Random().nextInt(6) + 1) - (Random().nextInt(6) + 1)).toString() + "/" + (num2 - (Random().nextInt(6) + 1) - (Random().nextInt(6) + 1)).toString()
                opcion4!!.text = (num1 + (Random().nextInt(6) + 1)).toString() + "/" + (num2 - (Random().nextInt(6) + 1)).toString()
            }

            1 -> {
                opcion2!!.text = resultCadena
                opcion1!!.text = (num1 + (Random().nextInt(6) + 1) + (Random().nextInt(6) + 1)).toString() + "/" + (num2 + (Random().nextInt(7) + 1) + (Random().nextInt(6) + 1)).toString()
                opcion3!!.text = (num1 - (Random().nextInt(6) + 1) - (Random().nextInt(8) + 1)).toString() + "/" + (num2 - (Random().nextInt(6) + 1) - (Random().nextInt(9) + 1)).toString()
                opcion4!!.text = (num1 + (Random().nextInt(6) + 1)).toString() + "/" + (num2 - (Random().nextInt(6) + 1)).toString()
            }

            2 -> {
                opcion3!!.text = resultCadena
                opcion2!!.text = (num1 - (Random().nextInt(7) + 1)).toString() + "/" + (num2 + (Random().nextInt(7) + 1)).toString()
                opcion1!!.text = (num1 - (Random().nextInt(6) + 1) - (Random().nextInt(6) + 1)).toString() + "/" + (num2 - (Random().nextInt(6) + 1) - (Random().nextInt(9) + 1)).toString()
                opcion4!!.text = (num1 + (Random().nextInt(6) + 1) + (Random().nextInt(6) + 1)).toString() + "/" + (num2 + (Random().nextInt(7) + 1)).toString()
            }

            3 -> {
                opcion4!!.text = resultCadena
                opcion2!!.text = (num1 + (Random().nextInt(6) + 1) + (Random().nextInt(6) + 1)).toString() + "/" + (num2 + (Random().nextInt(7) + 1) + (Random().nextInt(6) + 1)).toString()
                opcion3!!.text = (num1 + (Random().nextInt(6) + 1)).toString() + "/" + (num2 - (Random().nextInt(6) + 1)).toString()
                opcion1!!.text = (num1 - (Random().nextInt(6) + 1)).toString() + "/" + (num2 - (Random().nextInt(6) + 1)).toString()
            }
        }
    }

    private fun resetBotonoes() {
        opcion1!!.isEnabled = true
        opcion2!!.isEnabled = true
        opcion3!!.isEnabled = true
        opcion4!!.isEnabled = true
        opcion1!!.setBackgroundColor(Color.parseColor("#f8ab5e"))
        opcion2!!.setBackgroundColor(Color.parseColor("#f8ab5e"))
        opcion3!!.setBackgroundColor(Color.parseColor("#f8ab5e"))
        opcion4!!.setBackgroundColor(Color.parseColor("#f8ab5e"))
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
        editor.putInt("matpuntaje5", correctas)
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
        val intent = Intent(context, Math::class.java)
        context.startActivity(intent)
    }
}