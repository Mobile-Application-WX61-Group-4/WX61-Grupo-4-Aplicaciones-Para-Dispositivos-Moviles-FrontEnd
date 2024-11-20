package com.example.artisania_mobile_views.activities.ChatBoundedContext

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.activities.MainMenuActivity
import com.example.artisania_mobile_views.models.Product
import com.example.artisania_mobile_views.network.ProductsApiService
import com.example.artisania_mobile_views.services.ImageUploadResponse
import com.example.artisania_mobile_views.services.ImageUploadService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SubirProductoActivity : AppCompatActivity() {

    private lateinit var btAddProduct: Button
    private lateinit var tvProductoNombre: EditText
    private lateinit var tvProductoPrecio: EditText
    private lateinit var tvProductoDescripcion: EditText
    private lateinit var ivProductImage: ImageView
    private lateinit var imageInput: EditText
    private var imagePath: String? = null
    private lateinit var imageUploadService: ImageUploadService
    private val clientId = "dc5e67ed4c84aff"
    private val REQUEST_CODE_PERMISSIONS = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subir_producto)

        btAddProduct = findViewById(R.id.add_product_button)
        tvProductoNombre = findViewById(R.id.product_name_input)
        tvProductoPrecio = findViewById(R.id.price_input)
        tvProductoDescripcion = findViewById(R.id.product_description_input)
        ivProductImage = findViewById(R.id.product_image)
        imageInput = findViewById(R.id.image_input)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSIONS
            )
        }

        ivProductImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        imageUploadService = retrofit.create(ImageUploadService::class.java)

        btAddProduct.setOnClickListener {
            val nombre = tvProductoNombre.text.toString().ifBlank { "Nombre por defecto" }
            val precio = tvProductoPrecio.text.toString().toDoubleOrNull() ?: 0.0
            val descripcion = tvProductoDescripcion.text.toString().ifBlank { "Descripción por defecto" }
            val imagen = imageInput.text.toString().ifBlank { "https://default.image.url" }

            if (nombre.isNotBlank() && precio > 0 && descripcion.isNotBlank()) {
                addProduct(Product(null, nombre, precio, descripcion, null, null, null, null, null, null, null, imagen, null, null, emptyList())) { success ->
                    if (success) {
                        Toast.makeText(this, "Producto añadido con éxito", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al añadir el producto", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            uri?.let {
                imagePath = saveImageToInternalStorage(it)
                ivProductImage.setImageURI(Uri.parse(imagePath))
                uploadImage(it)
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

    private fun uploadImage(uri: Uri) {
        val file = File(getRealPathFromURI(uri))
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val call = imageUploadService.uploadImage("Client-ID $clientId", body)
        call.enqueue(object : Callback<ImageUploadResponse> {
            override fun onResponse(call: Call<ImageUploadResponse>, response: Response<ImageUploadResponse>) {
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.data?.link
                    imageInput.setText(imageUrl)
                } else {
                    Toast.makeText(this@SubirProductoActivity, "Error uploading image", Toast.LENGTH_SHORT).show()
                    Log.e("UploadImage", "Error response: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                Toast.makeText(this@SubirProductoActivity, "Upload failed: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("UploadImage", "Upload failed", t)
            }
        })
    }

    private fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            path = cursor.getString(idx)
            cursor.close()
        }
        return path
    }

    private fun addProduct(product: Product, callback: (Boolean) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://artisania.azurewebsites.net/")
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

    private fun allPermissionsGranted() = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ).all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }
}