package com.example.artisania_mobile_views.activities.BuyProductsBoundedContext

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.models.Product
import com.example.avanceproyecto_atenisa.adapter.ColorAdapter
import com.example.avanceproyecto_atenisa.models.Color
import com.squareup.picasso.Picasso

class CustomProduct : AppCompatActivity(), ColorAdapter.OnItemClickListener {

    var colors = ArrayList<Color>()
    //var colorAdapter = ColorAdapter(colors)
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_custom_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val product = intent.getSerializableExtra("product") as? Product

        val btGoback: Button = findViewById(R.id.bt_Goback)
        btGoback.setOnClickListener {

            val intent = Intent(this, ProductDetails::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
            finish()
        }

        val btAddBasket: Button = findViewById(R.id.btAddBasket)
        btAddBasket.setOnClickListener {


            showAlert()
        }

        if (product != null) {
            findViewById<TextView>(R.id.tvProductoNombre).text = product.name
            val imageView = findViewById<ImageView>(R.id.ivImage)

            Picasso.get().load(product.image).into(imageView)
        } else {
            // Handle the error case where the product is null
            println("Error: Product is null")
        }

        loadcolor()
    }

    override fun onResume() {
        super.onResume()
        val rvColors = findViewById<RecyclerView>(R.id.rvColors)
        rvColors.adapter = ColorAdapter(colors, this)
        rvColors.layoutManager = LinearLayoutManager(this)
    }


    private fun loadcolor() {
        colors.add(Color("Rojo"))
        colors.add(Color("Azul"))
        colors.add(Color("Verde"))
        colors.add(Color("Amarillo"))
        colors.add(Color("Naranja"))
        colors.add(Color("Morado"))
        colors.add(Color("Rosa"))
        colors.add(Color("Negro"))
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("Item added to basket")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    override fun onItemClick() {

    }
}