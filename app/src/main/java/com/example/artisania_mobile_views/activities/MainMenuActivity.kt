package com.example.artisania_mobile_views.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.artisania_mobile_views.Adapters.HorizontalRecyclerView
import com.example.artisania_mobile_views.Adapters.ViewPageCatalogAdapter
//import com.example.artisania_mobile_views.Adapters.ViewPageCatalogAdapter
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.activities.BuyProductsBoundedContext.ProductDetails
import com.example.artisania_mobile_views.activities.ChatBoundedContext.SubirProductoActivity
import com.example.artisania_mobile_views.models.Customer
import com.example.artisania_mobile_views.models.Product
import com.example.artisania_mobile_views.network.ProductsApiService
import com.example.avanceproyecto_atenisa.comunicacion.ApiResponse
import com.example.avanceproyecto_atenisa.db.AppDataBase
import com.example.avanceproyecto_atenisa.db.ProductDao
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.firebase.FirebaseApp
import java.io.Serializable

class MainMenuActivity : AppCompatActivity(), HorizontalRecyclerView.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var dao: ProductDao

    private var allProducts: List<Product> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        FirebaseApp.initializeApp(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.rvRecommend)
        viewPager = findViewById(R.id.vpCatalog)
        tabLayout = findViewById(R.id.tlCatalog)

        viewPager.isUserInputEnabled = false

        dao= AppDataBase.getInstance(this).getDao()


        val basketButton = findViewById<ImageButton>(R.id.btBasket)
        basketButton.setOnClickListener {
            if (dao.getAll().isEmpty()) {

                Toast.makeText(this, "No products in your basket", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(this, BasketActivity::class.java)
                startActivity(intent)

            }
        }


        val btAddProduct: ImageButton = findViewById(R.id.btAddProduct)
        if (intent.getBooleanExtra("isArtisan", false)) {
            btAddProduct.visibility = View.VISIBLE
            btAddProduct.setOnClickListener {
                val intent = Intent(this, SubirProductoActivity::class.java)
                startActivity(intent)
            }
        } else {
            btAddProduct.visibility = View.GONE
        }

        val searchView = findViewById<SearchView>(R.id.svCatalog)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterProducts(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText)
                return true
            }
        })

        val usuario = intent.getStringExtra("usuario") ?: "User"
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        tvWelcome.text = "Hello $usuario, What art from artisania you want?"
    }
    override fun onResume() {
        super.onResume()
        loadProducts { products ->
            allProducts = products
            recyclerView.adapter = HorizontalRecyclerView(products, this)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            viewPager.adapter = ViewPageCatalogAdapter(this, products)


            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Accesorios"
                    1 -> "Ropa"
                    2 -> "Decoración"
                    3 -> "Joyería"
                    4 -> "Arte"
                    else -> null
                }
            }.attach()
        }

    }

    private fun loadProducts(callback: (List<Product>) -> Unit) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://artisania.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productApiService: ProductsApiService = retrofit.create(ProductsApiService::class.java)
        val request = productApiService.getProducts()

        request.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val products = response.body()?.map { it.toProduct() } ?: emptyList()
                    callback(products)
                }else{
                    println("Error: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })

    }

    private fun filterProducts(query: String?) {
        val filteredProducts = if (query.isNullOrEmpty()) {
            allProducts
        } else {
            allProducts.filter { it.nombre.contains(query, ignoreCase = true) }
        }
        recyclerView.adapter = HorizontalRecyclerView(filteredProducts, this)
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(this, ProductDetails::class.java)
        intent.putExtra("product", product as Serializable)
        startActivity(intent)
    }

    override fun onItemClickBasquet(product: Product) {

        product.cantidad = 1
        val dao= AppDataBase.getInstance(this).getDao()
        dao.insertOne(product)

        Toast.makeText(this, "Person "+ product.nombre+" added to basquet", Toast.LENGTH_SHORT).show()

    }


}