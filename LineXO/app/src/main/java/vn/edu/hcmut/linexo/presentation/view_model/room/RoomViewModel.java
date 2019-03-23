package vn.edu.hcmut.linexo.presentation.view_model.room;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.model.RoomItem;
import vn.edu.hcmut.linexo.presentation.view.play.PlayActivity;
import vn.edu.hcmut.linexo.presentation.view.room.RoomRecyclerViewAdapter;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.utils.Event;

public class RoomViewModel extends BaseObservable implements ViewModel, ViewModelCallback {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private String strSearch = "";
    private RoomRecyclerViewAdapter adapter = new RoomRecyclerViewAdapter(new ArrayList<>(),this);
    private String urlAvatar;
    private List<RoomItem> data;

    boolean check = false;

    public RoomViewModel() {
        // create view list room
        data = new ArrayList<>();
        int i = 1;
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
        adapter = new RoomRecyclerViewAdapter(data,this);
        data = new ArrayList<>(data);

    }

    @Override
    public void subscribeObserver(Observer<Event> observer) {
        publisher.subscribe(observer);
    }

    @Override
    public void onHelp(Event e) {
        if(e.getType()==0){
            Object[] data = e.getData();
            String roomId = (String) data[0];
        }
        else if(e.getType()==1){
            data = new ArrayList<>((List<RoomItem>) e.getData()[0]);

            if(!strSearch.isEmpty()){
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
            }
            else {
                adapter.updateRoomListItems(data);
            }
        }
        else if(e.getType()==2){
            int i = 1;
            if (!check) {
                data = new ArrayList<>();
                data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
                data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                data.add(new RoomItem(i++, "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                check = true;
            } else {
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
                check = false;
            }

            onHelp(Event.create(1,data));
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
        if(urlAvatar == null || urlAvatar.isEmpty())
            return R.drawable.ic_logo_round;
        return urlAvatar;
    }

    public void onClickCreateRoom(View view) {
        Intent intent = new Intent(view.getContext(), PlayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        view.getContext().startActivity(intent);
    }

    @Override
    public void endTask() {

    }
}