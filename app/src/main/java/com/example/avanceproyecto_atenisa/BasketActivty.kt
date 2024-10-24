package com.example.avanceproyecto_atenisa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avanceproyecto_atenisa.adapter.ProductAdapter
import com.example.avanceproyecto_atenisa.db.AppDataBase
import com.example.avanceproyecto_atenisa.models.Product

class BasketActivty : AppCompatActivity(), ProductAdapter.OnItemClickListener {

    private lateinit var rvBasket: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_basket_activty)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvBasket = findViewById(R.id.rvBasket)

    }

    override fun onResume() {
        super.onResume()

        loadProducts { products ->
            rvBasket.adapter = ProductAdapter(products, this)
            rvBasket.layoutManager = LinearLayoutManager(this@BasketActivty)
        }
    }

    private fun loadProducts(onComplete: (List<Product>) -> Unit) {
        val dao = AppDataBase.getInstance(this).getDao()

        onComplete(dao.getAll())
    }

    override fun onItemClick(product: Product) {

    }
}