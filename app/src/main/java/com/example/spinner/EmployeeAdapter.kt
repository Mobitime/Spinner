package com.example.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class EmployeeAdapter(private val context: Context, private var employees: List<Person>) : BaseAdapter() {

    override fun getCount(): Int {
        return employees.size
    }

    override fun getItem(position: Int): Any {
        return employees[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_employee, parent, false)


        val tvFirstName = view.findViewById<TextView>(R.id.tvFirstName)
        val tvLastName = view.findViewById<TextView>(R.id.tvLastName)
        val tvPosition = view.findViewById<TextView>(R.id.tvPosition)
        val tvAge = view.findViewById<TextView>(R.id.tvAge)


        val employee = employees[position]


        tvFirstName.text = employee.firstName
        tvLastName.text = employee.lastName
        tvPosition.text = employee.position
        tvAge.text = "Возраст: ${employee.age}"

        return view
    }


    fun updateList(newEmployees: List<Person>) {
        employees = newEmployees
        notifyDataSetChanged()
    }
}
