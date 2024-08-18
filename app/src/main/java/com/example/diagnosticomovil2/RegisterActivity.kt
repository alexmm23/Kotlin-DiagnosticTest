package com.example.diagnosticomovil2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.example.diagnosticomovil2.models.Tool
import com.example.diagnosticomovil2.models.IdGenerator
import com.example.diagnosticomovil2.models.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RegisterActivity : AppCompatActivity() {
    var tools: MutableList<Tool> =  mutableListOf()
    val utils = Utils()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        utils.clearPreferences(this)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar3)
        setSupportActionBar(toolbar)
        // Obtener referencias a los elementos de la interfaz
        val edtToolName: EditText = findViewById(R.id.edtToolName)
        val edtToolBrand: EditText = findViewById(R.id.edtToolBrand)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val spnType: Spinner = findViewById(R.id.spnType)
        val edtPrice: EditText = findViewById(R.id.edtPrice)
        val btnSave: Button = findViewById(R.id.btnSave)
        val options = resources.getStringArray(R.array.types)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnType.adapter = adapter

        btnSave.setOnClickListener {
            Toast.makeText(this, "Guardando...", Toast.LENGTH_SHORT).show()
            val toolName = edtToolName.text.toString().trim()
            val toolBrand = edtToolBrand.text.toString().trim()
            val priceText = edtPrice.text.toString().trim()

            // Validaciones para EditText
            if (toolName.isEmpty()) {
                edtToolName.error = "El nombre es obligatorio"
                return@setOnClickListener
            }
            if (toolBrand.isEmpty()) {
                edtToolBrand.error = "La marca es obligatoria"
                return@setOnClickListener
            }
            if (priceText.isEmpty()) {
                edtPrice.error = "El precio es obligatorio"
                return@setOnClickListener
            }

            // Validación para RadioGroup
            val selectedRadioId = radioGroup.checkedRadioButtonId
            if (selectedRadioId == -1) {
                Toast.makeText(this, "Seleccione una opción", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val isElectric = findViewById<RadioButton>(selectedRadioId).text.toString()

            val selectedType = spnType.selectedItem?.toString()
            if (selectedType.isNullOrEmpty()) {
                Toast.makeText(this, "Seleccione un tipo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price: Float = try {
                priceText.toFloat()
            } catch (e: NumberFormatException) {
                edtPrice.error = "Precio inválido"
                return@setOnClickListener
            }
            val tool =  Tool(
                IdGenerator.getNextId(),
                toolName,
                selectedType,
                toolBrand,
                price.toDouble(),
                isElectric == "Sí" // Si el valor es "Sí" entonces es true, de lo contrario es false
            )
            Toast.makeText(this, "Herramienta registrada", Toast.LENGTH_SHORT).show()
            if (toolExists(tool)) {
                Toast.makeText(this, "La herramienta ya existe", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            tools.add(tool)
            utils.saveToolsToSharedPreferences(this, tools)
            clearInputs()
        }
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

    private fun open(option: String) {
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
    private fun toolExists(tool: Tool): Boolean {
        return tools.any { it.name == tool.name && it.brand == tool.brand || it.id == tool.id }
    }
    private fun clearInputs() {
        findViewById<EditText>(R.id.edtToolName).setText("")
        findViewById<EditText>(R.id.edtToolBrand).setText("")
        findViewById<EditText>(R.id.edtPrice).setText("")
        findViewById<RadioGroup>(R.id.radioGroup).clearCheck()
        findViewById<Spinner>(R.id.spnType).setSelection(0)
    }


}