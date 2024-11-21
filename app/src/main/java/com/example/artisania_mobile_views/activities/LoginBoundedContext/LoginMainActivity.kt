package com.example.artisania_mobile_views.activities.LoginBoundedContext

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.activities.MainMenuActivity
import com.example.artisania_mobile_views.models.Customer
import com.example.artisania_mobile_views.models.User
import com.example.artisania_mobile_views.network.CustomerApiService
import com.example.artisania_mobile_views.network.LoginRequest
import com.example.artisania_mobile_views.network.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit.Builder
import java.io.StringReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        OnLogin()
        onSignup()
    }

    private fun OnLogin() {
        val btLogin = findViewById<Button>(R.id.btLogin)

        btLogin.setOnClickListener {
            val tvUser = findViewById<TextView>(R.id.tvUser).text.toString()
            val tvPassword = findViewById<TextView>(R.id.tvPassword).text.toString()

            if (tvUser.isNotBlank() && tvPassword.isNotBlank()) {
                loginUser(tvUser, tvPassword)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(correo: String, contraseña: String) {
        val retrofit = createRetrofit()

        val apiService = retrofit.create(CustomerApiService::class.java)
        val loginRequest = LoginRequest(correo, contraseña)
        val call = apiService.loginUser(loginRequest)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val token = response.body()
                    if (token != null) {
                        Toast.makeText(this@MainActivity, "Login successful", Toast.LENGTH_SHORT).show()
                        fetchCustomers { customers ->
                            val customer = customers.find { it.correo == correo }
                            var usuario = customer?.usuario ?: "User"
                            usuario = usuario.replace(Regex("\\d"), " ")
                            Log.d("Customer", customer.toString())

                            // Guardar el estado de isArtisan en SharedPreferences
                            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("isArtisan", customer?.isArtisan ?: false)
                            editor.putString("usuario", usuario)
                            editor.apply()

                            val intent = Intent(this@MainActivity, MainMenuActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Login failed: Token is null", Toast.LENGTH_SHORT).show()
                        Log.e("LoginMainActivity", "Login failed: Token is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@MainActivity, "Login failed: $errorBody", Toast.LENGTH_SHORT).show()
                    Log.e("LoginMainActivity", "Login failed: $errorBody")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("LoginMainActivity", "Login request failed", t)
            }
        })
    }

    private fun onSignup(){
        val tvSignup = findViewById<TextView>(R.id.tvSignup)

        tvSignup.setOnClickListener(){
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl("https://artisania.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun fetchCustomers(callback: (List<Customer>) -> Unit) {
        val retrofit = createRetrofit()
        val apiService = retrofit.create(CustomerApiService::class.java)
        val call = apiService.getCustomers()

        call.enqueue(object : Callback<List<Customer>> {
            override fun onResponse(call: Call<List<Customer>>, response: Response<List<Customer>>) {
                if (response.isSuccessful) {
                    val customers = response.body() ?: emptyList()
                    callback(customers)
                } else {
                    Log.e("LoginMainActivity", "Failed to fetch customers: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                Log.e("LoginMainActivity", "Failed to fetch customers", t)
            }
        })
    }

}