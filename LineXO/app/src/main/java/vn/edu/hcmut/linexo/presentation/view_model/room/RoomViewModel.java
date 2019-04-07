package vn.edu.hcmut.linexo.presentation.view_model.room;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.BR;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.domain.interactor.Usecase;
import vn.edu.hcmut.linexo.presentation.model.RoomItem;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.presentation.view.play.PlayActivity;
import vn.edu.hcmut.linexo.presentation.view.room.RoomRecyclerViewAdapter;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.Optional;

public class RoomViewModel extends BaseObservable implements ViewModel, ViewModelCallback {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private String strSearch = "";
    private RoomRecyclerViewAdapter adapter = new RoomRecyclerViewAdapter(new ArrayList<>(), this);
    private User user;
    private List<RoomItem> data;
    private Context context;
    private Usecase roomUsecase;

    int i = 1;

    public RoomViewModel(Context context, Usecase roomUsecase) {
        this.context = context;
        this.roomUsecase = roomUsecase;
        // create view list room
        data = new ArrayList<>();

        data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
        data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
        data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null, true));
        data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new RoomItem(i++, "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem(i++, "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", null, true));
        data.add(new RoomItem(i++, "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new RoomItem(i++, "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
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
                String roomId = (String) data[0];
                break;
            }
            case Event.LOAD_LIST_ROOM: {
                data = new ArrayList<>((List<RoomItem>) e.getData()[0]);

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
            case Event.LOGOUT: {
                roomUsecase.execute(null,Event.LOGOUT);
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
        else return View.VISIBLE;
    }

    @Override
    public void endTask() {

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
}