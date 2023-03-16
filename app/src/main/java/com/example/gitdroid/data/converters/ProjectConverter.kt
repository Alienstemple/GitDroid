package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.ProjectData
import com.example.gitdroid.models.domain.Project

class ProjectConverter {

    private val converter = SearchResultItemConverter()

    fun convert(projectData: ProjectData): Project {
        return Project (
            projectId = projectData.id,
            projectName = projectData.name,
            searchResList = projectData.searchResList.map {
                converter.convert(it)
            }
        )
    }

    fun convert(project: Project): ProjectData {
        return ProjectData (
            id = project.projectId,
            name = project.projectName,
            searchResList = project.searchResList.map {
                converter.convert(it)
            }
        )
    }
}