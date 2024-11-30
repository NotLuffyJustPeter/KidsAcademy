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

class GeneradorPlaneta : View {
    private val planeta = listOf(
        Pair("Mercurio", R.drawable.planeta1),
        Pair("Venus", R.drawable.planeta2),
        Pair("Tierra", R.drawable.planeta3),
        Pair("Marte", R.drawable.planeta4),
        Pair("Jupiter", R.drawable.planeta5),
        Pair("Saturno", R.drawable.planeta6),
        Pair("Urano", R.drawable.planeta7),
        Pair("Neptuno", R.drawable.planeta8),
        Pair("Sol", R.drawable.planeta9),
        Pair("Luna", R.drawable.planeta10)
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
        cambiarPlaneta(0)
    }

    fun cambiarPlaneta(indice: Int) {
        if (indice in planeta.indices) {
            indiceActual = indice
            imagenActual = planeta[indice]
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
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 18f, resources.displayMetrics
            )
            color = Color.parseColor("#33691E")
            textAlign = Paint.Align.CENTER
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
            val top = (height - altoDeseadoPx) / 3f
            val rectFijo = RectF(left, top, left + anchoDeseadoPx, top + altoDeseadoPx)

            // Dibujar la imagen escalada
            canvas.drawBitmap(bitmap, null, rectFijo, paint)
        }
    }

    val result: String?
        // Calcula el resultado de la operación actual
        get() {
            return planeta.getOrNull(indiceActual)?.first // Retornar el nombre asociado al índice actual
        }

}