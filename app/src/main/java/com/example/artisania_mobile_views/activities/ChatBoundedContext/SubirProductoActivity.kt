package com.example.artisania_mobile_views.activities.ChatBoundedContext

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.models.Product
import com.example.artisania_mobile_views.network.ProductsApiService
import com.example.avanceproyecto_atenisa.comunicacion.Caracteristica
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SubirProductoActivity : AppCompatActivity() {

    private lateinit var btAddProduct: Button
    private lateinit var tvProductoNombre: TextView
    private lateinit var tvProductoPrecio: TextView
    private lateinit var tvProductoDescripcion: TextView
    private lateinit var ivProductImage: ImageView
    private var imagePath: String? = null
    private lateinit var storageReference: StorageReference

    private lateinit var image_input: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subir_producto)

        btAddProduct = findViewById(R.id.add_product_button)
        tvProductoNombre = findViewById(R.id.product_name_input)
        tvProductoPrecio = findViewById(R.id.product_quantity_input)
        tvProductoDescripcion = findViewById(R.id.product_description_input)
        ivProductImage = findViewById(R.id.product_image)

        image_input = findViewById(R.id.image_input)

        storageReference = FirebaseStorage.getInstance().reference

        ivProductImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        btAddProduct.setOnClickListener {
            val nombre = tvProductoNombre.text.toString()
            val precio = tvProductoPrecio.text.toString().toDoubleOrNull() ?: 0.0
            val descripcion = tvProductoDescripcion.text.toString()
            val imagen =  image_input.text.toString()
            val caracteristicas = listOf(
                Caracteristica(nombre = "Example Caracteristica", valor = "Example Value")
            )

            if (nombre.isNotBlank() && precio > 0 && descripcion.isNotBlank()) {
                //imagePath?.let { path ->
                    //uploadImageToFirebase(File(path)) { imageUrl ->
                        //if (imageUrl != null) {
                            addProduct(Product(null, nombre, precio, descripcion, null, null,
                                null, null, null, null, null,
                                imagen, null, null, caracteristicas)) { success ->
                                if (success) {
                                    Toast.makeText(this, "Producto añadido con éxito", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(this, "Error al añadir el producto", Toast.LENGTH_SHORT).show()
                                }
                            }
                        //} else {
                       //     Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                        //}
                    //}
                //}
            }

            val intent = Intent(this, ChatMainActivity::class.java)
            startActivity(intent)
        }

        // Load the image if the path is saved
        imagePath?.let {
            ivProductImage.setImageURI(Uri.parse(it))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            uri?.let {
                imagePath = saveImageToInternalStorage(it)
                ivProductImage.setImageURI(Uri.parse(imagePath))
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String {
        val resolver: ContentResolver = contentResolver
        val inputStream: InputStream? = resolver.openInputStream(uri)
        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
        val directory = File(filesDir, "images")
        if (!directory.exists()) {
            directory.mkdir()
        }
        val file = File(directory, "product_image.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file.absolutePath
    }

    private fun uploadImageToFirebase(file: File, callback: (String?) -> Unit) {
        val fileUri = Uri.fromFile(file)
        val ref = storageReference.child("images/${file.name}")
        val uploadTask = ref.putFile(fileUri)

        uploadTask.addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { uri ->
                callback(uri.toString())
            }.addOnFailureListener {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }

    private fun addProduct(product: Product, callback: (Boolean) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://artisania.azurewebsites.net/") // Ensure this is the correct URL
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