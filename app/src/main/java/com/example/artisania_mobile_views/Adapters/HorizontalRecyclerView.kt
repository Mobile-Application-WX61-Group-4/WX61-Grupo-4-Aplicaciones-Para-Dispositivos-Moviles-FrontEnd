package com.example.artisania_mobile_views.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.models.Product
import com.squareup.picasso.Picasso

class HorizontalRecyclerView(private val products: List<Product>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<HorizontalRecyclerView.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)    {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val cvProduct: CardView = itemView.findViewById(R.id.cvProduct)
        private val ivImagen = itemView.findViewById<ImageView>(R.id.ivProduct)

        private val btAddBasket = itemView.findViewById<AppCompatImageButton>(R.id.btAddBasket)


        fun bind(product: Product, clickListener: OnItemClickListener) {
            tvName.text = product.nombre
            tvPrice.text = product.precio.toString()

            Picasso.get()
                .load(product.imagen)
                .into(ivImagen)

            btAddBasket.setOnClickListener {
               clickListener.onItemClickBasquet(product)
            }

            ivImagen.setOnClickListener {
                clickListener.onItemClick(product)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalRecyclerView.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalRecyclerView.MyViewHolder, position: Int) {
        holder.bind(products[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    interface OnItemClickListener {
        fun onItemClick(product: Product)
        fun onItemClickBasquet(product: Product)
    }
}