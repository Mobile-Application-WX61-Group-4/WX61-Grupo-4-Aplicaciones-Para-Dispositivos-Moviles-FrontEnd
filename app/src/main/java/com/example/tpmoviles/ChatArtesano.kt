package com.example.tpmoviles

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ChatArtesano : AppCompatActivity() {
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_artesano)

        val messageRecyclerView: RecyclerView = findViewById(R.id.message_recycler_view)
        val messageInput: EditText = findViewById(R.id.message_input)
        val voiceInputButton: ImageButton = findViewById(R.id.voice_input_button)
        val backButton: ImageButton = findViewById(R.id.back_button)

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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}