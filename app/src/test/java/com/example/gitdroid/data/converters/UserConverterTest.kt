package com.example.gitdroid.data.converters

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.models.data.UserData
import com.example.gitdroid.models.domain.User
import com.google.common.truth.Truth.assertThat

internal class UserConverterTest {

    private val userConverter = UserConverter()
    private val stubUserData: UserData =
        MiscCreator.createUserData()
    private val stubUser: User = MiscCreator.createGhUser()

    @org.junit.Test
    fun convertFrom() {

        val result = userConverter.convert(stubUserData)

        assertThat(result).isEqualTo(stubUser)
    }

    @org.junit.Test
    fun convertTo() {

        val result = userConverter.convert(stubUser)

        assertThat(result).isEqualTo(stubUserData)
    }
}