package com.example.gitdroid.data.converters

import com.example.gitdroid.models.data.SearchResultItemData
import com.example.gitdroid.models.domain.SearchResultItem

/**
 * Служебный класс для преобразования модели [SearchResultItemData] уровня data
 * в модель [SearchResultItem] уровня domain
 */
class SearchResultItemConverter {

    private val converter = GHRepositoryConverter()

    /**
     * Преобразоване модели [SearchResultItemData] уровня data
     * в модель [SearchResultItem] уровня domain
     * @param searchResultItemData
     * @return [SearchResultItem]
     */
    fun convert(searchResultItemData: SearchResultItemData): SearchResultItem {
        return SearchResultItem(
            fileName = searchResultItemData.name,
            filePath = searchResultItemData.path,
            htmlFileUrl = searchResultItemData.htmlUrl,
            ghRepository = converter.convert(searchResultItemData.repository),
            searchResScore = searchResultItemData.score
        )
    }

    /**
     * Преобразоване модели [SearchResultItem] уровня domain
     * в модель [SearchResultItemData] уровня data
     * @param searchResultItem
     * @return [SearchResultItemData]
     */
    fun convert(searchResultItem: SearchResultItem): SearchResultItemData {
        return SearchResultItemData(
            name = searchResultItem.fileName,
            path = searchResultItem.filePath,
            htmlUrl = searchResultItem.htmlFileUrl,
            repository = converter.convert(searchResultItem.ghRepository),
            score = searchResultItem.searchResScore
        )
    }
}