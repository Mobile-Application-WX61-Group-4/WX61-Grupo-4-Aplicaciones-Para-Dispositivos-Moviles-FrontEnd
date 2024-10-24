
package com.example.avanceproyecto_atenisa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.avanceproyecto_atenisa.R
import com.example.avanceproyecto_atenisa.models.Product

class ProductAdapter(private val products: List<Product>,private val itemClickListener: OnItemClickListener): Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)    {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val btDetails: Button = itemView.findViewById(R.id.btMainDetails)
        private val cvProduct: CardView = itemView.findViewById(R.id.cvProduct)

        fun bind(product: Product, clickListener: OnItemClickListener) {
            tvName.text = product.name
            tvPrice.text = product.price.toString()

            btDetails.setOnClickListener {
                clickListener.onItemClick(product)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_viewproduct, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        holder.bind(products[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }
}


