package com.example.gitdroid.data.converters

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.models.data.ProjectData
import com.example.gitdroid.models.domain.Project
import com.google.common.truth.Truth

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