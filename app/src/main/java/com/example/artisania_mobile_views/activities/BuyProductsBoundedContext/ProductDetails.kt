package com.example.artisania_mobile_views.activities.BuyProductsBoundedContext

import android.content.Intent

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artisania_mobile_views.R

class ProductDetails : AppCompatActivity() {

    private var cantidad = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btcustom = findViewById<Button>(R.id.btAddBasket) // Ensure this is correct

        val btadd = findViewById<ImageButton>(R.id.btAdd)
        val btremove = findViewById<ImageButton>(R.id.btRemove)
        val tvcantidad = findViewById<TextView>(R.id.tvCantidad)

        // Asigna el OnClickListener al botón
        btcustom.setOnClickListener {
            val intent = Intent(this, CustomProduct::class.java)
            startActivity(intent)
        }
        btadd.setOnClickListener {
            cantidad++
            tvcantidad.text = cantidad.toString()
        }
        btremove.setOnClickListener {
            cantidad--
            tvcantidad.text = cantidad.toString()
        }


    }

    //el doy accion al menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btAddBasket -> {
                val intent = Intent(this, CustomProduct::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}