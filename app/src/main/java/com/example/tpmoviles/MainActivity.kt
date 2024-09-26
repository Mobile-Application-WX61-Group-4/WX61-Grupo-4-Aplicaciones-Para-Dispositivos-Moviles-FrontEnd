package com.example.tpmoviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the content view to activity_main.xml

        // Set up button listeners
        val continueButton: Button = findViewById(R.id.continue_button)
        val cancelButton: Button = findViewById(R.id.cancel_button)

        continueButton.setOnClickListener {
            val intent = Intent(this, ChatArtesano::class.java)
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, SubirProductoActivity::class.java)
            startActivity(intent)
        }
    }
}