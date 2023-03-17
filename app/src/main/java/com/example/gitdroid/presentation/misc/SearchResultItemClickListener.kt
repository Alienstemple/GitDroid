package com.example.gitdroid.presentation.misc

import com.example.gitdroid.models.domain.SearchResultItem

/**
 *  Слушатель нажатий для элемента списка результатов поиска
 */
interface SearchResultItemClickListener {
    /**
     * При нажатии на результат поиска открывается Chrome Custom Tab
     * по ссылке на GitHub-репозиторий
     * @param searchResultItem результат поиска
     */
    fun onItemClicked(searchResultItem: SearchResultItem)

    /**
     * При нажатии на кнопку "add to project" появляется диалог с возможностью
     * выбора, в какой проект поместить результат поиска
     * @param searchResultItem результат поиска
     */
    fun onAddToProjectClicked(searchResultItem: SearchResultItem)
}