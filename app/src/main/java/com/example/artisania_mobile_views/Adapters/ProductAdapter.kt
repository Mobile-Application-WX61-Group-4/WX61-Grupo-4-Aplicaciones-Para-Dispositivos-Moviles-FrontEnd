// ProductAdapter.kt
package com.example.avanceproyecto_atenisa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.R
import com.example.avanceproyecto_atenisa.models.Product

class ProductAdapter(val products: List<Product>, val itemClickListener: OnItemClickListener): RecyclerView.Adapter<ProductPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductPrototype {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_details, parent, false)
        return ProductPrototype(view)
    }

    override fun onBindViewHolder(holder: ProductPrototype, position: Int) {
        holder.bind(products[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}

class ProductPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product, itemClickListener: OnItemClickListener) {
        itemView.setOnClickListener {
            itemClickListener.OnItemClicked(product)
        }
    }
}

interface OnItemClickListener {
    fun OnItemClicked(product: Product)
}