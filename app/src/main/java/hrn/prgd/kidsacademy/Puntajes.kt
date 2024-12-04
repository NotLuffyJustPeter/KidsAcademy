package hrn.prgd.kidsacademy

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Puntajes : AppCompatActivity() {

    private lateinit var SpapunTextView1: TextView
    private lateinit var SpapunTextView2: TextView
    private lateinit var SpapunTextView3: TextView
    private lateinit var SpapunTextView4: TextView
    private lateinit var SpapunTextView5: TextView
    private lateinit var SpapunTextView6: TextView

    private lateinit var MatpunTextView1: TextView
    private lateinit var MatpunTextView2: TextView
    private lateinit var MatpunTextView3: TextView
    private lateinit var MatpunTextView4: TextView
    private lateinit var MatpunTextView5: TextView
    private lateinit var MatpunTextView6: TextView

   // private lateinit var CiepunTextView1: TextView
    private lateinit var CiepunTextView2: TextView
    private lateinit var CiepunTextView3: TextView
    private lateinit var CiepunTextView4: TextView
    private lateinit var CiepunTextView5: TextView
    private lateinit var CiepunTextView6: TextView

    private lateinit var EtipunTextView1: TextView
    private lateinit var EtipunTextView2: TextView
    private lateinit var EtipunTextView3: TextView
    private lateinit var EtipunTextView4: TextView
    private lateinit var EtipunTextView5: TextView
    private lateinit var EtipunTextView6: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_puntajes)

        // Inicializar los TextViews
        SpapunTextView1 = findViewById(R.id.spapun1)
        SpapunTextView2 = findViewById(R.id.spapun2)
        SpapunTextView3 = findViewById(R.id.spapun3)
        SpapunTextView4 = findViewById(R.id.spapun4)
        SpapunTextView5 = findViewById(R.id.spapun5)
        SpapunTextView6 = findViewById(R.id.spapun6)

        MatpunTextView1 = findViewById(R.id.matpun1)
        MatpunTextView2 = findViewById(R.id.matpun2)
        MatpunTextView3 = findViewById(R.id.matpun3)
        MatpunTextView4 = findViewById(R.id.matpun4)
        MatpunTextView5 = findViewById(R.id.matpun5)
        MatpunTextView6 = findViewById(R.id.matpun6)

       // CiepunTextView1 = findViewById(R.id.ciepun1)
        CiepunTextView2 = findViewById(R.id.ciepun2)
        CiepunTextView3 = findViewById(R.id.ciepun3)
        CiepunTextView4 = findViewById(R.id.ciepun4)
        CiepunTextView5 = findViewById(R.id.ciepun5)
        CiepunTextView6 = findViewById(R.id.ciepun6)

        EtipunTextView1 = findViewById(R.id.etipun1)
        EtipunTextView2 = findViewById(R.id.etipun2)
        EtipunTextView3 = findViewById(R.id.etipun3)
        EtipunTextView4 = findViewById(R.id.etipun4)
        EtipunTextView5 = findViewById(R.id.etipun5)
        EtipunTextView6 = findViewById(R.id.etipun6)

        // Recuperar los puntajes de SharedPreferences
        val sharedPreferences = getSharedPreferences("Puntajes", MODE_PRIVATE)

        // Leer los puntajes guardados, si no existen, se asigna un valor por defecto de 0
        val puntaje1 = sharedPreferences.getInt("puntaje1", 0)
        val puntaje2 = sharedPreferences.getInt("puntaje2", 0)
        val puntaje3 = sharedPreferences.getInt("puntaje3", 0)
        val puntaje4 = sharedPreferences.getInt("puntaje4", 0)
        val puntaje5 = sharedPreferences.getInt("puntaje5", 0)
        val puntaje6 = sharedPreferences.getInt("puntaje6", 0)

        val matPuntaje1 = sharedPreferences.getInt("matpuntaje1", 0)
        val matPuntaje2 = sharedPreferences.getInt("matpuntaje2", 0)
        val matPuntaje3 = sharedPreferences.getInt("matpuntaje3", 0)
        val matPuntaje4 = sharedPreferences.getInt("matpuntaje4", 0)
        val matPuntaje5 = sharedPreferences.getInt("matpuntaje5", 0)
        val matPuntaje6 = sharedPreferences.getInt("matpuntaje6", 0)

       // val ciePuntaje1 = sharedPreferences.getInt("ciepuntaje1", 0)
        val ciePuntaje2 = sharedPreferences.getInt("ciepuntaje2", 0)
        val ciePuntaje3 = sharedPreferences.getInt("ciepuntaje3", 0)
        val ciePuntaje4 = sharedPreferences.getInt("ciepuntaje4", 0)
        val ciePuntaje5 = sharedPreferences.getInt("ciepuntaje5", 0)
        val ciePuntaje6 = sharedPreferences.getInt("ciepuntaje6", 0)

        val etiPuntaje1 = sharedPreferences.getInt("etipuntaje1", 0)
        val etiPuntaje2 = sharedPreferences.getInt("etipuntaje2", 0)
        val etiPuntaje3 = sharedPreferences.getInt("etipuntaje3", 0)
        val etiPuntaje4 = sharedPreferences.getInt("etipuntaje4", 0)
        val etiPuntaje5 = sharedPreferences.getInt("etipuntaje5", 0)
        val etiPuntaje6 = sharedPreferences.getInt("etipuntaje6", 0)

        // Actualizar los TextViews con los puntajes
        SpapunTextView1.text = puntaje1.toString()
        SpapunTextView2.text = puntaje2.toString()
        SpapunTextView3.text = puntaje3.toString()
        SpapunTextView4.text = puntaje4.toString()
        SpapunTextView5.text = puntaje5.toString()
        SpapunTextView6.text = puntaje6.toString()

        MatpunTextView1.text = matPuntaje1.toString()
        MatpunTextView2.text = matPuntaje2.toString()
        MatpunTextView3.text = matPuntaje3.toString()
        MatpunTextView4.text = matPuntaje4.toString()
        MatpunTextView5.text = matPuntaje5.toString()
        MatpunTextView6.text = matPuntaje6.toString()

        //CiepunTextView1.text = ciePuntaje1.toString()
        CiepunTextView2.text = ciePuntaje2.toString()
        CiepunTextView3.text = ciePuntaje3.toString()
        CiepunTextView4.text = ciePuntaje4.toString()
        CiepunTextView5.text = ciePuntaje5.toString()
        CiepunTextView6.text = ciePuntaje6.toString()

        EtipunTextView1.text = etiPuntaje1.toString()
        EtipunTextView2.text = etiPuntaje2.toString()
        EtipunTextView3.text = etiPuntaje3.toString()
        EtipunTextView4.text = etiPuntaje4.toString()
        EtipunTextView5.text = etiPuntaje5.toString()
        EtipunTextView6.text = etiPuntaje6.toString()
    }
}
