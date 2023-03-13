package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.UserData
import com.example.gitdroid.models.domain.User

class UserConverter {
    fun convert(userData: UserData): User {
        return User(
            ghLogin = userData.login,
            userId = userData.id
        )
    }

    fun convert(user: User): UserData {
        return UserData(
            login = user.ghLogin,
            id = user.userId
        )
    }
}
