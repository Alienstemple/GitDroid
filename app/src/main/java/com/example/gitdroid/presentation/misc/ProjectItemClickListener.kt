package com.example.gitdroid.presentation.misc

import com.example.gitdroid.models.domain.Project

/**
 *  Слушатель нажатий для элемента списка проектов
 */
interface ProjectItemClickListener {
    /**
     * Раскрывает список всех добавленных в проект результатов поиска
     */
    fun onItemClicked(project: Project) {}

    /**
     * Удаление проекта
     */
    fun onDeleteClicked(project: Project) {}

    /**
     * Отправка проекта на почту
     */
    fun onShareClicked(project: Project) {}
}