package com.example.avanceproyecto_atenisa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.avanceproyecto_atenisa.R
import com.example.avanceproyecto_atenisa.models.Color
//revisar clase semana 4.1 desde la hora 1:00:00

class ColorAdapter(var colors: ArrayList<Color>, private val clickListener: OnItemClickListener): RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    inner class ColorViewHolder(itemView: View): ViewHolder(itemView) {
        private var cantidad = 0
        private val tvColor: TextView = itemView.findViewById(R.id.tvColor)
        private val btAdd: ImageButton = itemView.findViewById(R.id.btAdd)
        private val btRemove: ImageButton = itemView.findViewById(R.id.btRemove)
        private val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)

        fun bind(color: Color, clickListener: OnItemClickListener) {
            tvColor.text = color.name
            btAdd.setOnClickListener {
                cantidad++
                tvCantidad.text = cantidad.toString()
                clickListener.onItemClick()
            }
            btRemove.setOnClickListener {
                if (cantidad > 0) {
                    cantidad--
                }
                tvCantidad.text = cantidad.toString()
                clickListener.onItemClick()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorAdapter.ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_add_colors, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorAdapter.ColorViewHolder, position: Int) {
        holder.bind(colors[position], clickListener)
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    interface OnItemClickListener {
        fun onItemClick()
    }

}

