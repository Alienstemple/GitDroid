package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.UserData
import com.example.gitdroid.models.domain.User

class UserConverter {
    fun convert(userData: UserData): User {
        return User(
            GhLogin = userData.login,
            userId = userData.id
        )
    }
}
