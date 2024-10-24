package com.example.avanceproyecto_atenisa.activities

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avanceproyecto_atenisa.R

class AddColors : AppCompatActivity() {

    private var cantidad = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_colors)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ColorCard)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btadd = findViewById<ImageButton>(R.id.btAdd)
        val btremove = findViewById<ImageButton>(R.id.btRemove)
        val tvcantidad = findViewById<TextView>(R.id.tvCantidad)


        btadd.setOnClickListener {
            cantidad++
            tvcantidad.text = cantidad.toString()
        }
        btremove.setOnClickListener {

            if (cantidad > 0) {
                cantidad--
            }
            tvcantidad.text = cantidad.toString()
        }
    }
}