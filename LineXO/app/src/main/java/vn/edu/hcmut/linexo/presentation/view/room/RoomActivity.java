package vn.edu.hcmut.linexo.presentation.view.room;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.databinding.ActivityRoomBinding;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.view.BaseActivity;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;

public class RoomActivity extends BaseActivity {
    @Inject
    @Named("RoomViewModel")
    public ViewModel viewModel;

    RoomRecyclerViewAdapter mRcvAdapter;
    List<Room> data;
    ActivityRoomBinding binding;
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create view list room
        data = new ArrayList<>();
        data.add(new Room("001", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new Room("002", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new Room("003", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new Room("004", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
        data.add(new Room("005", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new Room("006", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
        data.add(new Room("007", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null, true));
        data.add(new Room("008", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new Room("009", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        data.add(new Room("010", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", null, true));
        data.add(new Room("011", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
        data.add(new Room("012", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
        mRcvAdapter = new RoomRecyclerViewAdapter(data);

        binding.lstRoom.setLayoutManager(new GridLayoutManager(this, 2));
        binding.lstRoom.setHasFixedSize(true);
        binding.lstRoom.setAdapter(mRcvAdapter);

        // avatar set image
        binding.avatar.setImageResource(R.drawable.ic_logo_round);

        // click create room
        binding.btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check) {
                    data = new ArrayList<>();
                    data.add(new Room("001", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new Room("002", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
                    data.add(new Room("003", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new Room("004", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                    check = true;
                } else {
                    data = new ArrayList<>();
                    data.add(new Room("001", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new Room("002", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                    data.add(new Room("003", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new Room("004", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
                    data.add(new Room("005", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                    data.add(new Room("006", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null));
                    data.add(new Room("007", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", null, true));
                    data.add(new Room("008", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                    data.add(new Room("009", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    data.add(new Room("010", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", null, true));
                    data.add(new Room("011", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg", true));
                    data.add(new Room("012", "https://image.vtcns.com/resize/685x498/files/ctv.giaoduc/2018/02/22/kieu-trinh-1-0550339.jpg", "https://kenh14cdn.com/2017/1-1506422137960.jpg"));
                    check = false;
                }
                mRcvAdapter.updateRoomListItems(data);
            }
        });

        // search room
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<Room> filteredList = new ArrayList<>();
                for (Room item : data) {
                    if (item.getId().toLowerCase().contains(editable.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }

                mRcvAdapter.filterList(filteredList);
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