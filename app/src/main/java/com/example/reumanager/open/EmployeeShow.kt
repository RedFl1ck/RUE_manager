package com.example.reumanager.open

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.reumanager.R
import com.example.reumanager.data.model.Employee
import com.example.reumanager.databinding.ActivityEmployeeShowBinding

class EmployeeShow : AppCompatActivity() {

    companion object {

        const val EMPLOYEE = "employee"

    }

    private lateinit var binding: ActivityEmployeeShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()

        val arg = intent.getParcelableExtra<Employee>(EmployeeShow.EMPLOYEE)

        binding.nameShow.text = arg?.name
        binding.surnameShow.text = arg?.surname
        binding.patronymicShow.text = arg?.patronymic
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.show, menu)
        return true
    }
}