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
import com.example.artisania_mobile_views.models.Product
import com.example.avanceproyecto_atenisa.db.AppDataBase

class BasketActivity : AppCompatActivity(), BasketRecyclerViewAdapter.OnItemClickListener {

    private lateinit var recyclerView : RecyclerView
    private lateinit var tvTotalPice : TextView

    val suma = 0

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

        val intent = Intent(this, OrderConfirmationActivity::class.java)
        val clickListener = View.OnClickListener {
            startActivity(intent)
            dialog.dismiss()
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
    override fun onItemClick(product: Product) {
        val dao = AppDataBase.getInstance(this).getDao()
        dao.delete(product)
        onResume()
    }

}