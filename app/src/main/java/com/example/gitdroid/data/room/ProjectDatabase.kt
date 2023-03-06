package com.example.gitdroid.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gitdroid.models.domain.Project

@Database(entities = [Project::class], version = 1, exportSchema = false)
abstract class ProjectDatabase: RoomDatabase() {
    abstract fun projectDao(): ProjectDao

    companion object {

        @Volatile
        private var INSTANCE: ProjectDatabase? = null

        fun getDatabaseClient(context: Context): ProjectDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, ProjectDatabase::class.java, "PROJECT_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }
    }
}