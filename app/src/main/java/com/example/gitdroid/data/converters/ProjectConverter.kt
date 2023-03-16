package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.ProjectData
import com.example.gitdroid.models.domain.Project

/**
 * Служебный класс для преобразования модели [ProjectData] уровня data
 * в модель [Project] уровня domain и обратно
 */
class ProjectConverter {

    private val converter = SearchResultItemConverter()

    /**
     * Преобразоване модели [ProjectData] уровня data
     * в модель [Project] уровня domain
     * @param projectData
     * @return [Project]
     */
    fun convert(projectData: ProjectData): Project {
        return Project(
            projectId = projectData.id,
            projectName = projectData.name,
            searchResList = projectData.searchResList.map {
                converter.convert(it)
            }
        )
    }

    /**
     * Преобразоване модели [Project] уровня domain
     * в модель [ProjectData] уровня data
     * @param project
     * @return [ProjectData]
     */
    fun convert(project: Project): ProjectData {
        return ProjectData(
            id = project.projectId,
            name = project.projectName,
            searchResList = project.searchResList.map {
                converter.convert(it)
            }
        )
    }
}