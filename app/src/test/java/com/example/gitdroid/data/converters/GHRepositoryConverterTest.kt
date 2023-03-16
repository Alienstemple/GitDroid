package com.example.gitdroid.data.converters

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.models.data.GHRepositoryData
import com.example.gitdroid.models.domain.GHRepository
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class GHRepositoryConverterTest {

    private val ghRepositoryConverter = GHRepositoryConverter()
    private val stubGHRepositoryData: GHRepositoryData = MiscCreator.createGhRepositoryData()
    private val stubGHRepository: GHRepository = MiscCreator.createGhRepository()

    @org.junit.Test
    fun convertFrom() {
        val result = ghRepositoryConverter.convert((stubGHRepositoryData))

        Truth.assertThat(result).isEqualTo(stubGHRepository)
    }

    @org.junit.Test
    fun convertTo() {
        val result = ghRepositoryConverter.convert((stubGHRepository))

        Truth.assertThat(result).isEqualTo(stubGHRepositoryData)
    }
}