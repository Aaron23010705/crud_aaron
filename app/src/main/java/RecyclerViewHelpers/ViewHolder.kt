
 package RecyclerViewHelper

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import aaron.garcia.aplicacion_crud_aaron2b.R
import android.widget.ImageView
import androidx.lifecycle.viewmodel.viewModelFactory

 class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.txtTicketCard)
    val imgEditar: ImageView = view.findViewById(R.id.imgEditar)
     val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)
}
