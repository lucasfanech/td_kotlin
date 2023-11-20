package fr.unilasalle.fanech.td_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private var listUser : ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val item = listUser[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    fun updateList(newList: List<User>) {
        listUser = newList as ArrayList<User>
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewUserFirstName: TextView = itemView.findViewById(R.id.textViewFirstname)
        val textViewUserName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewUserPhone: TextView = itemView.findViewById(R.id.textViewPhone)
        val textViewUserAge: TextView = itemView.findViewById(R.id.textViewAge)

        val background = itemView.findViewById<View>(R.id.itemContact)

        fun bind(item: User) {
            textViewUserFirstName.text = item.firstname.toString()
            textViewUserName.text = item.name.toString()
            textViewUserPhone.text = item.phoneNumber.toString()
            textViewUserAge.text = item.birthDate.toString()
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