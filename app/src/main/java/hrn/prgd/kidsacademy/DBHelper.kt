package hrn.prgd.kidsacademy

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usuarios.db"
        private const val DATABASE_VERSION = 6 // Incrementar la versión

        private const val TABLE_NAME = "usuarios"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_CORREO = "correo"
        private const val COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento"
        private const val COLUMN_CONTRASENA = "contrasena"

        // Nueva tabla para las actividades
        private const val TABLE_ACTIVIDADES = "actividades"
        private const val COLUMN_ACTIVIDAD = "actividad"
        private const val COLUMN_DESBLOQUEADA = "desbloqueada"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Crear tabla usuarios
        val CREATE_TABLE_USUARIOS = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_CORREO TEXT UNIQUE,
                $COLUMN_FECHA_NACIMIENTO TEXT,
                $COLUMN_CONTRASENA TEXT
            )
        """
        db?.execSQL(CREATE_TABLE_USUARIOS)

        // Crear tabla actividades (progreso de actividades)
        val CREATE_TABLE_ACTIVIDADES = """
            CREATE TABLE $TABLE_ACTIVIDADES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ACTIVIDAD TEXT,
                $COLUMN_DESBLOQUEADA INTEGER
            )
        """
        db?.execSQL(CREATE_TABLE_ACTIVIDADES)

        // Insertar actividades iniciales
        val INSERT_ACTIVIDADES = """
            INSERT INTO $TABLE_ACTIVIDADES ($COLUMN_ACTIVIDAD, $COLUMN_DESBLOQUEADA) VALUES
            ('actividad1', 1),
            ('actividad2', 0),
            ('actividad3', 0),
            ('actividad4', 0),
            ('actividad5', 0),
            ('actividad6', 0)
        """
        db?.execSQL(INSERT_ACTIVIDADES)

        val CREATE_TABLE_ACTIVIDADES_MATH = """
        CREATE TABLE actividades_math (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_ACTIVIDAD TEXT,
            $COLUMN_DESBLOQUEADA INTEGER
        )
    """
        db?.execSQL(CREATE_TABLE_ACTIVIDADES_MATH)

        val INSERT_ACTIVIDADES_MATH = """
        INSERT INTO actividades_math ($COLUMN_ACTIVIDAD, $COLUMN_DESBLOQUEADA) VALUES
        ('actividad1math', 1),
        ('actividad2math', 0),
        ('actividad3math', 0),
        ('actividad4math', 0),
        ('actividad5math', 0),
        ('actividad6math', 0)
    """
        db?.execSQL(INSERT_ACTIVIDADES_MATH)

        val CREATE_TABLE_ACTIVIDADES_CIENCIAS = """
        CREATE TABLE actividades_ciencias (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_ACTIVIDAD TEXT,
            $COLUMN_DESBLOQUEADA INTEGER
        )
    """
        db?.execSQL(CREATE_TABLE_ACTIVIDADES_CIENCIAS)

        val INSERT_ACTIVIDADES_CIENCIAS = """
        INSERT INTO actividades_ciencias ($COLUMN_ACTIVIDAD, $COLUMN_DESBLOQUEADA) VALUES
        ('actividad1ciencias', 1),
        ('actividad2ciencias', 1),
        ('actividad3ciencias', 0),
        ('actividad4ciencias', 0),
        ('actividad5ciencias', 0),
        ('actividad6ciencias', 0)
    """
        db?.execSQL(INSERT_ACTIVIDADES_CIENCIAS)

        val CREATE_TABLE_ACTIVIDADES_ETICA = """
        CREATE TABLE actividades_etica (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_ACTIVIDAD TEXT,
            $COLUMN_DESBLOQUEADA INTEGER
        )
    """
        db?.execSQL(CREATE_TABLE_ACTIVIDADES_ETICA)

        val INSERT_ACTIVIDADES_ETICA = """
        INSERT INTO actividades_etica ($COLUMN_ACTIVIDAD, $COLUMN_DESBLOQUEADA) VALUES
        ('actividad1etica', 1),
        ('actividad2etica', 0),
        ('actividad3etica', 0),
        ('actividad4etica', 0),
        ('actividad5etica', 0),
        ('actividad6etica', 0)
    """
        db?.execSQL(INSERT_ACTIVIDADES_ETICA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 6) {
            // Eliminar tablas si ya existen
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_ACTIVIDADES")
            db?.execSQL("DROP TABLE IF EXISTS actividades_math")
            db?.execSQL("DROP TABLE IF EXISTS actividades_ciencias")
            db?.execSQL("DROP TABLE IF EXISTS actividades_etica")

            onCreate(db)
        }
    }


    // Método para insertar un nuevo usuario
    fun insertUsuario(nombre: String, correo: String, fechaNacimiento: String, contrasena: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NOMBRE, nombre)
        contentValues.put(COLUMN_CORREO, correo)
        contentValues.put(COLUMN_FECHA_NACIMIENTO, fechaNacimiento)
        contentValues.put(COLUMN_CONTRASENA, contrasena)

        val id = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return id
    }

    // Método para verificar si un usuario es válido
    fun verificarUsuario(nombre: String, contrasena: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID),
            "$COLUMN_NOMBRE = ? AND $COLUMN_CONTRASENA = ?",
            arrayOf(nombre, contrasena),
            null, null, null
        )

        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }

    // Método para verificar si una actividad está desbloqueada
    fun isActivityUnlocked(actividad: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_ACTIVIDADES,
            arrayOf(COLUMN_DESBLOQUEADA),
            "$COLUMN_ACTIVIDAD = ?",
            arrayOf(actividad),
            null, null, null
        )

        var unlocked = false

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(COLUMN_DESBLOQUEADA)

            if (columnIndex != -1) {
                unlocked = cursor.getInt(columnIndex) == 1
            } else {
                Log.e("DB", "La columna $COLUMN_DESBLOQUEADA no existe.")
            }
        }

        cursor.close()
        db.close()
        return unlocked
    }

    // Métodos específicos para la tabla de ciencias
    fun isCienciasActivityUnlocked(actividad: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            "actividades_ciencias",  // Corregir la tabla
            arrayOf(COLUMN_DESBLOQUEADA),
            "$COLUMN_ACTIVIDAD = ?",
            arrayOf(actividad),
            null, null, null
        )

        var unlocked = false
        if (cursor.moveToFirst()) {
            unlocked = cursor.getInt(cursor.getColumnIndex(COLUMN_DESBLOQUEADA)) == 1
        }
        cursor.close()
        db.close()
        return unlocked
    }


    fun desbloquearSiguienteCienciasActividad(actividad: String) {
        val siguienteActividad = when (actividad) {
            "actividad1ciencias" -> "actividad2ciencias"
            "actividad2ciencias" -> "actividad3ciencias"
            "actividad3ciencias" -> "actividad4ciencias"
            "actividad4ciencias" -> "actividad5ciencias"
            "actividad5ciencias" -> "actividad6ciencias"
            else -> null
        }

        if (siguienteActividad != null) {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(COLUMN_DESBLOQUEADA, 1)
            db.update("actividades_ciencias", contentValues, "$COLUMN_ACTIVIDAD = ?", arrayOf(siguienteActividad))
            db.close()
        }
    }

    // Métodos específicos para la tabla de etica
    fun isEticaActivityUnlocked(actividad: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            "actividades_etica",  // Corregir la tabla
            arrayOf(COLUMN_DESBLOQUEADA),
            "$COLUMN_ACTIVIDAD = ?",
            arrayOf(actividad),
            null, null, null
        )

        var unlocked = false
        if (cursor.moveToFirst()) {
            unlocked = cursor.getInt(cursor.getColumnIndex(COLUMN_DESBLOQUEADA)) == 1
        }
        cursor.close()
        db.close()
        return unlocked
    }


    fun desbloquearSiguienteEticaActividad(actividad: String) {
        val siguienteActividad = when (actividad) {
            "actividad1etica" -> "actividad2etica"
            "actividad2etica" -> "actividad3etica"
            "actividad3etica" -> "actividad4etica"
            "actividad4etica" -> "actividad5etica"
            "actividad5etica" -> "actividad6etica"
            else -> null
        }

        if (siguienteActividad != null) {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(COLUMN_DESBLOQUEADA, 1)
            db.update("actividades_etica", contentValues, "$COLUMN_ACTIVIDAD = ?", arrayOf(siguienteActividad))
            db.close()
        }
    }


    // Métodos específicos para la tabla de matemáticas
    fun isMathActivityUnlocked(actividad: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            "actividades_math",
            arrayOf(COLUMN_DESBLOQUEADA),
            "$COLUMN_ACTIVIDAD = ?",
            arrayOf(actividad),
            null, null, null
        )

        var unlocked = false
        if (cursor.moveToFirst()) {
            unlocked = cursor.getInt(cursor.getColumnIndex(COLUMN_DESBLOQUEADA)) == 1
        }
        cursor.close()
        db.close()
        return unlocked
    }


    fun desbloquearSiguienteMathActividad(actividad: String) {
        val siguienteActividad = when (actividad) {
            "actividad1math" -> "actividad2math"
            "actividad2math" -> "actividad3math"
            "actividad3math" -> "actividad4math"
            "actividad4math" -> "actividad5math"
            "actividad5math" -> "actividad6math"
            else -> null
        }

        if (siguienteActividad != null) {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(COLUMN_DESBLOQUEADA, 1)
            db.update("actividades_math", contentValues, "$COLUMN_ACTIVIDAD = ?", arrayOf(siguienteActividad))
            db.close()
        }
    }

    // Método para desbloquear la siguiente actividad
    fun desbloquearSiguienteActividad(actividad: String) {
        val siguienteActividad = when (actividad) {
            "actividad1" -> "actividad2"
            "actividad2" -> "actividad3"
            "actividad3" -> "actividad4"
            "actividad4" -> "actividad5"
            "actividad5" -> "actividad6"
            else -> null
        }

        if (siguienteActividad != null) {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(COLUMN_DESBLOQUEADA, 1)

            val rowsAffected = db.update(TABLE_ACTIVIDADES, contentValues, "$COLUMN_ACTIVIDAD = ?", arrayOf(siguienteActividad))
            db.close()

            if (rowsAffected > 0) {
                Log.d("DBHelper", "Actividad desbloqueada: $siguienteActividad")
            } else {
                Log.e("DBHelper", "Error al desbloquear la actividad: $siguienteActividad")
            }
        }
    }

}

