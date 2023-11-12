package fr.unilasalle.fanech.td_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NumbersAdapter(private val listInt : ArrayList<Int>) : RecyclerView.Adapter<NumbersAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumbersAdapter.ViewHolder, position: Int) {
        val item = listInt[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listInt.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewNumberList: TextView = itemView.findViewById(R.id.textViewNumberList)

        fun bind(item: Int) {
            textViewNumberList.text = item.toString()
        }
    }



}