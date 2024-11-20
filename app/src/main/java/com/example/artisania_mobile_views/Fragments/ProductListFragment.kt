package com.example.artisania_mobile_views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.Adapters.HorizontalRecyclerView
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.models.Product

class ProductListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalRecyclerView
    private var productList: List<Product> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        adapter = HorizontalRecyclerView(productList, activity as HorizontalRecyclerView.OnItemClickListener)
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        fun newInstance(products: List<Product>): ProductListFragment {
            val fragment = ProductListFragment()
            val args = Bundle()
            args.putParcelableArrayList("products", ArrayList(products))
            fragment.arguments = args
            return fragment
        }
    }
}