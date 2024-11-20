package com.example.artisania_mobile_views.services

data class ImageUploadResponse(
    val success: Boolean,
    val data: ImageData
)

data class ImageData(
    val link: String
)