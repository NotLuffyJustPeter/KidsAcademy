package hrn.prgd.kidsacademy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Random

class GeneradorOperaciones : View {
    private var num1 = 0
    private var num2 = 0
    private var denominator1 = 0
    private var denominator2 = 0
    private var operator = 0.toChar()

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    // Inicializa los valores
    private fun init() {
        generateOperation(1)
    }

    fun generateOperation(tipo: Int) {
        val random = Random()

        when (tipo) {
            1 -> {
                num1 = random.nextInt(100) + 1
                num2 = random.nextInt(100) + 1
                operator = '+'
            }

            2 -> {
                num1 = random.nextInt(100) + 1
                num2 = random.nextInt(100) + 1
                operator = '-'
            }

            3 -> {
                num1 = random.nextInt(100) + 1
                num2 = random.nextInt(100) + 1
                operator = '*'
            }

            4 -> {
                num1 = random.nextInt(100) + 1
                num2 = random.nextInt(99) + 1
                operator = '/'
            }

            5 -> {
                num1 = random.nextInt(10) + 1
                denominator1 = random.nextInt(9) + 1
                num2 = random.nextInt(10) + 1
                denominator2 = random.nextInt(9) + 1
                operator = '+'
            }

            6 -> {
                num1 = random.nextInt(10) + 1
                denominator1 = random.nextInt(9) + 1
                num2 = random.nextInt(10) + 1
                denominator2 = random.nextInt(9) + 1
                operator = '-'
            }
        }
        invalidate() // Redibuja la vista para mostrar la nueva operación
    }

    val result: String
        // Calcula el resultado de la operación actual
        get() {
            when (operator) {
                '+' -> {
                    if (denominator1 > 0 && denominator2 > 0) { // Operación con fracciones
                        val comunDenominador = denominator1 * denominator2
                        val resultNumerador = (num1 * denominator2) + (num2 * denominator1)
                        return "$resultNumerador/$comunDenominador"
                    }
                    return (num1 + num2).toString()
                }

                '-' -> {
                    if (denominator1 > 0 && denominator2 > 0) { // Operación con fracciones
                        val comunDenominador = denominator1 * denominator2
                        val resultNumerador = (num1 * denominator2) - (num2 * denominator1)
                        return "$resultNumerador/$comunDenominador"
                    }
                    return (num1 - num2).toString()
                }

                '*' -> return (num1 * num2).toString()
                '/' -> return (num1 / num2.toDouble()).toString()
                else -> return "0.0"
            }
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.parseColor("#ff6200"))

        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 150f
        paint.textAlign = Paint.Align.CENTER
        paint.isAntiAlias = true
        paint.isFakeBoldText = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 4f

        paint.setShadowLayer(2f, 2f, 2f, Color.WHITE)
        val operacionTexto = if (denominator1 > 0 && denominator2 > 0) {
            "$num1/$denominator1 $operator $num2/$denominator2"
        } else {
            "$num1 $operator $num2"
        }
        val xPos = width / 2f
        val yPos = height / 2f - (paint.descent() + paint.ascent()) / 2
        canvas.drawText(operacionTexto, xPos, yPos, paint)
    }
}