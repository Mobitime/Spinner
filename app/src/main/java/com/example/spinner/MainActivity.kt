package com.example.spinner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerPosition: Spinner
    private lateinit var btnSave: Button
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etAge: EditText
    private lateinit var lvEmployees: ListView
    private lateinit var toolbarSpinner: Spinner

    private val employeeList = mutableListOf<Person>()
    private lateinit var employeeAdapter: EmployeeAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerPosition = findViewById(R.id.spinnerPosition)
        btnSave = findViewById(R.id.btnSave)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etAge = findViewById(R.id.etAge)
        lvEmployees = findViewById(R.id.lvEmployees)
        setSupportActionBar(findViewById(R.id.toolbar))
        toolbarSpinner = findViewById(R.id.toolbarSpinner)

        val positions = listOf("Инженер", "Конструктор", "Энергетик", "Бухгалтер")
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            positions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPosition.adapter = spinnerAdapter

        val toolbarSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("Все должности") + positions
        )
        toolbarSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toolbarSpinner.adapter = toolbarSpinnerAdapter

        employeeAdapter = EmployeeAdapter(this, employeeList)
        lvEmployees.adapter = employeeAdapter

        btnSave.setOnClickListener { saveEmployee() }

        toolbarSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedPosition = toolbarSpinner.selectedItem.toString()
                filterOrSortList(selectedPosition)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        lvEmployees.setOnItemClickListener { _, _, position, _ ->
            employeeList.removeAt(position)
            updateListView()
            Toast.makeText(this, "Работник удален", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveEmployee() {
        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val age = etAge.text.toString()
        val position = spinnerPosition.selectedItem.toString()

        if (firstName.isBlank() || lastName.isBlank() || age.isBlank()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val employee = Person(firstName, lastName, age.toInt(), position)
        employeeList.add(employee)
        updateListView()

        etFirstName.text.clear()
        etLastName.text.clear()
        etAge.text.clear()
        spinnerPosition.setSelection(0)
    }

    private fun updateListView(filteredEmployees: List<Person> = employeeList) {
        employeeAdapter.updateList(filteredEmployees)
    }

    private fun filterOrSortList(selectedPosition: String) {
        if (selectedPosition == "Все должности") {
            val sortedList = employeeList.sortedBy { it.position }
            updateListView(sortedList)
        } else {
            val filteredList = employeeList.filter { it.position == selectedPosition }
            updateListView(filteredList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
