// Generated by Dagger (https://google.github.io/dagger).
package vn.edu.hcmut.linexo.presentation.di;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import vn.edu.hcmut.linexo.domain.interactor.Usecase;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;

public final class AppModule_ProvideRoomViewModelFactory implements Factory<ViewModel> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  private final Provider<Usecase> roomUsecaseProvider;

  public AppModule_ProvideRoomViewModelFactory(
      AppModule module, Provider<Context> contextProvider, Provider<Usecase> roomUsecaseProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
    this.roomUsecaseProvider = roomUsecaseProvider;
  }

  @Override
  public ViewModel get() {
    return provideInstance(module, contextProvider, roomUsecaseProvider);
  }

  public static ViewModel provideInstance(
      AppModule module, Provider<Context> contextProvider, Provider<Usecase> roomUsecaseProvider) {
    return proxyProvideRoomViewModel(module, contextProvider.get(), roomUsecaseProvider.get());
  }

  public static AppModule_ProvideRoomViewModelFactory create(
      AppModule module, Provider<Context> contextProvider, Provider<Usecase> roomUsecaseProvider) {
    return new AppModule_ProvideRoomViewModelFactory(module, contextProvider, roomUsecaseProvider);
  }

  public static ViewModel proxyProvideRoomViewModel(
      AppModule instance, Context context, Usecase roomUsecase) {
    return Preconditions.checkNotNull(
        instance.provideRoomViewModel(context, roomUsecase),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
