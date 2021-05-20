package com.example.reumanager.login

import android.app.Activity
import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.lifecycle.*
import com.example.reumanager.R
import com.example.reumanager.data.MainViewModel
import com.example.reumanager.data.MainViewModelFactory
import com.example.reumanager.data.model.LoginApi
import com.example.reumanager.data.repository.Repository
import com.example.reumanager.user.User.Companion.user
import com.example.reumanager.user.User.Companion.userLog
import com.google.android.material.textfield.TextInputLayout

class LoginViewModel(application: Application) : AndroidViewModel(application){

    val currentLogin: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private  lateinit var viewModel: MainViewModel

    private fun validation(viewLifecycleOwner: LifecycleOwner,
                           viewModelStoreOwner: ViewModelStoreOwner,
                           loginPassword: TextInputLayout, loginEmail: TextInputLayout){
        validateEmail(loginEmail)
        validatePass(loginPassword)
        if (validateEmail(loginEmail) &&
            validatePass(loginPassword)){
            val name = loginEmail.editText?.text.toString()
            val pass = loginPassword.editText?.text.toString()
            val repository = Repository.Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MainViewModel::class.java)
            viewModel.auth(LoginApi(name, pass))
            viewModel.myResponse.observe(viewLifecycleOwner, Observer {
                userLog = if(it.code() == 200){
                    Log.d("Response", it.body().toString())
                    user.id = it.body()?.result?.id!!
                    user.userId = it.body()?.result?.author?.id!!
                    user.name = it.body()?.result?.author?.name!!
                    user.surname = it.body()?.result?.author?.surname!!
                    user.middleName = it.body()?.result?.author?.middleName!!
                    val date = it.body()?.result?.author?.birthday!!.toString().split('T').toTypedArray()
                    user.birthday = date[0]
                    user.email = it.body()?.result?.author?.email!!
                    user.educationInfo = it.body()?.result?.educationInfo!!
                    user.experience = it.body()?.result?.experience!!
                    user.careerObjective = it.body()?.result?.careerObjective!!
                    user.status = it.body()?.result?.status!!
                    true
                } else {
                    Log.d("Response", it.toString())
                    loginEmail.error = it.message()
                    false
                }
                currentLogin.value = userLog
            })
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
                field?.error = getApplication<Application>().getString(R.string.error_short_password)
                false
            }
            else -> {
                field?.error = null
                true
            }
        }
    }

    fun setListeners(
        viewLifecycleOwner: LifecycleOwner,
        viewModelStoreOwner: ViewModelStoreOwner,
        loginButton: Button,
        loginPassword: TextInputLayout,
        loginEmail: TextInputLayout
    ){

        loginButton.setOnClickListener {
            validation(viewLifecycleOwner, viewModelStoreOwner, loginPassword, loginEmail)
        }
        loginPassword.editText?.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                validation(viewLifecycleOwner, viewModelStoreOwner, loginPassword, loginEmail)
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