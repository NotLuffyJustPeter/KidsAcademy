package hrn.prgd.kidsacademy

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usuarios.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "usuarios"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_CORREO = "correo"
        private const val COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento"
        private const val COLUMN_CONTRASENA = "contrasena"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NOMBRE TEXT," +
                "$COLUMN_CORREO TEXT UNIQUE," +
                "$COLUMN_FECHA_NACIMIENTO TEXT," +
                "$COLUMN_CONTRASENA TEXT)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

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
}


