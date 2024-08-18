package com.example.diagnosticomovil2.models

import android.content.Context

object IdGenerator {
    private var currentId = 0
    private var utils: Utils = Utils()
    fun getNextId(context: Context): Int {
        utils.getToolsFromSharedPreferences(context)?.let {
            currentId = it.maxOf { tool -> tool.id }
        }
        currentId++
        return currentId
    }
}