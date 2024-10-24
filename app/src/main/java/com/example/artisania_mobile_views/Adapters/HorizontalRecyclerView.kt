package com.example.artisania_mobile_views.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.activities.BuyProductsBoundedContext.ProductDetails

class HorizontalRecyclerView(private val context: Context): RecyclerView.Adapter<HorizontalRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalRecyclerView.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetails::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}