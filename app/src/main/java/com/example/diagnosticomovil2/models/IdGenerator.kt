package com.example.diagnosticomovil2.models
object IdGenerator {
    private var currentId = 0

    fun getNextId(): Int {
        currentId++
        return currentId
    }
}