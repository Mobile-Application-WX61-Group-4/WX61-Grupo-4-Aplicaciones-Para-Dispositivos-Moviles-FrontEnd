package com.example.artisania_mobile_views.activities.ChatBoundedContext

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.activities.BuyProductsBoundedContext.CustomProduct
import com.example.artisania_mobile_views.activities.MainMenuActivity
import com.example.artisania_mobile_views.models.Product
import java.io.Serializable

class ChatMainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val continueButton: Button = findViewById(R.id.continue_button)
        val cancelButton: Button = findViewById(R.id.cancel_button)

        continueButton.setOnClickListener {
            val product = intent.getSerializableExtra("product") as? Product
            val intent = Intent(this, ChatArtesano::class.java)
            intent.putExtra("product", product as Serializable)
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            val product = intent.getSerializableExtra("product") as? Product
            val intent = Intent(this, CustomProduct::class.java)
            intent.putExtra("product", product as Serializable)
            startActivity(intent)
        }
    }
}