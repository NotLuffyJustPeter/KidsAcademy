package hrn.prgd.kidsacademy

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.AttributeSet
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

class DragAndDrop (context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    private val imagesWithGroups = listOf(
        ImageItem(R.drawable.fruta1, "Frutas"),
        ImageItem(R.drawable.fruta2, "Frutas"),
        ImageItem(R.drawable.fruta3, "Frutas"),
        ImageItem(R.drawable.verdura1, "Verduras"),
        ImageItem(R.drawable.verdura2, "Verduras"),
        ImageItem(R.drawable.verdura3, "Verduras"),
        ImageItem(R.drawable.cereal1, "Cereales"),
        ImageItem(R.drawable.cereal2, "Cereales"),
        ImageItem(R.drawable.cereal3, "Cereales"),
        ImageItem(R.drawable.leguminosa1, "Leguminosa"),
        ImageItem(R.drawable.leguminosa2, "Leguminosa"),
        ImageItem(R.drawable.leguminosa3, "Leguminosa"),
        ImageItem(R.drawable.origen1, "Origen\nAnimal"),
        ImageItem(R.drawable.origen2, "Origen\nAnimal"),
        ImageItem(R.drawable.origen3, "Origen\nAnimal")
    )

    private val dropZoneColors = listOf(0x7068de34, 0x70207e1d, 0x70ffb60f, 0x70ff6c00, 0x70ff0000)
    private val dropZoneNames = listOf("Frutas", "Verduras", "Cereales", "Leguminosa", "Origen\nAnimal")
    private var correctas = 0

    init {
        setupDraggableImages()
        setupDropZones()
    }

    private fun setupDraggableImages() {
        // Mezclar las imágenes aleatoriamente
        val shuffledImages = imagesWithGroups.shuffled()

        shuffledImages.forEachIndexed { index, imageItem ->
            val imageView = ImageView(context).apply {
                setImageResource(imageItem.imageRes)
                layoutParams = LayoutParams(150, 150).apply {
                    leftMargin = 50 + (index % 5) * 200 // Espaciado horizontal
                    topMargin = 50 + (index / 5) * 220 // Espaciado vertical
                }
                setOnTouchListener { view, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        val dragData = ClipData.newPlainText("group", imageItem.group)
                        val shadow = DragShadowBuilder(this)
                        view.startDragAndDrop(dragData, shadow, view, 0)
                        true
                    } else false
                }
            }
            addView(imageView)
        }
    }

    private fun setupDropZones() {
        dropZoneNames.forEachIndexed { index, group ->
            val dropZone = RelativeLayout(context).apply {
                layoutParams = LayoutParams(320, 320).apply {
                    if (index < 3) {
                        leftMargin = 40 + (index * 350)  // Espaciado entre zonas
                        topMargin = 800  // Zonas en la parte superior
                    } else {  // 2 zonas en la parte inferior (abajo)
                        leftMargin = 220 + ((index - 3) * 350)
                        topMargin = 1150  // Zonas en la parte inferior
                    }
                    setBackgroundColor(dropZoneColors[index].toInt())
                }
                // Agregar un TextView dentro de la drop zone para mostrar el nombre
                val textView = TextView(context).apply {
                    text = group
                    textSize = 18f
                    setTextColor(Color.WHITE)
                    layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                        addRule(RelativeLayout.CENTER_IN_PARENT) // Centrar el texto en la zona
                    }
                }
                // Agregar el TextView a la zona
                addView(textView)
                // Listener para el drag and drop
                setOnDragListener { _, event ->
                    when (event.action) {
                        DragEvent.ACTION_DRAG_STARTED -> true
                        DragEvent.ACTION_DRAG_ENTERED -> {
                            setBackgroundColor(dropZoneColors[index].toInt() and 0x55FFFFFF) // Más claro al entrar
                            true
                        }
                        DragEvent.ACTION_DRAG_EXITED -> {
                            setBackgroundColor(dropZoneColors[index].toInt()) // Restaurar color
                            true
                        }
                        DragEvent.ACTION_DROP -> {
                            val dragData = event.clipData.getItemAt(0).text.toString()
                            val draggedView = event.localState as View
                            if (dragData == group) {
                                Toast.makeText(context, "¡Correcto! Soltaste en $group", Toast.LENGTH_SHORT).show()
                                draggedView.visibility = View.INVISIBLE
                                correctas++
                                if (correctas == 15) {
                                    resultados()
                                }
                            } else {
                                Toast.makeText(context, "¡Incorrecto! Esa imagen no pertenece a $group", Toast.LENGTH_SHORT).show()
                            }
                            true
                        }
                        DragEvent.ACTION_DRAG_ENDED -> {
                            setBackgroundColor(dropZoneColors[index].toInt()) // Restaurar color
                            true
                        }
                        else -> false
                    }
                }
            }
            addView(dropZone)
        }
    }
    private fun resultados() {
        // Aquí se lanza la nueva actividad con los resultados
        val intent = Intent(context, Resultado2Ciencias::class.java).apply {
            // Puedes pasar información extra si lo deseas, como el contador de aciertos.
            putExtra("correct_count", correctas)

        }
        context.startActivity(intent)
        (context as Activity).finish()
    }


}