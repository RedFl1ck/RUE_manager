package com.example.reumanager.user

import android.app.Application

class User(var id: Int,
           var userId: Int,
           var educationInfo: String,
           var experience: String,
           var careerObjective: String,
           var status: Int) : Application() {
        companion object {
            var userLog = false
            var user : User = User(0,
                0,
                "not found",
                "not found",
                "not found",
                0)
        }
    }