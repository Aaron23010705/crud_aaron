package aaron.garcia.aplicacion_crud_aaron2b

import Modelo.ClaseConexion
import android.content.Context
import android.content.Intent
import android.health.connect.datatypes.units.Length
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
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.util.UUID

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtUsuario = findViewById<TextView>(R.id.txtUsuario)
        val txtContra2 = findViewById<TextView>(R.id.txtContra2)
        val btnRegistrarse2 = findViewById<Button>(R.id.btnRegistrarse2)

        btnRegistrarse2.setOnClickListener {
            val activity_ticket = Intent(this, Ticket::class.java)

            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().CadenaConexion()
                val VerUsuer =
                    objConexion?.prepareStatement("select * from Usuario where nombre_usuario = ? AND contra_usuario = ?")!!
                VerUsuer.setString(1, txtUsuario.text.toString())
                VerUsuer.setString(2, txtContra2.text.toString())

                //En este caso se va a ejecutar el comando en la base

                val usuarioVerdadero = VerUsuer.executeQuery()

                withContext(Dispatchers.Main) {
                    if (usuarioVerdadero.next()) {
                        startActivity(activity_ticket)

                    } else {
                        println("Error")
                    }


                }


            }


        }
    }
}