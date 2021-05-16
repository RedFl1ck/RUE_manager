package com.example.reumanager.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.reumanager.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var mRegisterViewModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        mRegisterViewModel = RegisterViewModel(application)

        mRegisterViewModel.setListeners(this, this,
            binding.regName, binding.regSurname, binding.regPatronymic,
            binding.regBirthDate, binding.regEMail, binding.regPassword, binding.regPasswordSubmit,
            binding.regEducationInfo, binding.regExperience, binding.regCareerObjective,
            binding.buttonRegIN)

        // Create the observer which updates the UI.
        val nameObserver = Observer<Boolean> { Register ->
            if (Register){
                finish()
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mRegisterViewModel.currentRegister.observe(this, nameObserver)
    }
}