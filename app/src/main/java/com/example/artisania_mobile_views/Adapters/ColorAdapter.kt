package com.example.avanceproyecto_atenisa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.artisania_mobile_views.R
import com.example.avanceproyecto_atenisa.models.Color
//revisar clase semana 4.1 desde la hora 1:00:00

class ColorAdapter(var colors: ArrayList<Color>): Adapter<ColorPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.activity_add_colors, parent, false)

        return ColorPrototype(view)
    }

    override fun onBindViewHolder(holder: ColorPrototype, position: Int) {
        holder.bind(colors.get(position))
    }

    override fun getItemCount(): Int {

        return colors.size
    }
}

class ColorPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvColor: TextView = itemView.findViewById(R.id.tvColor)

    fun bind(color: Color) {
        tvColor.text = color.name
    }
}

