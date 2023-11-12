package fr.unilasalle.fanech.td_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val listContact : ArrayList<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        val item = listContact[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listContact.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewContactFirstName: TextView = itemView.findViewById(R.id.textViewFirstname)
        val textViewContactName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewContactPhone: TextView = itemView.findViewById(R.id.textViewPhone)
        val textViewContactAge: TextView = itemView.findViewById(R.id.textViewAge)

        val background = itemView.findViewById<View>(R.id.itemContact)

        fun bind(item: Contact) {
            textViewContactFirstName.text = item.firstname.toString()
            textViewContactName.text = item.name.toString()
            textViewContactPhone.text = item.phone.toString()
            textViewContactAge.text = item.age.toString()
            if (item.gender == 0){
                background.setBackgroundResource(R.color.purple_200)
            }
            else if(item.gender == 1){
                background.setBackgroundResource(R.color.teal_200)
            }
            else{
                background.setBackgroundResource(R.color.white)
            }
        }
    }



}