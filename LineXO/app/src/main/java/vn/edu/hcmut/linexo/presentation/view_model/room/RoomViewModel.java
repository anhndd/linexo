package vn.edu.hcmut.linexo.presentation.view_model.room;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.presentation.model.RoomItem;
import vn.edu.hcmut.linexo.presentation.view.room.RoomRecyclerViewAdapter;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.utils.Event;

public class RoomViewModel extends BaseObservable implements ViewModel {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private String strSearch = "";
    private RoomRecyclerViewAdapter adapter = new RoomRecyclerViewAdapter(new ArrayList<>(),this);
    private List<RoomItem> data;
    boolean check = false;

    public RoomViewModel() {
        // create view list room
        data = new ArrayList<>();
        data.add(new RoomItem("001", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("002", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new RoomItem("003", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("004", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
        data.add(new RoomItem("005", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new RoomItem("006", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
        data.add(new RoomItem("007", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null, true));
        data.add(new RoomItem("008", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new RoomItem("009", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("010", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", null, true));
        data.add(new RoomItem("011", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new RoomItem("012", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
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
                    if (item.getId().toLowerCase().contains(strSearch.toLowerCase())) {
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
            if (!check) {
                data = new ArrayList<>();
                data.add(new RoomItem("001", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                data.add(new RoomItem("002", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
                data.add(new RoomItem("003", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                data.add(new RoomItem("004", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                check = true;
            } else {
                data = new ArrayList<>();
                data.add(new RoomItem("001", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                data.add(new RoomItem("002", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                data.add(new RoomItem("003", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                data.add(new RoomItem("004", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
                data.add(new RoomItem("005", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                data.add(new RoomItem("006", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
                data.add(new RoomItem("007", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null, true));
                data.add(new RoomItem("008", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                data.add(new RoomItem("009", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                data.add(new RoomItem("010", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", null, true));
                data.add(new RoomItem("011", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                data.add(new RoomItem("012", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                check = false;
            }

            onHelp(Event.create(1,data));
        }
    }

    public void setStrSearch(String strSearch) {
        this.strSearch = strSearch;

        ArrayList<RoomItem> filteredList = new ArrayList<>();
        for (RoomItem item : data) {
            if (item.getId().toLowerCase().contains(strSearch.toLowerCase())) {
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

    @Override
    public void endTask() {

    }
}