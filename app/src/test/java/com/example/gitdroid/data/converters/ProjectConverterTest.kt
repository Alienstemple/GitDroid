package com.example.gitdroid.data.converters

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.models.data.GHRepositoryData
import com.example.gitdroid.models.data.ProjectData
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Project
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class ProjectConverterTest {

    private val projectConverter = ProjectConverter()
    private val stubProjectData: ProjectData = MiscCreator.createProjectData()
    private val stubProject: Project = MiscCreator.createProject()

    @org.junit.Test
    fun convertFrom() {
        val result = projectConverter.convert((stubProjectData))
        Truth.assertThat(result).isEqualTo(stubProject)
    }

    @org.junit.Test
    fun convertTo() {
        val result = projectConverter.convert((stubProject))
        Truth.assertThat(result).isEqualTo(stubProjectData)
    }
}