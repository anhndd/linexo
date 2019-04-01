package vn.edu.hcmut.linexo.presentation.view.play;

import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.databinding.ActivityPlayBinding;
import vn.edu.hcmut.linexo.presentation.view.BaseActivity;
import vn.edu.hcmut.linexo.presentation.view.splash.SplashActivity;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.KeyboardHeightObserver;
import vn.edu.hcmut.linexo.utils.KeyboardHeightProvider;

public class PlayActivity extends BaseActivity implements KeyboardHeightObserver {

    @Inject
    @Named("PlayViewModel")
    public ViewModel viewModel;

    ActivityPlayBinding binding;
    /** The keyboard height provider */
    private KeyboardHeightProvider keyboardHeightProvider;
    InputMethodManager imm;

    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.lstMessage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.lstMessage.setHasFixedSize(true);

        keyboardHeightProvider = new KeyboardHeightProvider(this);
        binding.root.post(new Runnable() {
            public void run() {
                keyboardHeightProvider.start();
            }
        });
    }

    @Override
    public void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play);
        viewModel = (ViewModel) getLastCustomNonConfigurationInstance();
        if (viewModel == null) {
            SplashActivity.getAppComponent(this).inject(this);
        }
        binding.setViewModel((PlayViewModel) viewModel);
    }

    @Override
    public void onSubscribeViewModel() {
        viewModel.subscribeObserver(new PlayObserver());
    }

    @Override
    public void onUnSubscribeViewModel() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public Object onSaveViewModel() {
        return viewModel;
    }

    @Override
    public void onEndTaskViewModel() {
        viewModel.endTask();
    }

    public class PlayObserver implements Observer<Event> {
        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(Event event) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    public void onClickSend(View view) {
        PlayViewModel playViewModel = (PlayViewModel) viewModel;
        playViewModel.onHelp(Event.create(2, binding.lstMessage));
    }

    private Rect oldRect;

    public void onClickBtnMessage(View view) {
        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {

        String or = orientation == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape";
        Log.i("Text", "onKeyboardHeightChanged in pixels: " + height + " " + or);
    }
}
