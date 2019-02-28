package vn.edu.hcmut.linexo.presentation.di;

import javax.inject.Singleton;
import dagger.Component;
import vn.edu.hcmut.linexo.presentation.view.WelcomeActivity;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(WelcomeActivity welcomeActivity);
}
