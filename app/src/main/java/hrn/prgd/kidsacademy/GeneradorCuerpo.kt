package hrn.prgd.kidsacademy

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.Random

class GeneradorCuerpo : View {
    private val cuerpo = listOf(
        Pair("Cabeza", R.drawable.cuerpo1),
        Pair("Oreja", R.drawable.cuerpo2),
        Pair("Nariz", R.drawable.cuerpo3),
        Pair("Boca", R.drawable.cuerpo4),
        Pair("Mano", R.drawable.cuerpo5),
        Pair("Ojos", R.drawable.cuerpo6),
        Pair("Labios", R.drawable.cuerpo7),
        Pair("Brazo", R.drawable.cuerpo8),
        Pair("Piernas", R.drawable.cuerpo9),
        Pair("Pie", R.drawable.cuerpo10)
    ).shuffled()

    private var imagenActual: Pair<String, Int>? = null
    private var indiceActual = 0

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
        cambiarCuerpo(0)
    }

    fun cambiarCuerpo(indice: Int) {
        if (indice in cuerpo.indices) {
            indiceActual = indice
            imagenActual = cuerpo[indice]
            invalidate()
        } else {
            imagenActual = null // Si el índice no es válido, se limpia la imagen
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint().apply {
            isAntiAlias = true
        }

        imagenActual?.let { (_, imagenResId) -> // Ignoramos el nombre usando `_`
            // Configurar opciones para cargar el bitmap escalado
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true // Solo obtener dimensiones
            }
            BitmapFactory.decodeResource(resources, imagenResId, options)

            // Calcular el factor de escala adecuado
            val anchoOriginal = options.outWidth
            val altoOriginal = options.outHeight
            val anchoDeseadoPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 290f, resources.displayMetrics
            ).toInt()
            val altoDeseadoPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics
            ).toInt()

            val factorEscala = maxOf(
                anchoOriginal.toFloat() / anchoDeseadoPx,
                altoOriginal.toFloat() / altoDeseadoPx
            ).toInt()

            // Cargar el bitmap escalado
            val bitmap = BitmapFactory.Options().run {
                inSampleSize = factorEscala
                inJustDecodeBounds = false // Cargar el bitmap real
                BitmapFactory.decodeResource(resources, imagenResId, this)
            }

            // Crear un rectángulo para centrar la imagen en el recuadro
            val left = (width - anchoDeseadoPx) / 2f
            val top = (height - altoDeseadoPx) / 2f
            val rectFijo = RectF(left, top, left + anchoDeseadoPx, top + altoDeseadoPx)

            // Dibujar la imagen escalada
            canvas.drawBitmap(bitmap, null, rectFijo, paint)
        }
    }

    val result: String?
        // Calcula el resultado de la operación actual
        get() {
            return cuerpo.getOrNull(indiceActual)?.first // Retornar el nombre asociado al índice actual
        }

}