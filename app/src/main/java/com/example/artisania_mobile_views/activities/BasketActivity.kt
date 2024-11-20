package com.example.artisania_mobile_views.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.Adapters.BasketRecyclerViewAdapter
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.models.Order
import com.example.artisania_mobile_views.models.Parameter
import com.example.artisania_mobile_views.models.Product
import com.example.artisania_mobile_views.network.ProductsApiService
import com.example.avanceproyecto_atenisa.db.AppDataBase
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class BasketActivity : AppCompatActivity(), BasketRecyclerViewAdapter.OnItemClickListener {

    private lateinit var recyclerView : RecyclerView
    private lateinit var tvTotalPice : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basket_layout)

        val goBackButton = findViewById<ImageButton>(R.id.btBack)
        goBackButton.setOnClickListener {
            finish()
        }
        recyclerView = findViewById(R.id.rvBasket)
        tvTotalPice = findViewById(R.id.tvPrice)

        val checkoutButton = findViewById<Button>(R.id.btCheckout)
        checkoutButton.setOnClickListener {
            showBasketDialog()
        }
    }
    private fun showBasketDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.basket_dialog_layout)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.BOTTOM)

        val btnPayOnDelivery = dialog.findViewById<Button>(R.id.btnPayOnDelivery)
        val btnPayWithCard = dialog.findViewById<Button>(R.id.btnPayWithCard)

        val clickListener = View.OnClickListener {
            loadProducts { products ->
                sendOrderToApi(products)
                startActivity(Intent(this, OrderConfirmationActivity::class.java))
                dialog.dismiss()
            }
        }



        btnPayOnDelivery.setOnClickListener(clickListener)
        btnPayWithCard.setOnClickListener(clickListener)

        dialog.show()
    }

    private fun calculateTotalPrice(products: List<Product>): Double {
        return products.sumOf { it.precio }
    }

    override fun onResume() {
        super.onResume()

        loadProducts { products ->
            val totalPrice = calculateTotalPrice(products)
            tvTotalPice.text = getString(R.string.total_price_format, totalPrice)
            recyclerView.adapter = BasketRecyclerViewAdapter(products, this)
            recyclerView.layoutManager = LinearLayoutManager(this@BasketActivity)
        }
    }

    private fun loadProducts(onComplete: (List<Product>) -> Unit) {
        val dao = AppDataBase.getInstance(this).getDao()

        onComplete(dao.getAll())
    }

    private fun sendOrderToApi(products: List<Product>) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://artisania.azurewebsites.net/") // Ensure this is the correct URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val apiService = retrofit.create(ProductsApiService::class.java)
        val dao = AppDataBase.getInstance(this).getDao()

        products.forEach { product ->
            val validPrice = product.precio.coerceIn(5.0, 250.0)

            val order = Order(
                id = null, // Assuming id is auto-generated
                productId = product.id.toString(),
                product = product.nombre,
                parameters = emptyList(), // Set parameters to an empty list
                price = validPrice
            )

            val request = apiService.addOrder(order)
            request.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        dao.delete(product)
                    } else {
                        println("Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        }
    }

    override fun onItemClick(product: Product) {
        val dao = AppDataBase.getInstance(this).getDao()
        dao.delete(product)
        onResume()
    }

}