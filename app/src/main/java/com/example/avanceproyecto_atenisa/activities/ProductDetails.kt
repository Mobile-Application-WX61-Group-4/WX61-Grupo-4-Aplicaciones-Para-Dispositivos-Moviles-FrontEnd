package com.example.avanceproyecto_atenisa.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avanceproyecto_atenisa.MainActivity
import com.example.avanceproyecto_atenisa.R
import com.example.avanceproyecto_atenisa.db.AppDataBase
import com.example.avanceproyecto_atenisa.models.Product
import com.squareup.picasso.Picasso

class ProductDetails : AppCompatActivity() {

    private var cantidad = 0
    private var product: Product? = null
    private lateinit var btBasquet : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btcustom = findViewById<Button>(R.id.btAddBasket)
        val btadd = findViewById<ImageButton>(R.id.btAdd)
        val btremove = findViewById<ImageButton>(R.id.btRemove)
        val tvcantidad = findViewById<TextView>(R.id.tvCantidad)

        product = intent.getSerializableExtra("product") as? Product
        if (product != null) {
            displayProductDetails(product!!)
        } else {
            println("Error: Product is null")
        }

        btcustom.setOnClickListener {
            val intent = Intent(this, CustomProduct::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
        btadd.setOnClickListener {
            cantidad++
            tvcantidad.text = cantidad.toString()
        }
        btremove.setOnClickListener {
            cantidad--
            if (cantidad < 0) {
                cantidad = 0
            }
            tvcantidad.text = cantidad.toString()
        }

        btBasquet = findViewById(R.id.btBasquet2)
        btBasquet.setOnClickListener {
           val dao= AppDataBase.getInstance(this).getDao()
            dao.insertOne(product!!)

            Toast.makeText(this, "Person "+ product!!.name+" added to basquet", Toast.LENGTH_SHORT).show()

        }

        val btGoback = findViewById<Button>(R.id.bt_Goback)
        btGoback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        product?.let { displayProductDetails(it) }
    }

    private fun displayProductDetails(product: Product) {
        findViewById<TextView>(R.id.tvProductoNombre).text = product.name
        findViewById<TextView>(R.id.tvPriice).text = product.price.toString()
        findViewById<TextView>(R.id.tvDetalis).text = product.description

        val imageView = findViewById<ImageView>(R.id.ivImage)
        Picasso.get().load(product.image).into(imageView)
    }



}