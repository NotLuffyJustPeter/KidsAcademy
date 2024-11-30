package hrn.prgd.kidsacademy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Memorama : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var shuffledImages: List<Int>
    private var flippedCards = mutableListOf<ImageButton>()  // Listado de cartas volteadas
    private var flippedImages = mutableListOf<Int>()  // Imágenes de las cartas volteadas
    private var matchedPairs = 0  // Contador de pares emparejados
    private var attempts = 0  // Contador de intentos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad5_spanish)

        // Inicializa los ImageButtons
        buttons = listOf(
            findViewById(R.id.imageButton1),
            findViewById(R.id.imageButton2),
            findViewById(R.id.imageButton3),
            findViewById(R.id.imageButton4),
            findViewById(R.id.imageButton5),
            findViewById(R.id.imageButton6),
            findViewById(R.id.imageButton7),
            findViewById(R.id.imageButton8),
            findViewById(R.id.imageButton9),
            findViewById(R.id.imageButton10),
            findViewById(R.id.imageButton11),
            findViewById(R.id.imageButton12),
            findViewById(R.id.imageButton13),
            findViewById(R.id.imageButton14),
            findViewById(R.id.imageButton15),
            findViewById(R.id.imageButton16)
        )

        // Lista de imágenes (deben estar duplicadas para crear pares)
        val imageResources = listOf(
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher
        )

        // Mezcla las imágenes
        shuffledImages = imageResources.shuffled()

        // Asigna las imágenes a los ImageButton
        for (i in buttons.indices) {
            buttons[i].setImageResource(R.drawable.fondomemo) // Fondo inicial (reverso)
            buttons[i].tag = shuffledImages[i]  // Asignamos la imagen a la carta mediante el 'tag'
            buttons[i].setOnClickListener { onCardClicked(buttons[i], i) }
        }
    }

    // Función que maneja el clic en una carta
    private fun onCardClicked(button: ImageButton, index: Int) {
        // Si ya hay dos cartas volteadas, no hacer nada más
        if (flippedCards.size == 2) return

        // Voltea la carta
        val imageResId = button.tag as Int
        button.setImageResource(imageResId)
        flippedCards.add(button)
        flippedImages.add(imageResId)

        // Si se voltearon dos cartas
        if (flippedCards.size == 2) {
            attempts++  // Incrementar el contador de intentos
            // Verifica si las imágenes son iguales
            if (flippedImages[0] == flippedImages[1]) {
                // Las cartas coinciden
                matchedPairs++
                // Limpiar la lista de cartas volteadas
                flippedCards.clear()
                flippedImages.clear()

                // Verificar si se completaron todos los pares
                if (matchedPairs == buttons.size / 2) {
                    showGameOverDialog()
                }
            } else {
                // Las cartas no coinciden, voltear las cartas nuevamente después de un breve retraso
                Handler(Looper.getMainLooper()).postDelayed({
                    flippedCards[0].setImageResource(R.drawable.fondomemo)
                    flippedCards[1].setImageResource(R.drawable.fondomemo)
                    flippedCards.clear()
                    flippedImages.clear()
                }, 1000) // Espera 1 segundo antes de voltear las cartas
            }
        }
    }

    // Función para mostrar un mensaje de fin de juego
    private fun showGameOverDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Juego terminado!")
        builder.setMessage("Has emparejado todas las cartas en $attempts intentos.")
        builder.setPositiveButton("Reiniciar") { _, _ ->
            restartGame()
        }
        builder.setCancelable(false)
        builder.show()
    }

    // Función para reiniciar el juego
    private fun restartGame() {
        matchedPairs = 0
        attempts = 0
        flippedCards.clear()
        flippedImages.clear()

        // Mezclar las imágenes y volver a asignarlas a los botones
        shuffledImages = listOf(
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher,
            R.drawable.teacher, R.drawable.teacher
        ).shuffled()

        // Asignar imágenes y reiniciar el fondo de las cartas
        for (i in buttons.indices) {
            buttons[i].setImageResource(R.drawable.fondomemo)
            buttons[i].tag = shuffledImages[i]
        }
    }
}

