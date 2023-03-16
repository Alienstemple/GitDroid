package com.example.gitdroid.models.data

import com.google.gson.annotations.SerializedName

/**
 * Пользователь GitHub
 */
data class UserData(
    @SerializedName("login") val login: String = "",
    @SerializedName("id") val id: Int = 0,
)
