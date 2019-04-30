package vn.edu.hcmut.linexo.presentation.view.play;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.databinding.ActivityPlayBinding;
import vn.edu.hcmut.linexo.presentation.view.BaseActivity;
import vn.edu.hcmut.linexo.presentation.view.room.RoomActivity;
import vn.edu.hcmut.linexo.presentation.view.splash.SplashActivity;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.KeyboardHeightObserver;
import vn.edu.hcmut.linexo.utils.KeyboardHeightProvider;
import vn.edu.hcmut.linexo.utils.Tool;

public class PlayActivity extends BaseActivity implements KeyboardHeightObserver {

    @Inject
    @Named("PlayViewModel")
    public ViewModel viewModel;

    ActivityPlayBinding binding;
    /**
     * The keyboard height provider
     */
    private KeyboardHeightProvider keyboardHeightProvider;

    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int idRoom = (int) extras.get("idRoom");
            ((PlayViewModel) viewModel).onHelp(Event.create(Event.LOAD_PLAY_INFO, idRoom));
        }

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.lstMessage.setLayoutManager(linearLayoutManager);
        binding.lstMessage.setHasFixedSize(true);

        keyboardHeightProvider = new KeyboardHeightProvider(this);
        binding.root.post(new Runnable() {
            public void run() {
                keyboardHeightProvider.start();
            }
        });

        addControlKeyboardView(binding.edtMessage);
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
            switch (event.getType()) {
                case Event.SMOOTH_MESSAGE_LIST: {
                    int count = (int) event.getData()[0];
                    binding.lstMessage.smoothScrollToPosition(count);
                    break;
                }
                case Event.COUNT_DOWN: {
                    int count = (int) event.getData()[0];
                    Toast.makeText(PlayActivity.this, count + "",Toast.LENGTH_SHORT).show();
                    break;
                }
                case Event.RESULT:{
                    String state = "";
                    switch ((int) event.getData()[0]){
                        case Event.WIN:
                            state = "Win";
                            break;
                        case Event.LOSE:
                            state = "Lose";
                            break;
                        case Event.DRAW:
                            state = "Draw";
                            break;
                    }
                    Toast.makeText(PlayActivity.this,state,Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    public void onClickBtnMessage(View view) {
        binding.edtMessage.setVisibility(View.VISIBLE);
        Tool.showSoftKeyboard(binding.edtMessage, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
        Tool.hideSoftKeyboard(this);
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
        keyboardHeightProvider.stop();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, RoomActivity.class));
        super.onBackPressed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        int[] array = {height, orientation};
        ((PlayViewModel) viewModel).onHelp(Event.create(Event.KEYBOARD_CHANGED, array));
        String or = orientation == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape";
        Log.i("Text", "onKeyboardHeightChanged in pixels: " + height + " " + or);
    }
}
