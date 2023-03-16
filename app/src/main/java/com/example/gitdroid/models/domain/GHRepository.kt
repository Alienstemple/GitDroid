package com.example.gitdroid.models.domain

/**
 * Репозиторий GitHub
 * @property repoId Идентифкатор
 * @property repoName Имя
 * @property repoFullName Полное назание
 * @property repoOwner Пользователь-влделец [User]
 */
data class GHRepository(
    val repoId: Long,
    val repoName: String,
    val repoFullName: String,
    val repoOwner: User,
)
