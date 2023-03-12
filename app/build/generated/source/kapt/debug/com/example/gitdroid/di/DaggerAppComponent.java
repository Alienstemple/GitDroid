// Generated by Dagger (https://dagger.dev).
package com.example.gitdroid.di;

import android.content.Context;
import com.example.gitdroid.data.auth.SessionManager;
import com.example.gitdroid.data.room.ProjectsRoomRepository;
import com.example.gitdroid.data.search.NetworkService;
import com.example.gitdroid.domain.auth.AuthInteractor;
import com.example.gitdroid.domain.auth.AuthRepository;
import com.example.gitdroid.domain.projects.ProjectsFirebaseRepository;
import com.example.gitdroid.domain.projects.ProjectsInteractor;
import com.example.gitdroid.domain.search.GithubInteractor;
import com.example.gitdroid.domain.search.NetworkRepository;
import com.example.gitdroid.presentation.MainActivity;
import com.example.gitdroid.presentation.fragments.CodeSearchFragment;
import com.example.gitdroid.presentation.fragments.ProjectsFragment;
import com.example.gitdroid.presentation.vm.auth.AuthViewModelFactory;
import com.example.gitdroid.presentation.vm.projects.ProjectsViewModelFactory;
import com.example.gitdroid.presentation.vm.search.SearchResultViewModelFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerAppComponent {
  private DaggerAppComponent() {
  }

  public static AppComponent.Factory factory() {
    return new Factory();
  }

  private static final class Factory implements AppComponent.Factory {
    @Override
    public AppComponent create(Context context) {
      Preconditions.checkNotNull(context);
      return new AppComponentImpl(new ProjectsModule(), new SearchResultModule(), new AuthModule(), context);
    }
  }

  private static final class AppComponentImpl implements AppComponent {
    private final AppComponentImpl appComponentImpl = this;

    private Provider<Context> contextProvider;

    private Provider<AuthRepository> providesAuthRepositoryProvider;

    private Provider<AuthInteractor> providesAuthInteractorProvider;

    private Provider<ProjectsFirebaseRepository> providesProjectsFirebaseRepositoryProvider;

    private Provider<ProjectsRoomRepository> providesProjectsRoomRepositoryProvider;

    private Provider<ProjectsInteractor> providesProjectInteractorProvider;

    private Provider<SessionManager> providesSessionManagerProvider;

    private Provider<NetworkService> providesNetworkServiceProvider;

    private Provider<NetworkRepository> providesNetworkRepositoryProvider;

    private Provider<GithubInteractor> providesGithubInteractorProvider;

    private AppComponentImpl(ProjectsModule projectsModuleParam,
        SearchResultModule searchResultModuleParam, AuthModule authModuleParam,
        Context contextParam) {

      initialize(projectsModuleParam, searchResultModuleParam, authModuleParam, contextParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ProjectsModule projectsModuleParam,
        final SearchResultModule searchResultModuleParam, final AuthModule authModuleParam,
        final Context contextParam) {
      this.contextProvider = InstanceFactory.create(contextParam);
      this.providesAuthRepositoryProvider = DoubleCheck.provider(AuthModule_ProvidesAuthRepositoryFactory.create(authModuleParam, contextProvider));
      this.providesAuthInteractorProvider = DoubleCheck.provider(AuthModule_ProvidesAuthInteractorFactory.create(authModuleParam, providesAuthRepositoryProvider));
      this.providesProjectsFirebaseRepositoryProvider = DoubleCheck.provider(ProjectsModule_ProvidesProjectsFirebaseRepositoryFactory.create(projectsModuleParam));
      this.providesProjectsRoomRepositoryProvider = DoubleCheck.provider(ProjectsModule_ProvidesProjectsRoomRepositoryFactory.create(projectsModuleParam, contextProvider));
      this.providesProjectInteractorProvider = DoubleCheck.provider(ProjectsModule_ProvidesProjectInteractorFactory.create(projectsModuleParam, providesProjectsFirebaseRepositoryProvider, providesProjectsRoomRepositoryProvider));
      this.providesSessionManagerProvider = DoubleCheck.provider(SearchResultModule_ProvidesSessionManagerFactory.create(searchResultModuleParam, contextProvider));
      this.providesNetworkServiceProvider = DoubleCheck.provider(SearchResultModule_ProvidesNetworkServiceFactory.create(searchResultModuleParam, providesSessionManagerProvider));
      this.providesNetworkRepositoryProvider = DoubleCheck.provider(SearchResultModule_ProvidesNetworkRepositoryFactory.create(searchResultModuleParam, providesNetworkServiceProvider));
      this.providesGithubInteractorProvider = DoubleCheck.provider(SearchResultModule_ProvidesGithubInteractorFactory.create(searchResultModuleParam, providesNetworkRepositoryProvider));
    }

    @Override
    public AuthViewModelFactory authViewModelFactory() {
      return new AuthViewModelFactory(providesAuthInteractorProvider.get());
    }

    @Override
    public ProjectsViewModelFactory projectsViewModelFactory() {
      return new ProjectsViewModelFactory(providesProjectInteractorProvider.get());
    }

    @Override
    public SearchResultViewModelFactory searchResultViewModelFactory() {
      return new SearchResultViewModelFactory(providesGithubInteractorProvider.get());
    }

    @Override
    public void inject(MainActivity activity) {
    }

    @Override
    public void inject(CodeSearchFragment codeSearchFragment) {
    }

    @Override
    public void inject(ProjectsFragment projectsFragment) {
    }
  }
}
