package com.example.diagnosticomovil2.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Utils {
    fun clearPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    fun saveToolsToSharedPreferences(context: Context, tools: List<Tool>) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val editor = sharedPreferences.edit()

        // Convertir el array a JSON
        val json = gson.toJson(tools)
        editor.putString("tools", json)
        editor.apply()  // O editor.commit()
    }
    fun getToolsFromSharedPreferences(context: Context): List<Tool>? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val gson = Gson()

        // Recuperar el JSON desde SharedPreferences
        val json = sharedPreferences.getString("tools", null)
        val type = object : TypeToken<List<Tool>>() {}.type

        return gson.fromJson(json, type)
    }

}