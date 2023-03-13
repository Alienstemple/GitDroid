package com.example.gitdroid.models.domain

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var projectId: String = "",
    @NonNull
    @ColumnInfo(name = "name")
    var projectName: String = "",
//    val searchResList: List<String> = emptyList()  // TODO search results
    @Ignore
    var searchResList: List<SearchResultItem> = emptyList()  // TODO как хранить search res в бд?
)
