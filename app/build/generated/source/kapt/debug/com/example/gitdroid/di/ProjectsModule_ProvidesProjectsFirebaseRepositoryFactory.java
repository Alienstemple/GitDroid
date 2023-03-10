// Generated by Dagger (https://dagger.dev).
package com.example.gitdroid.di;

import com.example.gitdroid.domain.projects.ProjectsFirebaseRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ProjectsModule_ProvidesProjectsFirebaseRepositoryFactory implements Factory<ProjectsFirebaseRepository> {
  private final ProjectsModule module;

  public ProjectsModule_ProvidesProjectsFirebaseRepositoryFactory(ProjectsModule module) {
    this.module = module;
  }

  @Override
  public ProjectsFirebaseRepository get() {
    return providesProjectsFirebaseRepository(module);
  }

  public static ProjectsModule_ProvidesProjectsFirebaseRepositoryFactory create(
      ProjectsModule module) {
    return new ProjectsModule_ProvidesProjectsFirebaseRepositoryFactory(module);
  }

  public static ProjectsFirebaseRepository providesProjectsFirebaseRepository(
      ProjectsModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.providesProjectsFirebaseRepository());
  }
}
