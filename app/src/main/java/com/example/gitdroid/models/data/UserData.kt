package com.example.gitdroid.models.data

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("login") val login: String = "",
    @SerializedName("id") val id: Int = 0,
)
