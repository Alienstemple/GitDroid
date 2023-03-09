package com.example.gitdroid.di

import com.example.gitdroid.data.room.ProjectDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GeneralModule {
//    @Provides
//    @Singleton
//    fun providesInteractor(
//        tickerFileRepository: TickerFileRepository,
//        tickerNetworkRepository: TickerNetworkRepository,
//    ): TickerInteractor {
//        return TickerInteractorImpl(tickerNetworkRepository, tickerFileRepository)
//    }

//    val dao = ProjectDatabase.getDatabaseClient(this).projectDao()
//    projectsFirebaseRepository = ProjectsFirebaseRepositoryImpl()
//    projectsRoomRepository = ProjectsRoomRepository(dao)
//    projectsInteractor =
//    ProjectsInteractorImpl(projectsFirebaseRepository as ProjectsFirebaseRepository,
//    projectsRoomRepository as ProjectsRoomRepository)

}