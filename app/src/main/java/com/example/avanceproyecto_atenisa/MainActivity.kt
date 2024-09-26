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
import com.example.avanceproyecto_atenisa.adapter.ColorAdapter
import com.example.avanceproyecto_atenisa.adapter.OnItemClickListener
import com.example.avanceproyecto_atenisa.adapter.ProductAdapter
import com.example.avanceproyecto_atenisa.models.Color
import com.example.avanceproyecto_atenisa.models.Product

class MainActivity : AppCompatActivity() { //, OnItemClickListener

    /*override fun OnItemClicked(product: Product) {
        val intent = Intent(this, ProductDetails::class.java)
        startActivity(intent)
    }
    */



    lateinit var products: List<Product>
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btProducto)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Encuentra el botón por su ID
        val buttonProduct = findViewById<Button>(R.id.btProducto)


        // Asigna el OnClickListener al botón
        buttonProduct.setOnClickListener {
            val intent = Intent(this, ProductDetails::class.java)
            startActivity(intent)
        }



    }


    /*
    override fun onResume() {
        super.onResume()

        productAdapter = ProductAdapter(products, this)

    }
    */

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
}