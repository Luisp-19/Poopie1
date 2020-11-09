package com.example.poopie1.adaptER

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poopie1.Notas.Notas
import com.example.poopie1.R

class NotasAdapt internal constructor(
   context: Context, val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<NotasAdapt.NotasViewHolder>()  {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notaz = emptyList<Notas>()

    class NotasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  notasItemView: TextView = itemView.findViewById(R.id.view1)
        val notas2SubItemView: TextView = itemView.findViewById(R.id.view2)

        fun bind (notas: Notas, clickListener: OnItemClickListener){
            notasItemView.text = notas.nota
            notas2SubItemView.text = notas.subnota

            itemView.setOnClickListener {
                clickListener.onItemClicked(notas)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : NotasViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NotasViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotasViewHolder, postion: Int){
        val current = notaz[postion]
        holder.notasItemView.text = current.nota
        holder.notas2SubItemView.text = current.subnota
        holder.bind( current, itemClickListener)
    }

    internal fun setNotas(notaz: List<Notas>){
        this.notaz=notaz
        notifyDataSetChanged()
    }

    fun getAt( position: Int ): Notas {
        return notaz[position]
    }

    interface OnItemClickListener {
        fun onItemClicked (notas: Notas)
    }

    override fun getItemCount() = notaz.size

}