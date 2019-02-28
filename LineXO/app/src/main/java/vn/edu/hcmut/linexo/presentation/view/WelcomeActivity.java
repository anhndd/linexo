package vn.edu.hcmut.linexo.presentation.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import javax.inject.Inject;
import javax.inject.Named;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.databinding.ActivityWelcomeBinding;
import vn.edu.hcmut.linexo.presentation.di.AppComponent;
import vn.edu.hcmut.linexo.presentation.di.AppModule;
import vn.edu.hcmut.linexo.presentation.di.DaggerAppComponent;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.WelcomeViewModel;

public class WelcomeActivity extends BaseActivity {

    @Inject
    @Named("WelcomeViewModel")
    public ViewModel viewModel;

    ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        viewModel = (ViewModel) getLastCustomNonConfigurationInstance();
        if (viewModel == null) {
            WelcomeActivity.getAppComponent(this).inject(this);
        }
        binding.setViewModel((WelcomeViewModel)viewModel);
    }

    @Override
    public void onSubscribeViewModel() {

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
}
