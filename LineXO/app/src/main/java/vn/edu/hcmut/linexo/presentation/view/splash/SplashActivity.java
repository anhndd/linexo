package vn.edu.hcmut.linexo.presentation.view.splash;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.databinding.ActivitySplashBinding;
import vn.edu.hcmut.linexo.presentation.di.AppComponent;
import vn.edu.hcmut.linexo.presentation.di.AppModule;
import vn.edu.hcmut.linexo.presentation.di.DaggerAppComponent;
import vn.edu.hcmut.linexo.presentation.view.BaseActivity;
import vn.edu.hcmut.linexo.presentation.view.play.PlayActivity;
import vn.edu.hcmut.linexo.presentation.view.room.RoomActivity;
import vn.edu.hcmut.linexo.presentation.view_model.splash.SplashViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;

public class SplashActivity extends BaseActivity {

    @Inject
    @Named("SplashViewModel")
    public ViewModel viewModel;

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnimationDrawable animationDrawable = (AnimationDrawable) binding.loading.getBackground();
        animationDrawable.start();
    }

    @Override
    public void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        viewModel = (ViewModel) getLastCustomNonConfigurationInstance();
        if (viewModel == null) {
            SplashActivity.getAppComponent(this).inject(this);
        }
        binding.setViewModel((SplashViewModel) viewModel);
    }

    @Override
    public void onSubscribeViewModel() {
        viewModel.subscribeObserver(new EndActivityObserver());
    }

    @Override
    public void onUnSubscribeViewModel() {

    }

    @Override
    public Object onSaveViewModel() {
        return null;
    }

    @Override
    public void onEndTaskViewModel() {

    }

    /**
     * A Singleton {@code AppComponent} is used to inject object to other activity.
     */
    private static AppComponent appComponent;

    /**
     * create a new {@code AppComponent} if it does not exist.
     *
     * @param context parameter is used create a new {@code AppComponent}.
     * @return {@code AppComponent}.
     */
    public static AppComponent getAppComponent(Context context) {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                    .builder()
                    .appModule(new AppModule(context))
                    .build();
        }
        return appComponent;
    }

    class EndActivityObserver implements Observer {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Object o) {
            startActivity(new Intent(SplashActivity.this, RoomActivity.class));
            SplashActivity.this.finish();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
