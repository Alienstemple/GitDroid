package com.example.gitdroid.models.data

import com.google.gson.annotations.SerializedName

data class SearchResultData(
    @SerializedName("total_count") val totalResultCount: Long,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val searchResultItems: List<SearchResultItemData>
)
