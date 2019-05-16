package vn.edu.hcmut.linexo.presentation.view.play;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
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
    Dialog countDialog;
    Dialog endGameDialog;
    Handler endGameHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String idRoom = (String) extras.get("idRoom");
            ((PlayViewModel) viewModel).onHelp(Event.create(Event.LOAD_PLAY_INFO, idRoom));
        }

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        binding.lstMessage.setLayoutManager(linearLayoutManager);
        binding.lstMessage.setHasFixedSize(true);

        keyboardHeightProvider = new KeyboardHeightProvider(this);
        binding.root.post(() -> keyboardHeightProvider.start());

        addControlKeyboardView(binding.edtMessage);

        countDialog = new Dialog(PlayActivity.this);
        countDialog.setContentView(R.layout.layout_count_view);
        countDialog.setCancelable(true);
        countDialog.setCanceledOnTouchOutside(false);
        countDialog.setOnCancelListener(dialog -> {
            onBackPressed();
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
            switch (event.getType()) {
                case Event.SMOOTH_MESSAGE_LIST: {
                    int count = (int) event.getData()[0];
                    binding.lstMessage.smoothScrollToPosition(count);
                    break;
                }
                case Event.COUNT_DOWN: {
                    int count = (int) event.getData()[0];
                    if (count != 0) {
                        TextView txtCountStart = (countDialog.findViewById(R.id.txt_count_start));
                        txtCountStart.setText(count + "");
                        txtCountStart.setTextSize(getResources().getDisplayMetrics().widthPixels / 30);
                        txtCountStart.setWidth(getResources().getDisplayMetrics().widthPixels / 4);
                        txtCountStart.setHeight(getResources().getDisplayMetrics().widthPixels / 4);
                        countDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        countDialog.getWindow().setLayout(getResources().getDisplayMetrics().widthPixels / 6, getResources().getDisplayMetrics().widthPixels / 6);
                        countDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        if(!countDialog.isShowing())
                            countDialog.show();
                    } else {
                        countDialog.dismiss();
                    }
                    break;
                }
                case Event.RESULT: {
                    endGameDialog = new Dialog(PlayActivity.this);
                    endGameDialog.setContentView(R.layout.layout_end_game);
                    endGameDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    endGameDialog.setCancelable(true);
                    endGameDialog.setCanceledOnTouchOutside(false);
                    endGameDialog.setOnCancelListener(dialog -> {
                        if (endGameHandler != null) {
                            endGameHandler.removeCallbacksAndMessages(null);
                        }
                        onBackPressed();
                    });
                    switch ((int) event.getData()[0]) {
                        case Event.WIN: {
                            View view = endGameDialog.findViewById(R.id.endgame_status);
                            view.setBackgroundResource((R.drawable.ic_cup_win));
                            endGameDialog.show();
                            break;

                        }
                        case Event.LOSE: {
                            View view = endGameDialog.findViewById(R.id.endgame_status);
                            view.setBackgroundResource((R.drawable.ic_cup_lose));
                            endGameDialog.show();
                            break;
                        }
                        case Event.DRAW: {
                            View view = endGameDialog.findViewById(R.id.endgame_status);
                            view.setBackgroundResource((R.drawable.ic_cup_draw));
                            endGameDialog.show();
                            break;
                        }
                    }
                    TextView txtTime = endGameDialog.findViewById(R.id.txt_time);
                    endGameHandler = new Handler();
                    for (int i = 10; i >= 0; --i) {
                        int numCount = i;
                        endGameHandler.postDelayed(
                                () -> {
                                    if (numCount != 0) {
                                        txtTime.setText("Ván đấu tiếp theo sẽ bắt đầu sau " + numCount + " giây");
                                    } else {
                                        endGameDialog.dismiss();
                                    }
                                },
                                (10 - numCount) * 1000
                        );
                    }
                    break;
                }
                case Event.SHOW_KEYBOARD:{
                    binding.edtMessage.setVisibility(View.VISIBLE);
                    Tool.showSoftKeyboard(binding.edtMessage, PlayActivity.this);
                    break;
                }
                case Event.TOAST_USER_WIN:{
                    String mess = (String) event.getData()[0];
                    Toast.makeText(PlayActivity.this,mess,Toast.LENGTH_LONG).show();
                    break;
                }
                case Event.OUT_ROOM:{
                    if (countDialog != null && countDialog.isShowing()) {
                        countDialog.dismiss();
                    }
                    if (endGameDialog != null && endGameDialog.isShowing()) {
                        if (endGameHandler != null) {
                            endGameHandler.removeCallbacksAndMessages(null);
                        }
                        endGameDialog.dismiss();
                    }
                    startActivity(new Intent(PlayActivity.this, RoomActivity.class));
                    finish();
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
        ((PlayViewModel) viewModel).onHelp(Event.create(Event.LEAVE_ROOM));
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
