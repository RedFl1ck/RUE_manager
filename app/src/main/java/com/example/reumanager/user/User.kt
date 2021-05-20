package com.example.reumanager.user

import android.app.Application

class User(var id: Int,
           var userId: Int,
           var name: String,
           var surname: String,
           var middleName: String,
           var birthday: String,
           var email: String,
           var educationInfo: String,
           var experience: String,
           var careerObjective: String,
           var status: Int) : Application() {
        companion object {
            var userLog = false
            var user = userNull()

            fun userNull(): User {
                return User(0,
                    0,
                    "not found",
                    "not found",
                    "not found",
                    "not found",
                    "not found",
                    "not found",
                    "not found",
                    "not found",
                    0)
            }
        }
    }