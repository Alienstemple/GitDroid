package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.GHRepositoryData
import com.example.gitdroid.models.domain.GHRepository

/**
 * Служебный класс для преобразования модели [GHRepositoryData] уровня data
 * в модель [GHRepository] уровня domain и обратно
 */
class GHRepositoryConverter {

    private val converter = UserConverter()

    /**
     * Преобразоване модели [GHRepositoryData] уровня data
     * в модель [GHRepository] уровня domain
     * @param ghRepositoryData
     * @return [GHRepository]
     */
    fun convert(ghRepositoryData: GHRepositoryData): GHRepository {
        return GHRepository(
            repoId = ghRepositoryData.id,
            repoName = ghRepositoryData.name,
            repoFullName = ghRepositoryData.fullName,
            repoOwner = converter.convert(ghRepositoryData.owner)
        )
    }

    /**
     * Преобразоване модели [GHRepository] уровня domain
     * в модель [GHRepositoryData] уровня data
     * @param ghRepository
     * @return [GHRepositoryData]
     */
    fun convert(ghRepository: GHRepository): GHRepositoryData {
        return GHRepositoryData(
            id = ghRepository.repoId,
            name = ghRepository.repoName,
            fullName = ghRepository.repoFullName,
            owner = converter.convert(ghRepository.repoOwner)
        )
    }
}