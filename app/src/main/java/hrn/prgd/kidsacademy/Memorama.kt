package hrn.prgd.kidsacademy

import android.app.Activity
import android.view.View
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.MeasureSpec
import java.util.logging.Handler

class Memorama : View {
    private val bloques = Array<Bitmap?>(12) { null } //ya las comente
    private val descubierto = BooleanArray(12)
    private val seleccionados = mutableListOf<Int>()
    private var memoramaIndex = 0 // Índice para controlar los memoramas

    private val memoramas = listOf(
        listOf(
            R.drawable.clima1, R.drawable.clima2, R.drawable.clima3,
            R.drawable.clima4, R.drawable.clima5, R.drawable.clima6
        ),
        listOf(
            R.drawable.desastre1, R.drawable.desastre2, R.drawable.desastre3,
            R.drawable.desastre4, R.drawable.desastre5, R.drawable.desastre6
        ),
        listOf(
            R.drawable.ciclo1, R.drawable.ciclo2, R.drawable.ciclo3,
            R.drawable.ciclo4, R.drawable.ciclo5, R.drawable.ciclo6
        )
    )

    private var mostrandoFelicidades = false
    private val handler = android.os.Handler(Looper.getMainLooper())

    constructor(context: Context?) : super (context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) //ya

    init {
        cargarMemorama()
    }

    private fun cargarMemorama() {
        val anchoBloque = context.resources.displayMetrics.widthPixels / 4
        val altoBloque = context.resources.displayMetrics.heightPixels / 3

        val imagenes = memoramas[memoramaIndex].map { id ->
            cargarBitmap(id, anchoBloque, altoBloque)
        }.filterNotNull()

        if (imagenes.size < 6) {
            throw IllegalStateException("Error al cargar las imágenes.")
        }

        val paresColores = (imagenes + imagenes).shuffled()
        for (i in bloques.indices) {
            bloques[i] = paresColores[i]
        }

        descubierto.fill(false)
        seleccionados.clear()
        invalidate()
    }

    private fun cargarBitmap(resourceId: Int, ancho: Int, alto: Int): Bitmap? {
        val opciones = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(resources, resourceId, opciones)

        opciones.inSampleSize = calcularInSampleSize(opciones, ancho, alto)
        opciones.inJustDecodeBounds = false

        return BitmapFactory.decodeResource(resources, resourceId, opciones)
    }

    private fun calcularInSampleSize(opciones: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = opciones.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) { //ya la comente
        val ancho = calcularAncho(widthMeasureSpec)
        val alto = calcularAlto(widthMeasureSpec)

        setMeasuredDimension(ancho, alto)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!mostrandoFelicidades && event.action == MotionEvent.ACTION_DOWN) {
            val bloqueSelected = obtenerBloque(event.x, event.y)
            if (bloqueSelected != -1 && !descubierto[bloqueSelected] && seleccionados.size < 2) {
                if (!seleccionados.contains(bloqueSelected)) {
                    seleccionados.add(bloqueSelected)
                }
                invalidate()

                if (seleccionados.size == 2) {
                    handler.postDelayed({ verificarPareja() }, 1000)
                }
            }
        }
        return true
    }

    private fun obtenerBloque(x: Float, y: Float): Int {
        val anchoBloque = measuredWidth / 4
        val altoBloque = measuredHeight / 3
        val columna = (x / anchoBloque).toInt()
        val fila = (y / altoBloque).toInt()
        val indice = fila * 4 + columna
        return if (indice in bloques.indices) indice else -1
    }

    @Synchronized
    private fun verificarPareja() {
        if (seleccionados.size == 2) {
            val primero = seleccionados[0]
            val segundo = seleccionados[1]
            if (bloques[primero] == bloques[segundo]) {
                descubierto[primero] = true
                descubierto[segundo] = true
            }
            seleccionados.clear()

            if (descubierto.all { it }) {
                mostrarFelicidades()
            }
        }
        invalidate()
    }

    private fun mostrarFelicidades() {
        mostrandoFelicidades = true
        invalidate()


        handler.postDelayed({
             if (memoramaIndex >= memoramas.size - 1) {
                 val intent = Intent(context, Resultado3Ciencias::class.java)
                 intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                 context.startActivity(intent)

                 // Finalizar la actividad actual
                 if (context is Activity) {
                     (context as Activity).finish()
                 }

            } else {
                // Si no, continuar con el siguiente memorama
                memoramaIndex++
                mostrandoFelicidades = false
                cargarMemorama()
            }
        }, 2000)

    }

    private fun calcularAlto(heightMeasureSpec: Int): Int { //ya la comente
        var res= 100
        val modo = MeasureSpec.getMode(heightMeasureSpec)
        val limite = MeasureSpec.getSize(heightMeasureSpec)
        if (modo == MeasureSpec.AT_MOST || modo == MeasureSpec.EXACTLY) {
            res = limite
        }
        return res
    }

    private fun calcularAncho(widthMeasureSpec: Int): Int { //ya la comente
        var res= 100
        val modo = MeasureSpec.getMode(widthMeasureSpec)
        val limite = MeasureSpec.getSize(widthMeasureSpec)
        if (modo == MeasureSpec.AT_MOST || modo == MeasureSpec.EXACTLY) {
            res = limite
        }
        return res
    }

    override fun onDraw(canvas: Canvas) {
        val anchoBloque = measuredWidth / 4
        val altoBloque = measuredHeight / 3
        val pRelleno = Paint()
        if (mostrandoFelicidades) {
            val bitmap = cargarBitmap(R.drawable.bien, measuredWidth, measuredHeight)
            bitmap?.let {
                canvas.drawBitmap(it, null, Rect(0, 0, measuredWidth, measuredHeight), null)
            }
            return
        }
        for (i in bloques.indices) {
            val columna = i % 4
            val fila = i / 4
            val xInicio = columna * anchoBloque
            val yInicio = fila * altoBloque
            if (descubierto[i] || seleccionados.contains(i)) {
                bloques[i]?.let { imagen ->
                    canvas.drawBitmap(
                        imagen,
                        null,
                        Rect(xInicio, yInicio, xInicio + anchoBloque, yInicio + altoBloque),
                        null
                    )
                }
            } else {
                pRelleno.color = Color.GRAY
                canvas.drawRect(
                    xInicio.toFloat(),
                    yInicio.toFloat(),
                    (xInicio + anchoBloque).toFloat(),
                    (yInicio + altoBloque).toFloat(),
                    pRelleno
                )
            }
            val pBorde = Paint()
            pBorde.style = Paint.Style.STROKE
            pBorde.color = Color.BLACK
            canvas.drawRect(
                xInicio.toFloat(),
                yInicio.toFloat(),
                (xInicio + anchoBloque).toFloat(),
                (yInicio + altoBloque).toFloat(),
                pBorde
            )
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        for (i in bloques.indices) {
            bloques[i]?.recycle()
            bloques[i] = null
        }
    }

}