package Modelo
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
fun CadenaConexion():Connection?
{
    try {
        val ip ="jdbc:oracle:thin:@192.168.1.13:1521:xe"
        val usuario = "system"
        val contrasena = "ITR2024"


        val connection = DriverManager.getConnection(ip, usuario, contrasena)

        return connection
    } catch (e:Exception) {
        println("Este es el error: $e")

        return null

    }
}
}

