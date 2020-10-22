package com.example.poopie1.adaptER

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poopie1.Cities.Cities
import com.example.poopie1.R

class CitiesAdapt internal constructor(
   context: Context
) : RecyclerView.Adapter<CitiesAdapt.CitiesViewHolder>()  {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var citiez = emptyList<Cities>()

    class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  citiesItemView: TextView = itemView.findViewById(R.id.view1)
        val citiesSubItemView: TextView = itemView.findViewById(R.id.view2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CitiesViewHolder {
        val itemView = inflater.inflate(R.layout.reciclerview_item, parent, false)
        return CitiesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, postion: Int){
        val current = citiez[postion]
        holder.citiesItemView.text = current.city
        holder.citiesSubItemView.text = current.country
    }

    internal fun setCities(citiez: List<Cities>){
        this.citiez=citiez
        notifyDataSetChanged()
    }

    override fun getItemCount() = citiez.size
}