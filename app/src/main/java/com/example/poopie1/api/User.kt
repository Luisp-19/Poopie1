package com.example.poopie1.api

data class User(
    val id: Int,
    val username: String,
    val pass: String,
)

data class mapa(
    val id: Int,
    val problema: String,
    val tipo: String,
    val latitude: String,
    val longitude: String,
    val user: String
)