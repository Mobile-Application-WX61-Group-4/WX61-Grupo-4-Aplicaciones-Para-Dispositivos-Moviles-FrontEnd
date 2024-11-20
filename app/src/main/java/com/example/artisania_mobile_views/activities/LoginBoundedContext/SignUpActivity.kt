package com.example.artisania_mobile_views.activities.LoginBoundedContext

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.models.User
import com.example.artisania_mobile_views.network.CustomerApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var tvFirstname: EditText
    private lateinit var tvLastname: EditText
    private lateinit var tvPassword: EditText
    private lateinit var tvEmail: EditText
    private lateinit var btCreate: Button
    private lateinit var box_si: CheckBox
    private lateinit var box_no: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        tvFirstname = findViewById(R.id.tvFirstname)
        tvLastname = findViewById(R.id.tvLastname)
        tvPassword = findViewById(R.id.tvPassword)
        tvEmail = findViewById(R.id.tvEmail)
        btCreate = findViewById(R.id.btCreate)
        box_si = findViewById(R.id.box_si)
        box_no = findViewById(R.id.box_no)

        box_si.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                box_no.isChecked = false
            }
        }

        box_no.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                box_si.isChecked = false
            }
        }

        btCreate.setOnClickListener {
            val firstname = "1" + tvFirstname.text.toString()
            val lastname = "1" + tvLastname.text.toString()
            val password = tvPassword.text.toString()
            val email = tvEmail.text.toString()
            val isArtisan = box_si.isChecked


            if (firstname.isNotBlank() && lastname.isNotBlank() && password.isNotBlank() && email.isNotBlank()) {
                val username = "${firstname}${lastname}".replace(" ", "")
                Log.d("SignUpActivity", "Username: $username")
                val user = User(username, password, email, "", isArtisan)
                registerUser(user)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(user: User) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://artisania.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(CustomerApiService::class.java)
        val call = apiService.registerUser(user)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SignUpActivity, "User registered successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    intent.putExtra("isArtisan", user.isArtisan)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@SignUpActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                    Log.e("SignUpActivity", "Registration failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("SignUpActivity", "Registration failed", t)
            }
        })
    }
}