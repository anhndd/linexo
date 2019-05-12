package vn.edu.hcmut.linexo.presentation.view_model.room;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.BR;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.domain.interactor.Usecase;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.view.room.RankItem;
import vn.edu.hcmut.linexo.presentation.view.room.RoomItem;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.presentation.view.room.RoomRecyclerViewAdapter;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.NetworkChangeReceiver;
import vn.edu.hcmut.linexo.utils.Optional;

public class RoomViewModel extends BaseObservable implements ViewModel, ViewModelCallback {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private String strSearch = "";
    private RoomRecyclerViewAdapter adapter = new RoomRecyclerViewAdapter(new ArrayList<>(), this);
    private User user;
    private List<RoomItem> data = new ArrayList<>();

    private Context context;
    private Usecase roomUsecase;
    private boolean isConnected;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    int i = 1;

    public RoomViewModel(Context context, Usecase roomUsecase) {

        this.context = context;

        this.roomUsecase = roomUsecase;

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            user = new User(firebaseUser.getUid(), firebaseUser.getEmail(),
                    firebaseUser.getPhotoUrl().toString(), firebaseUser.getDisplayName(), -1, System.currentTimeMillis());
            onHelp(Event.create(Event.LOGIN_INFO, user));
        }

        data = new ArrayList<>();
        RoomItem roomItem = new RoomItem("AI", 0, "LineXOAI", null, false);
        data.add(roomItem);
        adapter.updateRoomListItems(data);

        networkChangeReceiver.initReceiver(context, new NetworkChangeReceiver.NetworkChangeListener() {
            @Override
            public void onNetworkChange(boolean networkState) {
                isConnected = networkState;
                notifyPropertyChanged(BR.networkVisibility);

                if (data.size() < 2) { // if list room is empty or just contain AI room
                    if (!isConnected)
                        publisher.onNext(Event.create(Event.TOAST_NO_CONNECTION));
                    roomUsecase.execute(new LoadListRoomObserver(), Event.LOAD_LIST_ROOM, isConnected);
                }
            }
        });
        // create view list room

        adapter = new RoomRecyclerViewAdapter(data, this);
    }

    @Override
    public void subscribeObserver(Observer<Event> observer) {
        publisher.subscribe(observer);
    }

    @Override
    public void onHelp(Event e) {
        switch (e.getType()) {
            case Event.CLICK_ROOM: {
                Object[] data = e.getData();
                String roomId = (String) data[0];
                if (roomId.equals("AI")) {
                    publisher.onNext(Event.create(Event.SHOW_PLAY_ACTIVITY, roomId));
                } else if (user != null && user.getScore() != -1) {
                    if (!isConnected) {
                        publisher.onNext(Event.create(Event.TOAST_NO_CONNECTION));
                    } else {
                        publisher.onNext(Event.create(Event.SHOW_PLAY_ACTIVITY, roomId));
                    }
                } else {
                    publisher.onNext(Event.create(Event.SHOW_LOGIN));
                }
            }
            break;
            case Event.LOAD_LIST_ROOM: {
                data = new ArrayList<>((List<RoomItem>) e.getData()[0]);
                RoomItem roomItem = new RoomItem("AI", 0, "LineXOAI", null, false);
                data.add(0,roomItem);
                Collections.sort(data, new RoomComparator());
                if (!strSearch.isEmpty()) {
                    ArrayList<RoomItem> filteredList = new ArrayList<>();
                    for (RoomItem item : data) {
                        int number = item.getRoomNumber();
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
                roomUsecase.execute(new LoginObserver(), Event.LOGIN_USER, user);
                break;

            }
            case Event.LOGOUT: {
                user = null;
                notifyPropertyChanged(BR.urlAvatar);
                notifyPropertyChanged(BR.userName);
                notifyPropertyChanged(BR.userVisibility);
                notifyPropertyChanged(BR.score);
                mAuth.signOut();
                roomUsecase.execute(null, Event.LOGOUT);
                break;
            }
            case Event.SHOW_RANK_DIALOG: {
                if (!isConnected) {
                    publisher.onNext(Event.create(Event.TOAST_NO_CONNECTION));
                } else {
                    roomUsecase.execute(new RankScoreObserver(), Event.SHOW_RANK_DIALOG);
                }
                break;
            }
            case Event.LOGIN_INFO: {
                roomUsecase.execute(new LoginInfoObserver(), Event.LOGIN_INFO, user.getUid(), isConnected);
                break;
            }
            case Event.SHOW_LOGIN: {
                publisher.onNext(Event.create(Event.SHOW_LOGIN));
                break;
            }
        }
    }

    public void setStrSearch(String strSearch) {
        this.strSearch = strSearch;

        ArrayList<RoomItem> filteredList = new ArrayList<>();
        for (RoomItem item : data) {
            int number = item.getRoomNumber();
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
        if (!isConnected) {
            publisher.onNext(Event.create(Event.TOAST_NO_CONNECTION));
        } else if (user == null) {
            publisher.onNext(Event.create(Event.SHOW_LOGIN));
        } else {
            Room room = new Room(null, Room.CREATE, user, System.currentTimeMillis());
            roomUsecase.execute(new CreateRoomObserver(), Event.CREATE_ROOM, room);
        }
    }

    @Bindable
    public String getScore() {
        if (user == null) {
            return "";
        } else if (user.getScore() == -1) {
            roomUsecase.execute(new FullInfoUserObserver(), Event.LOGIN_INFO, user.getUid(), isConnected);
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
        if (user.getScore() == -1)
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

    class FullInfoUserObserver extends DisposableSingleObserver<Optional<User>> {
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

    class RankScoreObserver extends DisposableSingleObserver<List<RankItem>> {
        @Override
        public void onSuccess(List<RankItem> listRank) {
            List<RankItem> rankItems = listRank;
            publisher.onNext(Event.create(Event.SHOW_RANK_DIALOG, rankItems));
        }

        @Override
        public void onError(Throwable e) {

        }
    }

    class LoginObserver extends DisposableSingleObserver<User> {
        @Override
        public void onSuccess(User user) {
            RoomViewModel.this.user = user;

            notifyPropertyChanged(BR.urlAvatar);
            notifyPropertyChanged(BR.userName);
            notifyPropertyChanged(BR.userVisibility);
            notifyPropertyChanged(BR.score);
        }

        @Override
        public void onError(Throwable e) {

        }
    }

    class LoginInfoObserver extends DisposableSingleObserver<Optional<User>> {
        @Override
        public void onSuccess(Optional<User> user) {
            if (user.isPresent()) {
                RoomViewModel.this.user = user.get();

                notifyPropertyChanged(BR.urlAvatar);
                notifyPropertyChanged(BR.userName);
                notifyPropertyChanged(BR.userVisibility);
                notifyPropertyChanged(BR.score);
            } else {
                onHelp(Event.create(Event.LOGIN_USER, RoomViewModel.this.user));
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    }

    class CreateRoomObserver extends DisposableSingleObserver<String> {
        @Override
        public void onSuccess(String roomid) {
            publisher.onNext(Event.create(Event.SHOW_PLAY_ACTIVITY, roomid));
        }

        @Override
        public void onError(Throwable e) {

        }
    }

     class RoomComparator implements Comparator<RoomItem> {

         public int compare(RoomItem o1,RoomItem o2){
            return o1.getRoomNumber() - o2.getRoomNumber();
         }
     }
}
