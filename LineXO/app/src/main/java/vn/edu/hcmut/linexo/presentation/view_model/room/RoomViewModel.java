package vn.edu.hcmut.linexo.presentation.view_model.room;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.BR;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.domain.interactor.Usecase;
import vn.edu.hcmut.linexo.presentation.view.room.RoomItem;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.presentation.view.play.PlayActivity;
import vn.edu.hcmut.linexo.presentation.view.room.RoomRecyclerViewAdapter;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.NetworkChangeReceiver;
import vn.edu.hcmut.linexo.utils.Optional;
import vn.edu.hcmut.linexo.utils.Tool;

public class RoomViewModel extends BaseObservable implements ViewModel, ViewModelCallback {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private String strSearch = "";
    private RoomRecyclerViewAdapter adapter = new RoomRecyclerViewAdapter(new ArrayList<>(), this);
    private User user;
    private List<RoomItem> data = new ArrayList<>();
    ;
    private Context context;
    private Usecase roomUsecase;
    private boolean isConnected;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    int i = 1;

    public RoomViewModel(Context context, Usecase roomUsecase) {

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            User user = new User(firebaseUser.getUid(), firebaseUser.getEmail(),
                    firebaseUser.getPhotoUrl().toString(), firebaseUser.getDisplayName(), 0, System.currentTimeMillis());
            onHelp(Event.create(Event.LOGIN_USER, user));
        }
        this.context = context;

        this.roomUsecase = roomUsecase;
        networkChangeReceiver.initReceiver(context, new NetworkChangeReceiver.NetworkChangeListener() {
            @Override
            public void onNetworkChange(boolean networkState) {
                isConnected = networkState;
                notifyPropertyChanged(BR.networkVisibility);
                if (data.size() < 2) // if list room is empty or just contain AI room
                    roomUsecase.execute(new LoadListRoomObserver(), Event.LOAD_LIST_ROOM, isConnected);
            }
        });
        // create view list room

        adapter = new RoomRecyclerViewAdapter(data, this);
        data = new ArrayList<>(data);

    }

    @Override
    public void subscribeObserver(Observer<Event> observer) {
        publisher.subscribe(observer);

        roomUsecase.execute(new UserInfoObserver(), Event.LOGIN_INFO);
    }

    @Override
    public void onHelp(Event e) {
        switch (e.getType()) {
            case Event.CLICK_ROOM: {
                Object[] data = e.getData();
                int roomId = (int) data[0];
//                Log.e("test222",roomId+"");
                if (roomId == 0 || user != null) {
                    publisher.onNext(Event.create(Event.SHOW_PLAY_ACTIVITY, roomId));
                } else {
                    publisher.onNext(Event.create(Event.SHOW_LOGIN));
                }
                break;
            }
            case Event.LOAD_LIST_ROOM: {
                data = new ArrayList<>((List<RoomItem>) e.getData()[0]);
                if (data == null) return;
                if (!strSearch.isEmpty()) {
                    ArrayList<RoomItem> filteredList = new ArrayList<>();
                    for (RoomItem item : data) {
                        int number = item.getId();
                        String strNumber = "";
                        if (number == 0) {
//                        strNumber = getResources().getString(R.string.room_ai);
                        } else if (number < 10) {
                            strNumber = "00" + number;
                        } else if (number < 100) {
                            strNumber = "0" + number;
                        } else if (number < 1000) {
                            strNumber += number;
                        }
                        if (strNumber.toLowerCase().contains(strSearch.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    adapter.updateRoomListItems(filteredList);
                } else {
                    adapter.updateRoomListItems(data);
                }
                break;
            }
            case Event.LOGIN_USER: {
                user = (User) e.getData()[0];
                notifyPropertyChanged(BR.urlAvatar);
                notifyPropertyChanged(BR.userName);
                notifyPropertyChanged(BR.userVisibility);
                notifyPropertyChanged(BR.score);
                break;

            }
            case Event.LOGOUT: {
                mAuth.signOut();
                roomUsecase.execute(null, Event.LOGOUT);
                break;
            }
        }
    }

    public void setStrSearch(String strSearch) {
        this.strSearch = strSearch;

        ArrayList<RoomItem> filteredList = new ArrayList<>();
        for (RoomItem item : data) {
            int number = item.getId();
            String strNumber = "";
            if (number == 0) {
//                strNumber = getResources().getString(R.string.room_ai);
            } else if (number < 10) {
                strNumber = "00" + number;
            } else if (number < 100) {
                strNumber = "0" + number;
            } else if (number < 1000) {
                strNumber += number;
            }
            if (strNumber.toLowerCase().contains(strSearch.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.updateRoomListItems(filteredList);
    }

    @Bindable
    public String getStrSearch() {
        return strSearch;
    }

    @Bindable
    public RoomRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RoomRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Bindable
    public Object getUrlAvatar() {
        if (user == null)
            return R.drawable.ic_logo_round;
        return user.getAvatar();
    }

    public void onClickCreateRoom(View view) {
        Intent intent = new Intent(view.getContext(), PlayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        view.getContext().startActivity(intent);
    }

    @Bindable
    public String getScore() {
        if (user == null) {
            return "";
        }
        else if(user.getScore() == -1){
            return "-";
        }

        return String.valueOf(user.getScore());
    }

    @Bindable
    public String getUserName() {
        if (user == null) {
            return context.getString(R.string.app_name);
        }
        return user.getName();
    }

    @Bindable
    public int getUserVisibility() {
        if (user == null)
            return View.GONE;
        return View.VISIBLE;
    }

    @Bindable
    public int getNetworkVisibility() {
        if (isConnected) {
            return View.GONE;
        }
        return View.VISIBLE;
    }

    @Override
    public void endTask() {

    }

    public void onClickAvatar(View view) {
        if (user != null) {
            publisher.onNext(Event.create(Event.SHOW_POPUP_USER_ON));
        } else {
            publisher.onNext(Event.create(Event.SHOW_POPUP_USER_OFF));
        }
    }

    class UserInfoObserver extends DisposableSingleObserver<Optional<User>> {
        @Override
        public void onSuccess(Optional<User> userOptional) {
            if (userOptional.isPresent()) {
                user = userOptional.get();
                notifyPropertyChanged(BR.urlAvatar);
                notifyPropertyChanged(BR.userName);
                notifyPropertyChanged(BR.userVisibility);
                notifyPropertyChanged(BR.score);
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    }

    class LoadListRoomObserver extends DisposableObserver<List<RoomItem>> {

        @Override
        public void onNext(List<RoomItem> list) {
            onHelp(Event.create(Event.LOAD_LIST_ROOM, list));
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    class FullUserInfoObserver extends DisposableSingleObserver<Optional<User>> {
        @Override
        public void onSuccess(Optional<User> userOptional) {
            if (userOptional.isPresent()) {
                user = userOptional.get();
                notifyPropertyChanged(BR.urlAvatar);
                notifyPropertyChanged(BR.userName);
                notifyPropertyChanged(BR.userVisibility);
                notifyPropertyChanged(BR.score);
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    }
}