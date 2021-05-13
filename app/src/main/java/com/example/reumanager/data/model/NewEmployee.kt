package com.example.reumanager.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewEmployee(
    val name: String,
    val surname: String,
    val patronymic: String,
    val education: String,
    val experience: String
): Parcelable