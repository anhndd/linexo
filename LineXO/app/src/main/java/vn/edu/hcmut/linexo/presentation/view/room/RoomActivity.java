package vn.edu.hcmut.linexo.presentation.view.room;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.databinding.ActivityRoomBinding;
import vn.edu.hcmut.linexo.presentation.model.RoomItem;
import vn.edu.hcmut.linexo.presentation.view.BaseActivity;

public class RoomActivity extends BaseActivity {
    RoomRecyclerViewAdapter mRcvAdapter;
    List<RoomItem> data;
    ActivityRoomBinding binding;
    boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new ArrayList<>();
        data.add(new RoomItem("001","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("002","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("003","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("004","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg",null));
        data.add(new RoomItem("005","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("006","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg",null));
        data.add(new RoomItem("007","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg",null,true));
        data.add(new RoomItem("008","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("009","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new RoomItem("010","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg",null,true));
        data.add(new RoomItem("011","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
        data.add(new RoomItem("012","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        mRcvAdapter = new RoomRecyclerViewAdapter(data);

        binding.lstRoom.setLayoutManager(new GridLayoutManager(this, 2));
        binding.lstRoom.setHasFixedSize(true);
        binding.lstRoom.setAdapter(mRcvAdapter);

        binding.avatar.setImageResource(R.drawable.ic_logo_round);

        binding.btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!check) {
                    data = new ArrayList<>();
                    data.add(new RoomItem("001", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new RoomItem("002", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
                    data.add(new RoomItem("003", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new RoomItem("004", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
                    check = true;
                }
                else {
                    data = new ArrayList<>();
                    data.add(new RoomItem("001","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new RoomItem("002","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
                    data.add(new RoomItem("003","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new RoomItem("004","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg",null));
                    data.add(new RoomItem("005","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
                    data.add(new RoomItem("006","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg",null));
                    data.add(new RoomItem("007","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg",null,true));
                    data.add(new RoomItem("008","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
                    data.add(new RoomItem("009","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new RoomItem("010","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg",null,true));
                    data.add(new RoomItem("011","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg",true));
                    data.add(new RoomItem("012","https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg","https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    check = false;
                }
                mRcvAdapter.updateRoomListItems(data);
            }
        });
    }

    @Override
    public void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room);
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
