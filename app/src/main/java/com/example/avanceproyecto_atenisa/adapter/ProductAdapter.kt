package com.example.avanceproyecto_atenisa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.avanceproyecto_atenisa.R
import com.example.avanceproyecto_atenisa.models.Product

class ProductAdapter(val products: List<Product>, val itemClickListener: OnItemClickListener): Adapter<ProductPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductPrototype {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_details, parent, false)

        return ProductPrototype(view)
    }

    override fun onBindViewHolder(holder: ProductPrototype, position: Int) {
        holder.bind(products.get(position), itemClickListener)    }

    override fun getItemCount(): Int {
        return products.size
    }

}

class ProductPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //vincular los componentes de mainactivity con mi logia
    val btproducto = itemView.findViewById<Button>(R.id.btProducto)

    fun bind(product: Product, itemClickListener: OnItemClickListener){
        btproducto.setOnClickListener{
            itemClickListener.OnItemClicked(product)
        }
    }

}

interface OnItemClickListener{ //para que al dar clic en un item se pueda hacer algo
    fun OnItemClicked(product: Product)
}