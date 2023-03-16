package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.GHRepositoryData
import com.example.gitdroid.models.domain.GHRepository

class GHRepositoryConverter {

    private val converter = UserConverter()
    fun convert(ghRepositoryData: GHRepositoryData): GHRepository {
        return GHRepository(
            repoId = ghRepositoryData.id,
            repoName = ghRepositoryData.name,
            repoFullName = ghRepositoryData.fullName,
            repoOwner = converter.convert(ghRepositoryData.owner)
        )
    }

    fun convert(ghRepository: GHRepository): GHRepositoryData {
        return GHRepositoryData (
            id = ghRepository.repoId,
            name = ghRepository.repoName,
            fullName = ghRepository.repoFullName,
            owner = converter.convert(ghRepository.repoOwner)
        )
    }
}