package com.example.artisania_mobile_views.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.Adapters.HorizontalRecyclerView
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.activities.BuyProductsBoundedContext.ProductDetails
import com.example.artisania_mobile_views.models.Product
import com.example.avanceproyecto_atenisa.db.AppDataBase
import java.io.Serializable

class RecyclerViewFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalRecyclerView
    private var products: List<Product> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            products = it.getParcelableArrayList<Product>("products") ?: emptyList()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        // Initialize the adapter with the product list and set it to the RecyclerView
        adapter = HorizontalRecyclerView(products, object : HorizontalRecyclerView.OnItemClickListener {
            override fun onItemClick(product: Product) {
                // Handle item click
                val intent = Intent(requireContext(), ProductDetails::class.java)
                intent.putExtra("product", product as Serializable)
                startActivity(intent)
            }

            override fun onItemClickBasquet(product: Product) {
                // Handle basket item click
                product.cantidad = 1
                val dao= AppDataBase.getInstance(requireContext()).getDao()
                dao.insertOne(product)

                Toast.makeText(requireContext(), "Person "+ product.nombre+" added to basquet", Toast.LENGTH_SHORT).show()


            }
        })
        recyclerView.adapter = adapter

        // Set the layout manager for the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        return view
    }

    companion object {
        fun newInstance(products: List<Product>): RecyclerViewFragment {
            val fragment = RecyclerViewFragment()
            val args = Bundle()
            args.putParcelableArrayList("products", ArrayList(products))
            fragment.arguments = args
            return fragment
        }
    }


}