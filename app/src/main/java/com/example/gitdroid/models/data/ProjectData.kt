package com.example.gitdroid.models.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectData(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: String = "",
    @NonNull
    @ColumnInfo(name = "name")
    var name: String = "",
    @Ignore
    var searchResList: List<SearchResultItemData> = emptyList()  // TODO как хранить search res в бд?
)
