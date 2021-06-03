package com.example.reumanager.register

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import com.example.reumanager.data.model.RegisterApi
import com.example.reumanager.data.repository.Repository
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

class RegisterViewModel(application: Application) : AndroidViewModel(application){

    private  lateinit var viewModel: MainViewModel

    val currentRegister: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun validation(context: Context,
        viewLifecycleOwner: LifecycleOwner, viewModelStoreOwner: ViewModelStoreOwner,
        name: TextInputLayout, surname: TextInputLayout, middleName: TextInputLayout,
        birthDate: TextInputLayout, email: TextInputLayout, pass: TextInputLayout,
        passSubmit: TextInputLayout, educationInfo: TextInputLayout,
        experience: TextInputLayout, careerObjective: TextInputLayout)
    {
        validateName(name)
        validateSurname(surname)
        validatePatronymic(middleName)
        validateBirthDate(birthDate)
        validateEmail(email)
        validatePass(pass)
        validatePassSubmit(pass, passSubmit)
        validateEducationInfo(educationInfo)
        validateExperience(experience)
        validateCareerObjective(careerObjective)
        if (validateName(name) &&
            validateSurname(surname) &&
            validatePatronymic(middleName) &&
            validateBirthDate(birthDate) &&
            validateEmail(email) &&
            validatePass(pass) &&
            validatePassSubmit(pass, passSubmit) &&
            validateEducationInfo(educationInfo) &&
            validateExperience(experience) &&
            validateCareerObjective(careerObjective)){
            if(isOnline(context)) {
                val name_ = name.editText?.text.toString()
                val surname_ = surname.editText?.text.toString()
                val patronymic_ = middleName.editText?.text.toString()
                val email_ = email.editText?.text.toString()
                val pass_ = pass.editText?.text.toString()
                val educationInfo_ = educationInfo.editText?.text.toString()
                val experience_ = experience.editText?.text.toString()
                val careerObjective_ = careerObjective.editText?.text.toString()
                val repository = Repository.Repository()
                val viewModelFactory = MainViewModelFactory(repository)
                val arrayDate = birthDate.editText?.text.toString().split(".").toTypedArray()
                val date = "${arrayDate[2]}-${arrayDate[1]}-${arrayDate[0]}"
                viewModel = ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MainViewModel::class.java)
                viewModel.register(RegisterApi(name_, surname_, patronymic_, date, email_,
                    pass_, educationInfo_, experience_, careerObjective_))
                viewModel.myRegResponse.observe(viewLifecycleOwner, Observer {
                    currentRegister.value = if(it.code() == 200){
                        Log.d("Response", it.toString())
                        true
                    } else {
                        Log.d("Response", it.toString())
                        email.error = it.message()
                        false
                    }
                })
            } else {
                val builder = android.app.AlertDialog.Builder(context)
                builder.setPositiveButton("OK") { _, _ ->}
                builder.setTitle("Нет доступа к интернету")
                builder.setMessage("Проверьте подключение и попробуйте снова")
                builder.create().show()
            }

        }
    }

    private fun validateName(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return if (temp.isEmpty()){
            field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
            false
        } else {
            field?.error = null
            true
        }
    }

    private fun validateSurname(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return if (temp.isEmpty()){
            field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
            false
        } else {
            field?.error = null
            true
        }
    }

    private fun validatePatronymic(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return if (temp.isEmpty()){
            field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
            false
        } else {
            field?.error = null
            true
        }
    }

    private fun validateBirthDate(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return if (temp.isEmpty()){
            field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
            false
        } else if (!Pattern.matches("\\d\\d.\\d\\d.\\d\\d\\d\\d", temp)){
            field?.error = getApplication<Application>().resources.getString(R.string.error_wrong_birth_date)
            false
        } else {
            field?.error = null
            true
        }
    }

    private fun validateEmail(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return if (temp.isEmpty()){
            field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(temp).matches()){
            field?.error = getApplication<Application>().resources.getString(R.string.error_wrong_e_mail)
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
                field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
                false
            }
            temp.count() < 4 -> {
                field?.error = getApplication<Application>().resources.getString(R.string.error_short_password)
                false
            }
            else -> {
                field?.error = null
                true
            }
        }
    }

    private fun validatePassSubmit(pass: TextInputLayout?, submitPass: TextInputLayout?): Boolean {
        val temp = submitPass?.editText?.text.toString()
        return when {
            temp.isEmpty() -> {
                submitPass?.error = getApplication<Application>().resources.getString(R.string.error_empty)
                false
            }
            temp != pass?.editText?.text.toString() -> {
                submitPass?.error = getApplication<Application>().resources.getString(R.string.error_match_password)
                false
            }
            else -> {
                submitPass?.error = null
                true
            }
        }
    }

    private fun validateEducationInfo(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return when {
            temp.isEmpty() -> {
                field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
                false
            }
            temp.contains('*') -> {
                field?.error = getApplication<Application>().resources.getString(R.string.error_wrong_input)
                false
            }
            else -> {
                field?.error = null
                true
            }
        }
    }

    private fun validateExperience(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return when {
            temp.isEmpty() -> {
                field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
                false
            }
            temp.contains('*') -> {
                field?.error = getApplication<Application>().resources.getString(R.string.error_wrong_input)
                false
            }
            else -> {
                field?.error = null
                true
            }
        }
    }

    private fun validateCareerObjective(field: TextInputLayout?): Boolean {
        val temp = field?.editText?.text.toString()
        return when {
            temp.isEmpty() -> {
                field?.error = getApplication<Application>().resources.getString(R.string.error_empty)
                false
            }
            temp.contains('*') -> {
                field?.error = getApplication<Application>().resources.getString(R.string.error_wrong_input)
                false
            }
            else -> {
                field?.error = null
                true
            }
        }
    }

    fun setListeners(context: Context,
        viewLifecycleOwner: LifecycleOwner, viewModelStoreOwner: ViewModelStoreOwner,
        name: TextInputLayout, surname: TextInputLayout, middleName: TextInputLayout,
        birthDate: TextInputLayout, email: TextInputLayout, pass: TextInputLayout,
        passSubmit: TextInputLayout, educationInfo: TextInputLayout, experience: TextInputLayout,
                     careerObjective: TextInputLayout, button: Button){

        button.setOnClickListener {
            validation(context, viewLifecycleOwner, viewModelStoreOwner, name, surname, middleName,
                birthDate, email, pass, passSubmit, educationInfo, experience, careerObjective)
        }
        passSubmit.editText?.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                validation(context, viewLifecycleOwner, viewModelStoreOwner, name, surname, middleName,
                    birthDate, email, pass, passSubmit, educationInfo, experience, careerObjective)
                true
            } else {
                false
            }
        }
        name.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateName(name)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateName(name)
            }
        })
        surname.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateSurname(surname)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateSurname(surname)
            }
        })
        middleName.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePatronymic(middleName)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePatronymic(middleName)
            }
        })
        birthDate.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateBirthDate(birthDate)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateBirthDate(birthDate)
            }
        })
        email.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateEmail(email)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(email)
            }
        })
        pass.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePass(pass)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePass(pass)
            }
        })
        passSubmit.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePassSubmit(pass, passSubmit)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassSubmit(pass, passSubmit)
            }
        })
        experience.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateExperience(experience)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateExperience(experience)
            }
        })
        careerObjective.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateCareerObjective(careerObjective)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateCareerObjective(careerObjective)
            }
        })
        educationInfo.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateEducationInfo(educationInfo)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEducationInfo(educationInfo)
            }
        })
    }

}