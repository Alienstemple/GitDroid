package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.UserData
import com.example.gitdroid.models.domain.User

/**
 * Служебный класс для преобразования модели [UserData] уровня data
 * в модель [User] уровня domain
 */
class UserConverter {
    /**
     * Преобразоване модели [UserData] уровня data
     * в модель [User] уровня domain
     * @param userData
     * @return [User]
     */
    fun convert(userData: UserData): User {
        return User(
            ghLogin = userData.login,
            userId = userData.id
        )
    }

    /**
     * Преобразоване модели [User] уровня domain
     * в модель [UserData] уровня data
     * @param user
     * @return [UserData]
     */
    fun convert(user: User): UserData {
        return UserData(
            login = user.ghLogin,
            id = user.userId
        )
    }
}
