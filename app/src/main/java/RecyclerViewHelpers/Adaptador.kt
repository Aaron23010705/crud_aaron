package RecyclerViewHelper

import Modelo.dataClassTicket
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import Modelo.ClaseConexion
import aaron.garcia.aplicacion_crud_aaron2b.Ticket
import androidx.recyclerview.R


class Adaptador(private var Datos: List<dataClassTicket>) : RecyclerView.Adapter<ViewHolder>() {

    fun ActualizarLista(nuevoLista: List<dataClassTicket>) {
        Datos = nuevoLista
        notifyDataSetChanged()//Esto notifica al RecyclerView que hay datos nuevos
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(aaron.garcia.aplicacion_crud_aaron2b.R.layout.activity_card, parent, false)

        return ViewHolder(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = Datos[position]
        holder.textView.text = Ticket.titulo_ticket
    }

    fun EliminarDatos (titulo_ticket:String, Posicion: Int){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(Posicion)
        GlobalScope.launch(Dispatchers.IO) {
            // creamos un objeto de la clase conexion

            val objConexion = ClaseConexion().CadenaConexion()

            // 2- Crear una variable que contenga un preparestatement (donde se mete el código de sqlserver
            val deleteTicket = objConexion?.prepareStatement( "delete from Ticket where titulo_ticket = ?")!!
            deleteTicket.setString(1, titulo_ticket)
            deleteTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        //Notificar el adaptador sobre los cambios
        notifyItemRemoved(Posicion)
        notifyDataSetChanged()
    }
    }



