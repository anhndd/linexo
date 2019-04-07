package vn.edu.hcmut.linexo.presentation.view.room;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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
import vn.edu.hcmut.linexo.utils.Tool;

public class RoomActivity extends BaseActivity {

    @Inject
    @Named("RoomViewModel")
    public ViewModel viewModel;

    ActivityRoomBinding binding;

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

            }

            @Override
            public void onNext(Event event) {
                if(event.getType()==1) {
//                    viewModel.onHelp(event);
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
        Tool.forcePopupMenuShowIcon(popup);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_logout, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.it_rating:
                        break;
                    case R.id.it_logout:
                        ((ViewModelCallback) viewModel).onHelp(Event.create(Event.LOGOUT));
                        break;
                }
                return true;
            }
        });
        popup.show(); //showing popup menu
    }
}