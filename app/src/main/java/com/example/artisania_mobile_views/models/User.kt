package com.example.artisania_mobile_views.models

data class User(
    val Usuario: String,
    val Contraseña: String,
    val Correo: String,
    val userImage: String = "http://example.com/image.jpg",
    val isArtisan: Boolean = false,
    val role: String = "user"
)