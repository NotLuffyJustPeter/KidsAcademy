import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hrn.prgd.kidsacademy.R


class ControlConecta : AppCompatActivity() {

    private lateinit var buttons: List<Button>
    private val sequence = mutableListOf<Int>() // Secuencia generada por el juego
    private var userSequence = mutableListOf<Int>() // Respuesta del usuario
    private var level = 0 // Nivel actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad2_spanish)

        // Inicializa botones
        buttons = listOf(
            findViewById(R.id.btn_red),
            findViewById(R.id.btn_green),
            findViewById(R.id.btn_blue),
            findViewById(R.id.btn_yellow),
            findViewById(R.id.btn_purple),
            findViewById(R.id.btn_orange)
        )

        // Asignar eventos de clic a los botones
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { onButtonClicked(index) }
        }

        startGame()
    }

    private fun startGame() {
        sequence.clear()
        userSequence.clear()
        level = 1
        generateSequence()
    }

    private fun generateSequence() {
        // Agrega un número aleatorio a la secuencia
        sequence.add((0 until buttons.size).random())
        playSequence()
    }

    private fun playSequence() {
        userSequence.clear()
        // Muestra la secuencia al usuario con un retardo
        var delay: Long = 500
        sequence.forEach { index ->
            Handler(Looper.getMainLooper()).postDelayed({
                buttons[index].alpha = 0.5f
                Handler(Looper.getMainLooper()).postDelayed({
                    buttons[index].alpha = 1.0f
                }, 300)
            }, delay)
            delay += 800
        }
    }

    private fun onButtonClicked(index: Int) {
        userSequence.add(index)
        checkSequence()
    }

    private fun checkSequence() {
        // Compara la secuencia del usuario con la generada
        for (i in userSequence.indices) {
            if (userSequence[i] != sequence[i]) {
                // Si el usuario falla, reinicia el juego
                Toast.makeText(this, "¡Fallaste! Intenta de nuevo", Toast.LENGTH_SHORT).show()
                startGame()
                return
            }
        }

        // Si el usuario completa la secuencia, genera la siguiente
        if (userSequence.size == sequence.size) {
            Toast.makeText(this, "¡Bien hecho! Nivel $level completado", Toast.LENGTH_SHORT).show()
            level++
            generateSequence()
        }
    }
}
