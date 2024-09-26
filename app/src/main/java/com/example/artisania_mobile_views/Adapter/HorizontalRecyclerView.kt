package com.example.artisania_mobile_views.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.R

class HorizontalRecyclerView: RecyclerView.Adapter<HorizontalRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalRecyclerView.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalRecyclerView.MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}