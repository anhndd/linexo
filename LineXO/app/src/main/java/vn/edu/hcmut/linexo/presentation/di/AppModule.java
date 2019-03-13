package vn.edu.hcmut.linexo.presentation.di;

import android.content.Context;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.splash.SplashViewModel;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    @Named("SplashViewModel")
    public ViewModel provideSplashViewModel() {
        return new SplashViewModel();
    }
}
