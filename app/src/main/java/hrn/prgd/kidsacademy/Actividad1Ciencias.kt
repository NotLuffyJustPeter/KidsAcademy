package hrn.prgd.kidsacademy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.min

class Actividad1Ciencias : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gravity: Sensor? = null
    private lateinit var customView: GameView

    private var xForce = 0f
    private var yForce = 0f
    private var gx = 0f
    private var gy = 0f

    private var score = 0
    object GameData {
        var score: Int = 0
    }
    private var gameTiempo = 40
    private var restanteTiempo = gameTiempo
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.actividad1_ciencia)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        findViewById<Button>(R.id.starButton).setOnClickListener {
            startGame()
        }

    }

    private fun startGame() {
        customView = GameView(this)
        setContentView(customView)

        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        gravity?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }

        startGameTimer()
    }

    private fun startGameTimer() {
        handler.removeCallbacksAndMessages(null)

        restanteTiempo = gameTiempo
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (restanteTiempo > 0) {
                    restanteTiempo--
                    customView.updateTime(restanteTiempo)
                    handler.postDelayed(this, 1000)
                } else {
                    // Fin del juego
                    customView.endGame(score)
                }
            }
        }, 1000)
    }
    override fun onResume() {
        super.onResume()
        if(::customView.isInitialized) {
            accelerometer?.also {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            }
            gravity?.also {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            }
        }
    }
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        handler.removeCallbacksAndMessages(null)
    }
    override fun onSensorChanged(event: SensorEvent) {
        if (::customView.isInitialized) {
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    xForce = event.values[0] * -1
                    yForce = event.values[1]
                    customView.updatePosition(xForce, yForce)
                }
                Sensor.TYPE_GRAVITY -> {
                    gx = event.values[0]
                    gy = event.values[1]
                }
            }
            customView.updateTextViews(xForce, yForce, gx, gy)
        }
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) { }

    inner class GameView(context: Context) : View(context) {
        private val paintObstaculo = Paint().apply {
            color = Color.rgb(122,40,1)
            style = Paint.Style.FILL
        }
        private val paintPorteria = Paint().apply {
            color = Color.GREEN
        }
        private val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = 60f
        }
        private val muestraScore = Paint().apply {
            color = Color.argb(150, 0, 0, 0) // Fondo semi-transparente negro
            style = Paint.Style.FILL
        }

        private var circleX = 0f
        private var circleY = 0f
        private var circleRadius = 50f
        //Variables para las porterias y obstaculos
        private var porteriaTop = RectF()
        private var activeArriba = true
        private val obstacles = mutableListOf<RectF>()
        private var animal1: Bitmap? = null
        private var animal2: Bitmap? = null
        private var animal3: Bitmap? = null
        private var fondo1: Bitmap? = null
        private var fondo2: Bitmap? = null
        private var fondo3: Bitmap? = null
        private var completo: Bitmap? = null
        private var isGoal = false

        private var animal = 0
        private var cambio = false



        init {
            setupField()
            completo = BitmapFactory.decodeResource(resources, R.drawable.bien).let {
                Bitmap.createScaledBitmap(it, 500, 500, true)
            }
            animal1 = BitmapFactory.decodeResource(resources, R.drawable.leon).let {
                Bitmap.createScaledBitmap(it, (circleRadius * 2).toInt(), (circleRadius * 2).toInt(), true)
            }
            animal2 = BitmapFactory.decodeResource(resources, R.drawable.chango).let {
                Bitmap.createScaledBitmap(it, (circleRadius * 2).toInt(), (circleRadius * 2).toInt(), true)
            }
            animal3 = BitmapFactory.decodeResource(resources, R.drawable.armadillo).let {
                Bitmap.createScaledBitmap(it, (circleRadius * 2).toInt(), (circleRadius * 2).toInt(), true)
            }
        }

        private fun setupField() {
            post {
                val originalBitmap1 = BitmapFactory.decodeResource(resources, R.drawable.sabana)
                fondo1 = Bitmap.createScaledBitmap(originalBitmap1, width, height, true)

                val originalBitmap2 = BitmapFactory.decodeResource(resources, R.drawable.jungla)
                fondo2 = Bitmap.createScaledBitmap(originalBitmap2, width, height, true)

                val originalBitmap3 = BitmapFactory.decodeResource(resources, R.drawable.desierto)
                fondo3 = Bitmap.createScaledBitmap(originalBitmap3, width, height, true)

                circleX = width / 2f
                circleY = height.toFloat()

                // Definición de porterías
                porteriaTop.set(415f, 0f, 665f, 50f)

                // Agrega obstáculos
                //horizontales
                obstacles.add(RectF(690f, 1175f, 920f, 1225f))
                obstacles.add(RectF(1025f, 1175f, 1080f, 1225f))
                obstacles.add(RectF(160f, 1175f, 390f, 1225f))
                obstacles.add(RectF(0f, 1175f, 55f, 1225f))
                obstacles.add(RectF(310f, 1520f, 410f, 1570f))
                obstacles.add(RectF(670f, 1520f, 770f, 1570f))
                obstacles.add(RectF(415f, 1345f, 665f, 1395f))
                obstacles.add(RectF(415f, 1735f, 665f, 1785f))
                obstacles.add(RectF(0f, 1845f, 105f, 1895f))
                obstacles.add(RectF(975f, 1845f, 1080f, 1895f))
                obstacles.add(RectF(415f, 2045f, 665f, 2095f))
                obstacles.add(RectF(415f, 2200f, 487f, 2250f))
                obstacles.add(RectF(593f, 2200f, 665f, 2250f))
                obstacles.add(RectF(0f, 2140f, 260f, 2190f))
                obstacles.add(RectF(820f, 2140f, 1080f, 2190f))


                //verticales
                obstacles.add(RectF(105f, 1380f, 155f, 1560f))
                obstacles.add(RectF(105f, 1665f, 155f, 1895f))
                obstacles.add(RectF(260f, 1330f, 310f, 1630f))
                obstacles.add(RectF(260f, 1735f, 310f, 2035f))
                obstacles.add(RectF(925f, 1380f, 975f, 1560f))
                obstacles.add(RectF(925f, 1665f, 975f, 1895f))
                obstacles.add(RectF(770f, 1330f, 820f, 1630f))
                obstacles.add(RectF(770f, 1735f, 820f, 2035f))
                obstacles.add(RectF(515f, 1520f, 565f, 1735f))
                obstacles.add(RectF(365f, 2200f, 415f, 2400f))
                obstacles.add(RectF(665f, 2200f, 715f, 2400f))
                //obstacles.add(RectF(105f, 2295f, 155f, 2400f))
                //obstacles.add(RectF(925f, 2295f, 975f, 2400f))

                //pequeños
                obstacles.add(RectF(360f, 1890f, 390f, 1940f))
                obstacles.add(RectF(457f, 1890f, 487f, 1940f))
                obstacles.add(RectF(593f, 1890f, 623f, 1940f))
                obstacles.add(RectF(690f, 1890f, 720f, 1940f))


                //EL OTRO LADO
                //horizontales
                obstacles.add(RectF(310f, 830f, 410f, 880f))
                obstacles.add(RectF(670f, 830f, 770f, 880f))
                obstacles.add(RectF(415f, 1005f, 665f, 1055f))
                obstacles.add(RectF(415f, 615f, 665f, 665f))
                obstacles.add(RectF(0f, 505f, 105f, 555f))
                obstacles.add(RectF(975f, 505f, 1080f, 555f))
                obstacles.add(RectF(415f, 305f, 665f, 355f))
                obstacles.add(RectF(415f, 150f, 487f, 200f))
                obstacles.add(RectF(593f, 150f, 665f, 200f))
                obstacles.add(RectF(0f, 210f, 260f, 260f))
                obstacles.add(RectF(820f, 210f, 1080f, 260f))

                //verticales
                obstacles.add(RectF(105f, 505f, 155f, 735f))
                obstacles.add(RectF(105f, 840f, 155f, 1020f))
                obstacles.add(RectF(260f, 770f, 310f, 1070f))
                obstacles.add(RectF(260f, 365f, 310f, 665f))
                obstacles.add(RectF(925f, 505f, 975f, 735f))
                obstacles.add(RectF(925f, 840f, 975f, 1020f))
                obstacles.add(RectF(770f, 770f, 820f, 1070f))
                obstacles.add(RectF(770f, 365f, 820f, 665f))
                obstacles.add(RectF(515f, 665f, 565f, 880f))
                obstacles.add(RectF(365f, 0f, 415f, 200f))
                obstacles.add(RectF(665f, 0f, 715f, 200f))
                //obstacles.add(RectF(105f, 0f, 155f, 105f))
                //obstacles.add(RectF(925f, 0f, 975f, 105f))

                //pequeños
                obstacles.add(RectF(360f, 460f, 390f, 510f))
                obstacles.add(RectF(457f, 460f, 487f, 510f))
                obstacles.add(RectF(593f, 460f, 623f, 510f))
                obstacles.add(RectF(690f, 460f, 720f, 510f))
            }
        }
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            when (animal) {
                0 -> {
                    fondo1?.let { canvas.drawBitmap(it, 0f, 0f, null) }
                    animal1?.let { canvas.drawBitmap(it, circleX - circleRadius, circleY - circleRadius, null) }
                }
                1 -> {
                    fondo2?.let { canvas.drawBitmap(it, 0f, 0f, null) }
                    animal2?.let { canvas.drawBitmap(it, circleX - circleRadius, circleY - circleRadius, null) }
                }
                else -> {
                    fondo3?.let { canvas.drawBitmap(it, 0f, 0f, null) }
                    animal3?.let { canvas.drawBitmap(it, circleX - circleRadius, circleY - circleRadius, null) }
                }
            }

            for (obstacle in obstacles) {
                canvas.drawRect(obstacle, paintObstaculo)
            }
            canvas.drawRect(porteriaTop, paintPorteria)


            canvas.drawRect(765f, 0f, 975f, 105f, muestraScore)
            canvas.save()
            canvas.drawText("$restanteTiempo s", 830f, 80f, textPaint)
            canvas.restore()

            if (isGoal) {
                completo?.let {
                    canvas.drawBitmap(it, (width - it.width) / 2f, (height - it.height) / 2f, null)
                }
            }
            if (cambio) {
                canvas.drawRect(105f, 840f, 975f, 1500f, muestraScore)
                canvas.save()
                canvas.drawText("Se acabo el tiempo", 310f, 1100f, textPaint)
                canvas.drawText("Ayuda al siguiente animalito", 155f, 1275f, textPaint)
                canvas.restore()
            }
        }

        fun updatePosition(xForce: Float, yForce: Float) {
            val speed = 5f
            circleX += xForce * speed
            circleY += yForce * speed
            circleX = circleX.coerceIn(circleRadius, width - circleRadius)
            circleY = circleY.coerceIn(circleRadius, height - circleRadius)

            if (RectF(circleX - circleRadius, circleY - circleRadius, circleX + circleRadius, circleY + circleRadius)
                    .intersect(porteriaTop)
            ) {
                mostrarGoal()
                score++
                resetBallPosition()
            }
            ajustarPosicionObstacleCollision()
            invalidate()
        }

        private fun mostrarGoal() {
            isGoal = true
            invalidate()

            // Oculta la imagen después de 2 segundos
            handler.postDelayed({
                isGoal = false
                cambioAnimal()
                invalidate()
            }, 2000)
        }

        private fun ajustarPosicionObstacleCollision() {
            val ballBounds = RectF(
                circleX - circleRadius,
                circleY - circleRadius,
                circleX + circleRadius,
                circleY + circleRadius
            )
            for (obstacle in obstacles) {
                if (RectF.intersects(ballBounds, obstacle)) {
                    val overlapX = min(circleX + circleRadius - obstacle.left, obstacle.right - (circleX - circleRadius))
                    val overlapY = min(circleY + circleRadius - obstacle.top, obstacle.bottom - (circleY - circleRadius))

                    if (overlapX < overlapY) {
                        if (circleX < obstacle.left) {
                            circleX -= overlapX
                        } else {
                            circleX += overlapX
                        }
                    } else {
                        if (circleY < obstacle.top) {
                            circleY -= overlapY
                        } else {
                            circleY += overlapY
                        }
                    }
                }
            }
        }
        private fun resetBallPosition() {
            circleX = width / 2f
            circleY = height.toFloat()
        }
        fun updateTextViews(xForce: Float, yForce: Float, gx: Float, gy: Float) {
            invalidate()
        }
        fun updateTime(time: Int) {
            restanteTiempo = time
            invalidate()
        }
        fun endGame(finalScore1: Int) {
            if(animal<2) {
                cambio = true
                invalidate()

                handler.postDelayed({
                    cambio = false
                    invalidate()
                    cambioAnimal()
                }, 3000)
            } else {
                invalidate()

                handler.postDelayed({
                    animal = 0
                    invalidate()
                    resetGame()
                    terminarJuego()
                }, 1000)
            }
        }
        private fun resetGame() {
            score = 0
            animal = 0
            restanteTiempo = gameTiempo
            activeArriba = true
            circleX = width / 2f
            circleY = height.toFloat()
            startGameTimer()
        }
        private fun cambioAnimal() {
            if(animal<2) {
                animal ++
                restanteTiempo = gameTiempo
                activeArriba = true
                circleX = width / 2f
                circleY = height.toFloat()
                startGameTimer()
            } else {
                invalidate()

                handler.postDelayed({
                    animal = 0
                    invalidate()
                    resetGame()
                    terminarJuego()
                }, 1000)
            }
        }
        private fun terminarJuego () {
            //AQUI VA A TENER QUE GUARDAR EN LA BD
            val intent = Intent(context as Actividad1Ciencias, Resultado1Ciencias::class.java)
            startActivity(intent)
            finish()
        }
    }
}