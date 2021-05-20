package com.example.reumanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.reumanager.databinding.ActivityMainBinding
import com.example.reumanager.databinding.ActivityUserInfoBinding
import com.example.reumanager.login.LoginActivity
import com.example.reumanager.user.User

class UserInfo : AppCompatActivity() {

    private lateinit var binding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        binding.userCareerObjective.text = User.user.careerObjective
        binding.userEducationInfo.text = User.user.educationInfo
        binding.userExperience.text = User.user.experience
        binding.userName.text = "${User.user.name} ${User.user.surname} ${User.user.middleName}"
        binding.userEmail.text = User.user.email
        binding.userBirthday.text = User.user.birthday
        binding.userStatus.text = when (User.user.status){
            0 -> "На рассмотрении"
            1 -> "Принята"
            -1 -> "Отклонена"
            else -> "Статус неизвестен"
        }

        binding.personalExitButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            User.userLog = false
            User.user = User.userNull()
        }
    }
}