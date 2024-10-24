package com.example.artisania_mobile_views.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.Adapters.BasketRecyclerViewAdapter
import com.example.artisania_mobile_views.R

class BasketActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: BasketRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basket_layout)

        val goBackButton = findViewById<ImageButton>(R.id.btBack)
        goBackButton.setOnClickListener {
            finish()
        }
        recyclerView = findViewById(R.id.rvBasket)
        adapter = BasketRecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

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

}