package com.example.reumanager.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.reumanager.MainActivity
import com.example.reumanager.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mLoginViewModel = LoginViewModel(application)

        mLoginViewModel.setListeners(binding.loginButton,
            binding.loginPassword, binding.loginEmail)

        // Create the observer which updates the UI.
        val nameObserver = Observer<Boolean> { Login ->
            if (Login){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mLoginViewModel.currentLogin.observe(this, nameObserver)
    }

    override fun onBackPressed() {}
}
