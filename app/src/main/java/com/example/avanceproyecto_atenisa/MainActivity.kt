package com.example.avanceproyecto_atenisa

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avanceproyecto_atenisa.activities.ProductDetails
//import com.example.avanceproyecto_atenisa.adapter.OnItemClickListener
import com.example.avanceproyecto_atenisa.adapter.ProductAdapter
import com.example.avanceproyecto_atenisa.comunicacion.ApiResponse
import com.example.avanceproyecto_atenisa.models.Product
import com.example.avanceproyecto_atenisa.network.ProductsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), ProductAdapter.OnItemClickListener { //, OnItemClickListener

    private lateinit var rvProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btProducto)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvProducts = findViewById(R.id.rvProducts)

        // Encuentra el botón por su ID
        val buttonProduct = findViewById<Button>(R.id.btProducto)


        // Asigna el OnClickListener al botón
        buttonProduct.setOnClickListener {
            val intent = Intent(this, ProductDetails::class.java)
            startActivity(intent)
        }

        val buttonBasket = findViewById<Button>(R.id.tvBasket)
        buttonBasket.setOnClickListener {
            val intent = Intent(this, BasketActivty::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        loadProducts { products ->
            rvProducts.adapter = ProductAdapter(products, this)
            rvProducts.layoutManager = LinearLayoutManager(this@MainActivity)
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

    //el doy accion al menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btProducto -> {
                val intent = Intent(this, ProductDetails::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(this, ProductDetails::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }
}