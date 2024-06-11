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
import android.app.AlertDialog
import android.widget.EditText
import androidx.recyclerview.R


class Adaptador(private var Datos: List<dataClassTicket>) : RecyclerView.Adapter<ViewHolder>() {

    fun ActualizarLista(nuevoLista: List<dataClassTicket>) {
        Datos = nuevoLista
        notifyDataSetChanged()//Esto notifica al RecyclerView que hay datos nuevos
    }


    fun actualizarPantalla (uuid: String, nuevoEstado: String){
        val index = Datos.indexOfFirst { it.UUID_Ticket == uuid }
        Datos[index].estado= nuevoEstado
        notifyDataSetChanged()
    }
    fun actualizarDatos(nuevoEstado: String, uuid:String){
        GlobalScope.launch(Dispatchers.IO){

            ///1 - creo un objeto de la clase conexion
            val objConexion = ClaseConexion().CadenaConexion()

            //2 - Creo una variable que tenga un prepareStatement
            val updateTicket = objConexion?.prepareStatement("Update Ticket set estado = ? where UUID_Ticket = ?")!!
            updateTicket.setString(1, nuevoEstado)
            updateTicket.setString(2, uuid)
            updateTicket.executeUpdate()
            withContext(Dispatchers.Main) {
                actualizarPantalla(uuid, nuevoEstado)
            }


        }
    }

    fun EliminarDatos(estado: String, Posicion: Int) {
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(Posicion)
        GlobalScope.launch(Dispatchers.IO) {
            // creamos un objeto de la clase conexion

            val objConexion = ClaseConexion().CadenaConexion()

            // 2- Crear una variable que contenga un preparestatement (donde se mete el código de sqlserver
            val deleteTicket =
                objConexion?.prepareStatement("delete from Ticket where estado = ?")!!
            deleteTicket.setString(1, estado)
            deleteTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        //Notificar el adaptador sobre los cambios
        notifyItemRemoved(Posicion)
        notifyDataSetChanged()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context)
                .inflate(aaron.garcia.aplicacion_crud_aaron2b.R.layout.activity_card, parent, false)

        return ViewHolder(vista)
    }



    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = Datos[position]
        holder.textView.text = ticket.estado

        holder.imgBorrar.setOnClickListener {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Elimnar")
            builder.setMessage("¿Está seguro que quiere eliminar la mascota?")

            //Botones
            builder.setPositiveButton("Si") { dialog, which ->
                EliminarDatos(ticket.estado, position)
            }
            builder.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
            holder.imgEditar.setOnClickListener {
                val context = holder.itemView.context

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Editar")
                builder.setMessage("Estas seguro que quieres editar?")

//Agregar un cuadro de texto para que el usuario escriba el nuevo nombre.

                val cuadroTexto = EditText(context)
                cuadroTexto.setHint(ticket.estado)
                builder.setView(cuadroTexto)

                builder.setPositiveButton("Actualizar ") { dialog, which ->
                    actualizarDatos(cuadroTexto.text.toString(), ticket.UUID_Ticket)
                }
                builder.setNegativeButton("no") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }






    }




