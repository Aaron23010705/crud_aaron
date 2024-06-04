package aaron.garcia.aplicacion_crud_aaron2b

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombre = findViewById<TextView>(R.id.txtNombre)
        val txtContra = findViewById<TextView>(R.id.txtContra)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        btnRegistrarse.setOnClickListener {
            val activity_login = Intent(this, Login::class.java)

            CoroutineScope(Dispatchers.IO).launch {
               val objConexion = ClaseConexion().CadenaConexion()

               val addUser =
                   objConexion?.prepareStatement("Insert into Usuario (UUID_Usuario, nombre_usuario,contra_usuario) values (?,?,?)")!!
               addUser.setString(1, UUID.randomUUID().toString())
               addUser.setString(2, txtNombre.text.toString())
               addUser.setString(3, txtContra.text.toString())
               addUser.executeUpdate()
               startActivity(activity_login)

           }

        }

    }
}