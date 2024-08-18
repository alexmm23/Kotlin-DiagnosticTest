package com.example.diagnosticomovil2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.diagnosticomovil2.models.User
import com.example.diagnosticomovil2.models.Utils

class LoginActivity : AppCompatActivity() {
    private val allowedUsers: Array<User> = arrayOf(
        User(1, "alex", "alex1"),
        User(2, "admin", "admin2"),
        User(3, "user", "user3"),
        User(4, "test", "test4"),
        User(5, "root", "root5")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnExit = findViewById<Button>(R.id.btnCancel)
        val utils = Utils()
        utils.clearPreferences(this)
        btnLogin.setOnClickListener {
            login()
        }
        btnExit.setOnClickListener {
            exit()
        }
    }

    private fun login() {
        val user = findViewById<EditText>(R.id.edtName)
        val password = findViewById<EditText>(R.id.edtPassword)
        if(!validateInputs(user.text.toString(), password.text.toString())) {
            Toast.makeText(this, "Usuario y contraseña son requeridos", Toast.LENGTH_SHORT).show()
            user.requestFocus()
            return
        }
        val foundUser = allowedUsers.find { it.name == user.text.toString() }
        if (foundUser != null && foundUser.password == password.text.toString()){
            Toast.makeText(this, "Bienvenido! ${foundUser.name}", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", foundUser.name)
            //Iniciar sharedPreferences
            val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("user", foundUser.name)
            editor.apply()
            startActivity(intent)
        } else {
            Toast.makeText(this, "Usuario o contraseña inválidos", Toast.LENGTH_SHORT).show()
        }
    }
    private fun validateInputs(user: String, password: String): Boolean {
        return user.isNotEmpty() && password.isNotEmpty()
    }
    private fun exit(){
        Toast.makeText(this, "Bye!", Toast.LENGTH_SHORT).show()
        finish()
    }

}