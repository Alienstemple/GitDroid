package com.example.gitdroid.data.projects

import com.example.gitdroid.models.domain.Project

object MiscCreator {
    fun createProject(): Project =
        Project(
            projectId = "",
            projectName = "",
            searchResList = emptyList()
        )
}