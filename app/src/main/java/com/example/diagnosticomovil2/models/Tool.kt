package com.example.diagnosticomovil2.models


data class Tool(
    val id: Int,
    val name: String,
    val type: String,
    val brand: String,
    val price: Double,
    val isElectric: Boolean
)
