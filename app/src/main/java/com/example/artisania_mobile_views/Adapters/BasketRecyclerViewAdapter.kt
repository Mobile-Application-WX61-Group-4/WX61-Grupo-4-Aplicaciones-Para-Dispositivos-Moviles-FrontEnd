package com.example.artisania_mobile_views.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.models.Product
import com.squareup.picasso.Picasso

class BasketRecyclerViewAdapter(private val products: List<Product>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<BasketRecyclerViewAdapter.MyViewHolder>() {
        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)    {
            private val tvName: TextView = itemView.findViewById(R.id.tvNamebasquet)
            //private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
            private val cvbasquetproduct: CardView = itemView.findViewById(R.id.cvBasquetproduct)
            private val ivImagen = itemView.findViewById<ImageView>(R.id.ivProductbasquet)

            fun bind(product: Product, clickListener: OnItemClickListener) {
                tvName.text = product.nombre
                //tvPrice.text = product.price.toString()

                Picasso.get()
                    .load(product.imagen)
                    .into(ivImagen)
                cvbasquetproduct.setOnClickListener {
                    clickListener.onItemClick(product)
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRecyclerViewAdapter.MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: BasketRecyclerViewAdapter.MyViewHolder, position: Int) {
            holder.bind(products[position], itemClickListener)
        }

        override fun getItemCount(): Int {
            return products.size
        }

        interface OnItemClickListener {
            fun onItemClick(product: Product)
        }
    }