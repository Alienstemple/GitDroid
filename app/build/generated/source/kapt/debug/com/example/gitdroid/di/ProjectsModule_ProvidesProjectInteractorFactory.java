// Generated by Dagger (https://dagger.dev).
package com.example.gitdroid.di;

import com.example.gitdroid.data.room.ProjectsRoomRepository;
import com.example.gitdroid.domain.projects.ProjectsFirebaseRepository;
import com.example.gitdroid.domain.projects.ProjectsInteractor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ProjectsModule_ProvidesProjectInteractorFactory implements Factory<ProjectsInteractor> {
  private final ProjectsModule module;

  private final Provider<ProjectsFirebaseRepository> projectsFirebaseRepositoryProvider;

  private final Provider<ProjectsRoomRepository> projectsRoomRepositoryProvider;

  public ProjectsModule_ProvidesProjectInteractorFactory(ProjectsModule module,
      Provider<ProjectsFirebaseRepository> projectsFirebaseRepositoryProvider,
      Provider<ProjectsRoomRepository> projectsRoomRepositoryProvider) {
    this.module = module;
    this.projectsFirebaseRepositoryProvider = projectsFirebaseRepositoryProvider;
    this.projectsRoomRepositoryProvider = projectsRoomRepositoryProvider;
  }

  @Override
  public ProjectsInteractor get() {
    return providesProjectInteractor(module, projectsFirebaseRepositoryProvider.get(), projectsRoomRepositoryProvider.get());
  }

  public static ProjectsModule_ProvidesProjectInteractorFactory create(ProjectsModule module,
      Provider<ProjectsFirebaseRepository> projectsFirebaseRepositoryProvider,
      Provider<ProjectsRoomRepository> projectsRoomRepositoryProvider) {
    return new ProjectsModule_ProvidesProjectInteractorFactory(module, projectsFirebaseRepositoryProvider, projectsRoomRepositoryProvider);
  }

  public static ProjectsInteractor providesProjectInteractor(ProjectsModule instance,
      ProjectsFirebaseRepository projectsFirebaseRepository,
      ProjectsRoomRepository projectsRoomRepository) {
    return Preconditions.checkNotNullFromProvides(instance.providesProjectInteractor(projectsFirebaseRepository, projectsRoomRepository));
  }
}
