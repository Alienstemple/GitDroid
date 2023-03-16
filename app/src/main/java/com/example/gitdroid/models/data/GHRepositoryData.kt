package com.example.gitdroid.models.data

import com.example.gitdroid.models.domain.User
import com.google.gson.annotations.SerializedName

/**
 * Репозиторий GitHub, модель уровня data
 * @property id Идентифкатор
 * @property name Имя
 * @property fullName Полное назание
 * @property owner Пользователь-влделец [User]
 */
data class GHRepositoryData(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("full_name") val fullName: String = "",
    @SerializedName("owner") val owner: UserData = UserData(),
)
