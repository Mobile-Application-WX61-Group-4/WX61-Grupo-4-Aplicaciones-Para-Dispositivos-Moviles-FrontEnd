package com.example.artisania_mobile_views.activities.ChatBoundedContext

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.models.Product
import com.example.artisania_mobile_views.network.ProductsApiService
import com.example.avanceproyecto_atenisa.comunicacion.Caracteristica
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SubirProductoActivity : AppCompatActivity() {

    private lateinit var btAddProduct: Button
    private lateinit var tvProductoNombre: TextView
    private lateinit var tvProductoPrecio: TextView
    private lateinit var tvProductoDescripcion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subir_producto)

        btAddProduct = findViewById(R.id.add_product_button)
        tvProductoNombre = findViewById(R.id.product_name_input)
        tvProductoPrecio = findViewById(R.id.product_quantity_input)
        tvProductoDescripcion = findViewById(R.id.product_description_input)
        val addProductButton: Button = findViewById(R.id.add_product_button)

        addProductButton.setOnClickListener {

            val nombre = tvProductoNombre.text.toString()
            val precio = tvProductoPrecio.text.toString().toDoubleOrNull() ?: 0.0
            val descripcion = tvProductoDescripcion.text.toString()
            val id=2
            val imagen = "default_image_url" // Reemplaza con la URL de la imagen del producto
            val caracteristicas = listOf(
                Caracteristica(nombre = "Example Caracteristica", valor = "Example Value")
            )

            if (nombre.isNotBlank() && precio > 0 && descripcion.isNotBlank()) {
                addProduct(Product(id,nombre, precio, descripcion,null,null,
                    null,null,null,null,null,
                    imagen,null,null,caracteristicas)) { success ->
                    if (success) {
                        Toast.makeText(this, "Producto añadido con éxito", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al añadir el producto", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val intent = Intent(this, ChatMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addProduct(product: Product, callback: (Boolean) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://artisania.azurewebsites.net/") //
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ProductsApiService = retrofit.create(ProductsApiService::class.java)

        val request = apiService.addProduct(product)

        request.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Error: ${t.message}")
                callback(false)
            }
        })
    }
}