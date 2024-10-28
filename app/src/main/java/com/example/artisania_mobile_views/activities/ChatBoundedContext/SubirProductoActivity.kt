package com.example.artisania_mobile_views.activities.ChatBoundedContext

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.artisania_mobile_views.R

class SubirProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subir_producto)

        val addProductButton: Button = findViewById(R.id.add_product_button)
        addProductButton.setOnClickListener {
            val intent = Intent(this, ChatMainActivity::class.java)
            startActivity(intent)
        }
    }
}