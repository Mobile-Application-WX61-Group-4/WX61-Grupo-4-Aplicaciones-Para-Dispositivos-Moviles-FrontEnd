package com.example.artisania_mobile_views.activities.ChatBoundedContext

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.R
import com.example.artisania_mobile_views.activities.BuyProductsBoundedContext.CustomProduct
import com.example.artisania_mobile_views.activities.BuyProductsBoundedContext.ProductDetails
import com.example.artisania_mobile_views.models.Product
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class ChatArtesano : AppCompatActivity() {
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_artesano)

        val messageRecyclerView: RecyclerView = findViewById(R.id.message_recycler_view)
        val messageInput: EditText = findViewById(R.id.message_input)
        val voiceInputButton: ImageButton = findViewById(R.id.voice_input_button)
        val backButton: ImageButton = findViewById(R.id.back_button)
        val product = intent.getSerializableExtra("product") as? Product


        messageAdapter = MessageAdapter(messages)
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter

        voiceInputButton.setOnClickListener {
            val messageText = messageInput.text.toString()
            if (messageText.isNotEmpty()) {
                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                val message = Message(messageText, currentTime)
                messages.add(message)
                messageAdapter.notifyItemInserted(messages.size - 1)
                messageRecyclerView.scrollToPosition(messages.size - 1)
                messageInput.text.clear()
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, CustomProduct::class.java)
            intent.putExtra("product", product as Serializable)
            startActivity(intent)
            finish()
        }
    }
}