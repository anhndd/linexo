package vn.edu.hcmut.linexo.presentation.view.room;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.databinding.ActivityRoomBinding;
import vn.edu.hcmut.linexo.presentation.view.BaseActivity;
import vn.edu.hcmut.linexo.presentation.view.splash.SplashActivity;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel;
import vn.edu.hcmut.linexo.utils.Event;

public class RoomActivity extends BaseActivity {

    @Inject
    @Named("RoomViewModel")
    public ViewModel viewModel;

    ActivityRoomBinding binding;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.lstRoom.setLayoutManager(new GridLayoutManager(this, 2));
        binding.lstRoom.setHasFixedSize(true);
    }

    @Override
    public void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room);
        viewModel = (ViewModel) getLastCustomNonConfigurationInstance();
        if (viewModel == null) {
            SplashActivity.getAppComponent(this).inject(this);
        }
        binding.setViewModel((RoomViewModel)viewModel);
    }

    @Override
    public void onSubscribeViewModel() {
        viewModel.subscribeObserver(new Observer<Event>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Event event) {
                switch (event.getType()){
                    case Event.SHOW_LOGIN:{
                        Dialog loginDialog = new Dialog(RoomActivity.this);
                        loginDialog.setContentView(R.layout.popup_login);

                        Button btnGoogle = loginDialog.findViewById(R.id.btn_google);
                        btnGoogle.setOnClickListener(view -> {
                            loginGoogle();
                            loginDialog.dismiss();
                        });

                        Button btnFacebook = loginDialog.findViewById(R.id.btn_facebook);
                        btnFacebook.setOnClickListener(view -> {
                            loginFacebook();
                            loginDialog.dismiss();
                        });

                        loginDialog.show();
                        break;
                    }
                    case Event.SHOW_PLAY_ACTIVITY:{
                        int idRoom = (int) event.getData()[0];

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
        });
    }

    @Override
    public void onUnSubscribeViewModel() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public Object onSaveViewModel() {
        return null;
    }

    @Override
    public void onEndTaskViewModel() {

    }

    public void onClickAvatar(View view) {
        Context wrapper = new ContextThemeWrapper(this, R.style.AppTheme_PopupMenu);
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(wrapper, binding.avatar);

        ((ViewModelCallback) viewModel).onHelp(Event.create(Event.CLICK_AVATAR_POPUP_MENU,popup));
    }

    private void loginGoogle() {
        Toast.makeText(this,"google",Toast.LENGTH_SHORT).show();
    }

    private void loginFacebook() {
        Toast.makeText(this,"facebook",Toast.LENGTH_SHORT).show();
    }
}