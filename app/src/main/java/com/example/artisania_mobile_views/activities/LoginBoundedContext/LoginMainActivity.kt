package com.example.artisania_mobile_views.activities.LoginBoundedContext

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
import org.w3c.dom.Text
import java.util.ArrayList
import android.os.Handler
import android.os.Looper
import com.example.artisania_mobile_views.activities.MainMenuActivity


class MainActivity : AppCompatActivity() {

    lateinit var accounts: ArrayList<Account>


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
        accounts = ArrayList()
        accounts.add(Account("admin", "password"))

        val btLogin = findViewById<Button>(R.id.btLogin)

        btLogin.setOnClickListener {
            val tvUser = findViewById<TextView>(R.id.tvUser).text.toString()
            val tvPassword = findViewById<TextView>(R.id.tvPassword).text.toString()

            var loginSuccess = false
            for (account in accounts) {
                if (account.user == tvUser && account.password == tvPassword) {
                    loginSuccess = true
                    break
                }
            }

            if (loginSuccess) {
                Toast.makeText(this, "Has iniciado sesión", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent)
                }, 2000) // 2 seconds delay
            } else {
                Toast.makeText(this, "Usuario y Contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onSignup(){
        val tvSignup = findViewById<TextView>(R.id.tvSignup)

        tvSignup.setOnClickListener(){
            setContentView(R.layout.register)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

    }
}