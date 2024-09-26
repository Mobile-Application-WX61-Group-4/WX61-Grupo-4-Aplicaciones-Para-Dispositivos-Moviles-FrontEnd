package com.example.avanceproyecto_atenisa

import android.content.Intent
import android.os.Bundle
import android.view.ViewStub
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avanceproyecto_atenisa.adapter.ColorAdapter
import com.example.avanceproyecto_atenisa.models.Color

class CustomProduct : AppCompatActivity() {

    var colors = ArrayList<Color>()
    var colorAdapter = ColorAdapter(colors)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_custom_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        loadcolor()
        initView()

        val btGoback: Button = findViewById(R.id.bt_Goback)
        btGoback.setOnClickListener {
            val intent = Intent(this, ProductDetails::class.java)
            startActivity(intent)
            finish()
        }

        val btAddBasket: Button = findViewById(R.id.btAddBasket)
        btAddBasket.setOnClickListener {
            showAlert()
        }
    }

    private fun initView() {
        val rvColors = findViewById<RecyclerView>(R.id.rvColors)
        rvColors.adapter = colorAdapter
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
}