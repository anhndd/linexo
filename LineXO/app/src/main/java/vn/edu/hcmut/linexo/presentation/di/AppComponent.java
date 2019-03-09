package vn.edu.hcmut.linexo.presentation.di;

import javax.inject.Singleton;
import dagger.Component;
import vn.edu.hcmut.linexo.presentation.view.SplashActivity;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(SplashActivity splashActivity);
}
