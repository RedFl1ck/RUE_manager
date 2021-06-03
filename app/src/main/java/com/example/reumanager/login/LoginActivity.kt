package com.example.reumanager.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.reumanager.LoadingActivity
import com.example.reumanager.MainActivity
import com.example.reumanager.UserInfo
import com.example.reumanager.data.MainViewModel
import com.example.reumanager.databinding.ActivityLoginBinding
import com.example.reumanager.register.RegisterActivity
import com.example.reumanager.user.User.Companion.user
import com.example.reumanager.user.User.Companion.userLog

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var mLoginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        mLoginViewModel = LoginViewModel(application)

        mLoginViewModel.setListeners(this, this, this,
            binding.loginButton, binding.loginPassword, binding.loginEmail)

        // Create the observer which updates the UI.
        val nameObserver = Observer<Boolean> { Login ->
            if (Login){
                if (user.educationInfo == "!*||*!"){
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, UserInfo::class.java))
                }
                finish()
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mLoginViewModel.currentLogin.observe(this, nameObserver)

        /*binding.loginButton.setOnClickListener {
            if (userLog){
                finish()
            }
        }*/

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        startActivity(Intent(this, LoadingActivity::class.java))
    }

    override fun onBackPressed() {}
}
