package com.example.artisania_mobile_views.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.Adapters.HorizontalRecyclerView
//import com.example.artisania_mobile_views.Adapters.ViewPageCatalogAdapter
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.activities.BuyProductsBoundedContext.ProductDetails
import com.example.artisania_mobile_views.activities.ChatBoundedContext.SubirProductoActivity
import com.example.artisania_mobile_views.models.Product
import com.example.artisania_mobile_views.network.ProductsApiService
import com.example.avanceproyecto_atenisa.comunicacion.ApiResponse
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainMenuActivity : AppCompatActivity(), HorizontalRecyclerView.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.rvRecommend)

        val basketButton = findViewById<ImageButton>(R.id.btBasket)
        basketButton.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }

        val btAddProduct: ImageButton = findViewById(R.id.btAddProduct)
        btAddProduct.setOnClickListener {
            val intent = Intent(this, SubirProductoActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        loadProducts { products ->
            recyclerView.adapter = HorizontalRecyclerView(products, this)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }

    }

    private fun loadProducts(onComplete: (List<Product>) -> Unit) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productApiService: ProductsApiService = retrofit.create(ProductsApiService::class.java)
        val request = productApiService.getProducts()

        request.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val productsApi: ApiResponse = response.body()!!
                    val productList = mutableListOf<Product>()

                    productsApi.products?.forEach {
                        productList.add(it.toProduct())

                    }
                    onComplete(productList)
                }
            }


            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })

    }

    override fun onItemClick(product: Product) {
        val intent = Intent(this, ProductDetails::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }


}