package com.example.gitdroid.models.domain

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: String = "",
    @NonNull
    @ColumnInfo(name = "name")
    var name: String = "",
//    val searchResList: List<String> = emptyList()  // TODO search results
    var searchResList: List<SearchResultItem> = emptyList()  // TODO как хранить search res в бд?
)
