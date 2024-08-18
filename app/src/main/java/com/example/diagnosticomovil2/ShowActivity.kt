package com.example.diagnosticomovil2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.diagnosticomovil2.models.Tool
import com.example.diagnosticomovil2.models.Utils

class ShowActivity : AppCompatActivity() {

    val utils = Utils()
    private lateinit var tools: List<Tool>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        tools = utils.getToolsFromSharedPreferences(this) ?: emptyList()
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        val txtShow: TextView = findViewById(R.id.txtShow)
        var text = ""
        if (tools.isEmpty()) {
            text = "No hay herramientas registradas"
        } else {
            text = "Herramientas registradas:\n"
            text += "ID - Nombre - Tipo - Marca - Precio - Es eléctrica\n"
            var isElectric = ""
            for (tool in tools) {
                isElectric = if (tool.isElectric) "Si" else "No"
                text += "${tool.id} - ${tool.name} - ${tool.type} - ${tool.brand} - ${tool.price} - ${isElectric}\n"
            }
        }
        txtShow.text = text
    }

    // Import menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itmHome -> {
                open("home")
                true
            }
            R.id.itmRegister -> {
                open("register")
                true
            }
            R.id.itmShow -> {
                open("show")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    fun open(option: String) {
        val intent = when (option) {
            "home" -> Intent(this, MainActivity::class.java)
            "register" -> Intent(this, RegisterActivity::class.java)
            "show" -> Intent(this, ShowActivity::class.java)
            else -> null
        }

        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)?.let {
            startActivity(it)
        }
    }
}