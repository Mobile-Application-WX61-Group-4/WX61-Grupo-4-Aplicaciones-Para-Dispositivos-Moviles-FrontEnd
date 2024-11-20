package com.example.artisania_mobile_views.models

data class Customer(
    val id: Int,
    val usuario: String,
    val contraseñaHashed: String,
    val correo: String,
    val imagenUsuario: String,
    val isArtisan: Boolean,
    val role: String
)