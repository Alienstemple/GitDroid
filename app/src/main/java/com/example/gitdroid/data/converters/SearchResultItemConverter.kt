package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.SearchResultItemData
import com.example.gitdroid.models.domain.SearchResultItem

class SearchResultItemConverter {
    fun convert(searchResultItemData: SearchResultItemData): SearchResultItem {
        return SearchResultItem(
            fileName = searchResultItemData.name,
            filePath = searchResultItemData.path,
            htmlFileUrl = searchResultItemData.htmlUrl,
            ghRepository = GHRepositoryConverter().convert(searchResultItemData.repository),
            searchResScore = searchResultItemData.score
        )
    }

    fun convert(searchResultItem: SearchResultItem): SearchResultItemData {
        return SearchResultItemData(
            name = searchResultItem.fileName,
            path = searchResultItem.filePath,
            htmlUrl = searchResultItem.htmlFileUrl,
            repository = GHRepositoryConverter().convert(searchResultItem.ghRepository),  // TODO convert
            score = searchResultItem.searchResScore
        )
    }
}