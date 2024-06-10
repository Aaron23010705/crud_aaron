package aaron.garcia.aplicacion_crud_aaron2b

import Modelo.ClaseConexion
import Modelo.dataClassTicket
import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class Ticket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtNumero_Ticket = findViewById<TextView>(R.id.txtNum_Ticket)
        val txtTitulo_Ticket = findViewById<TextView>(R.id.txtTitulo)
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcion_ticket)
        val txtAutor = findViewById<TextView>(R.id.txtAutor)
        val txtEmail_Autor = findViewById<TextView>(R.id.txtEmail_Autor)
        val txtEstado = findViewById<TextView>(R.id.txtEstado)
        val txtFecha_Creacion = findViewById<TextView>(R.id.txtFecha_Creacion)
        val txtFecha_Finalizacion = findViewById<TextView>(R.id.txtFecha_Finalizacion)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)

        val rcvTicket = findViewById<RecyclerView>(R.id.rcvTicket)

        rcvTicket.layoutManager =LinearLayoutManager(this)

        fun obtenerDatos() : List<dataClassTicket>{
            //1- Creo un objeto de la clase conexión
            val objConexion = ClaseConexion().CadenaConexion()

            //2 - Creo un statement
            //El símbolo de pregunta es pq los datos pueden ser nulos
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from Ticket")!!
            //Esta variable result yo lo puse mal xd es resulSet pero  me da hueva cambiarla

            //en esta variable se añaden TODOS los valores de mascotas
            val tickets = mutableListOf<dataClassTicket>()

            //Recorro todos los registros de la base de datos
            //.next() significa que mientras haya un valor después de ese se va a repetir el proceso
            while (resultSet.next()){
                val UUID_Ticket = resultSet.getString("UUID_Ticket")
                val num_ticket = resultSet.getInt("num_ticket")
                val titulo = resultSet.getString("titulo")
                val descripcion = resultSet.getString("descripcion")
                val autor = resultSet.getString("autor")
                val email_autor = resultSet.getString("email_autor")
                val fecha_ticket = resultSet.getString("fecha_ticket")
                val estado = resultSet.getString("estado")
                val fecha_fin_ticket = resultSet.getString("fecha_fin_ticket")

                val ticket = dataClassTicket(UUID_Ticket,num_ticket,titulo,autor,descripcion, email_autor,fecha_ticket,estado,fecha_fin_ticket)
                tickets.add(ticket)
            }
            return tickets
        }
//Ultimo paso
        //Asignar el adaptador al RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val TicketBD = obtenerDatos()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(TicketBD)
                rcvTicket.adapter = adapter
            }
        }



        //2 Programar el botón para agregar
        btnIngresar.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                //1- Creo un objeto de la clase conexión dentro de la cortina
                val objConexion = ClaseConexion().CadenaConexion()

                //2-Creo una variable que contenga un preparestatement
                val addTicket = objConexion?.prepareStatement("insert into Ticket (UUID_Ticket,num_ticket,titulo,descripcion,autor,email_autor,fecha_ticket,estado,fecha_fin_ticket) values(?,?,?,?,?,?,?,?,?)")!!
                addTicket.setString(1, UUID.randomUUID().toString())
                addTicket.setInt(2 , txtNumero_Ticket.text.toString().toInt())
                addTicket.setString(3, txtTitulo_Ticket.text.toString())
                addTicket.setString(4, txtDescripcion.text.toString())
                addTicket.setString(5, txtAutor.text.toString())
                addTicket.setString(6, txtEmail_Autor.text.toString())
                addTicket.setString(7, txtFecha_Creacion.text.toString())
                addTicket.setString(8, txtEstado.toString())
                addTicket.setString(9, txtFecha_Finalizacion.toString())
                addTicket.executeUpdate()

                //Refresco la lista
                val nuevosTickets = obtenerDatos()
                withContext(Dispatchers.Main) {
                    (rcvTicket.adapter as? Adaptador)?.ActualizarLista(nuevosTickets)

                    //el adaptador es el que escucha y vigila que los datos se esten creando
                }
            }
        }

    }


}
}