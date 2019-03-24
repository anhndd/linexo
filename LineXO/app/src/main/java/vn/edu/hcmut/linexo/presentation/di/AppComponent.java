package vn.edu.hcmut.linexo.presentation.di;

import javax.inject.Singleton;
import dagger.Component;
import vn.edu.hcmut.linexo.presentation.view.play.PlayActivity;
import vn.edu.hcmut.linexo.presentation.view.room.RoomActivity;
import vn.edu.hcmut.linexo.presentation.view.splash.SplashActivity;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(SplashActivity splashActivity);
    void inject(RoomActivity roomActivity);
    void inject(PlayActivity playActivity);
}
