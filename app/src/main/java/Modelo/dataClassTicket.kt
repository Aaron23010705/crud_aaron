package Modelo
 data class dataClassTicket(
    val UUID_Ticket: String,
     var num_ticket: Int,
    val titulo_ticket: String,
    val descripcion: String,
    val autor:String,
    val email_autor:String,
    val fecha_ticket:String,
    val estado:String,
    val fecha_fin_ticket:String

);
