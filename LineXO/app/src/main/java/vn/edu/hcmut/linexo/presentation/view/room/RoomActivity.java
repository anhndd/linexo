package vn.edu.hcmut.linexo.presentation.view.room;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.custom.RoomRecyclerViewAdapter;
import vn.edu.hcmut.linexo.presentation.model.RoomItem;
import vn.edu.hcmut.linexo.presentation.view.BaseActivity;

public class RoomActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    RoomRecyclerViewAdapter mRcvAdapter;
    List<RoomItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mRecyclerView = (RecyclerView) findViewById(R.id.lst_room);
        data = new ArrayList<>();
        data.add(new RoomItem("001","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("002","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("003","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("004","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("005","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("006","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("007","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("008","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("009","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("010","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("011","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("012","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        mRcvAdapter = new RoomRecyclerViewAdapter(data);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mRcvAdapter);
    }

    @Override
    public void onDataBinding() {

    }

    @Override
    public void onSubscribeViewModel() {

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
}
