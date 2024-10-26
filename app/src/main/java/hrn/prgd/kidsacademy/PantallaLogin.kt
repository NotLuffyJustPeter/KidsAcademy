package hrn.prgd.kidsacademy

class PantallaLogin(private val nombre: String, private val edad: String, private val contrasena: String) {

    fun validarDatos(): Boolean {
        return nombre.isNotEmpty() && edad.isNotEmpty() && contrasena.isNotEmpty()
    }

    fun validarEdad(): Boolean {
        return edad.toIntOrNull()?.let { it > 5 } ?: false
    }

    fun validarContrasena(): Boolean {
        return contrasena.length >= 6
    }

    fun iniciarSesion(): Boolean {
        return validarDatos() && validarEdad() && validarContrasena()
    }
}