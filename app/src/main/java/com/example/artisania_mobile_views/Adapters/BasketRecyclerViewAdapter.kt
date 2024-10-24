package com.example.artisania_mobile_views.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.R

class BasketRecyclerViewAdapter: RecyclerView.Adapter<BasketRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketRecyclerViewAdapter.MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 25
    }

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}