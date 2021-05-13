package com.example.reumanager.login

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.amier.reumanager.user.User
import com.example.reumanager.R
import com.google.android.material.textfield.TextInputLayout

class LoginViewModel(application: Application) : AndroidViewModel(application){

    val currentLogin: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private fun validation(loginPassword: TextInputLayout, loginEmail: TextInputLayout){
        validateEmail(loginEmail)
        validatePass(loginPassword)
        if (validateEmail(loginEmail) &&
            validatePass(loginPassword)){
            val name = loginEmail.editText?.text.toString()
            val pass = loginPassword.editText?.text.toString()
            // TODO: login user
            User.user = true
            currentLogin.value = User.user

        }
    }

    private fun validateEmail(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return if (temp.isEmpty()){
            field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(temp).matches()){
            field?.error = getApplication<Application>().getString(R.string.error_wrong_e_mail)
            false
        } else {
            //TODO: auth check
            field?.error = null
            true
        }
    }

    private fun validatePass(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return when {
            temp.isEmpty() -> {
                field?.error = getApplication<Application>().getString(R.string.error_empty)
                false
            }
            temp.count() < 4 -> {
                //TODO: auth check
                field?.error = getApplication<Application>().getString(R.string.error_short_password)
                false
            }
            else -> {
                field?.error = null
                true
            }
        }
    }

    fun setListeners(loginButton: Button, loginPassword: TextInputLayout, loginEmail: TextInputLayout){

        loginButton.setOnClickListener {
            validation(loginPassword, loginEmail)
        }
        loginPassword.editText?.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                validation(loginPassword, loginEmail)
                true
            } else {
                false
            }
        }
        loginEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateEmail(loginEmail)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(loginEmail)
            }
        })
        loginPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePass(loginPassword)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePass(loginPassword)
            }
        })
    }


}