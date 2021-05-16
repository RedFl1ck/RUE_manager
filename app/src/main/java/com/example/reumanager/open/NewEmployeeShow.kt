package com.example.reumanager.open

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.reumanager.R
import com.example.reumanager.data.model.NewEmployee
import com.example.reumanager.databinding.ActivityNewEmployeeShowBinding

class NewEmployeeShow : AppCompatActivity() {

    companion object {

        const val NEW_EMPLOYEE = "new_employee"

    }

    private lateinit var binding: ActivityNewEmployeeShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewEmployeeShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()

        val arg = intent.getParcelableExtra<NewEmployee>(NEW_EMPLOYEE)

        binding.nameShow.text = arg?.name
        binding.surnameShow.text = arg?.surname
        binding.patronymicShow.text = arg?.patronymic
        binding.educationShow.text = arg?.education
        binding.experienceShow.text = arg?.experience
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.show, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}