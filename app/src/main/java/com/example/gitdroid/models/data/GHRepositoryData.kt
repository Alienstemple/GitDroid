package com.example.gitdroid.models.data

import com.google.gson.annotations.SerializedName

data class GHRepositoryData(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("full_name") val fullName: String = "",
    @SerializedName("owner") val owner: UserData = UserData(),
)
