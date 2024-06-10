package Modelo
 data class dataClassTicket(
    val UUID_Ticket: String,
     var num_ticket: Int,
    var titulo_ticket: String,
    var descripcion: String,
    var autor:String,
    var email_autor:String,
    var fecha_ticket:String,
    var estado:String,
    var fecha_fin_ticket:String

);
