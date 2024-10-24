package com.example.artisania_mobile_views.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.Adapters.HorizontalRecyclerView
import com.example.artisania_mobile_views.Adapters.ViewPageCatalogAdapter
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.activities.ChatBoundedContext.SubirProductoActivity
import com.google.android.material.tabs.TabLayoutMediator

class MainMenuActivity : AppCompatActivity() {

    private lateinit var adapter: HorizontalRecyclerView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.rvRecommend)
        adapter = HorizontalRecyclerView(this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        val viewPager = findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.vpCatalog)
        viewPager.adapter = ViewPageCatalogAdapter(this)

        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tlCatalog)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Hottest"
                1 -> "Popular"
                2 -> "New Combo"
                3 -> "Top"
                else -> null
            }
        }.attach()

        val basketButton = findViewById<ImageButton>(R.id.btBasket)
        basketButton.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }

        val btAddProduct: ImageButton = findViewById(R.id.btAddProduct)
        btAddProduct.setOnClickListener {
            val intent = Intent(this, SubirProductoActivity::class.java)
            startActivity(intent)
        }
    }
}