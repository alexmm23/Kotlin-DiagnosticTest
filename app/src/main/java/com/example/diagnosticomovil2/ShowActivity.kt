package com.example.diagnosticomovil2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class ShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
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