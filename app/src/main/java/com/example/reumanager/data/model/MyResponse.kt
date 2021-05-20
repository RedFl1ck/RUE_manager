package com.example.reumanager.data.model

class MyResponse {

    data class MyResponseInfo(
        val errors : List<String>,
        val message : String,
        val success : Boolean,
        val statusCode : Int
    )

    data class MineUserInfo(
        val result: Result,
        val message: String,
        val success: Boolean,
        val statusCode: Int,
    )

    data class Result(
        val id: Int,
        val author: Author,
        val educationInfo: String,
        val experience: String,
        val careerObjective: String,
        val status: Int
    )

    data class Author(
        val id: Int,
        val name: String,
        val surname: String,
        val middleName: String,
        val birthday: String,
        val email: String
    )
}