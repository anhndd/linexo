package vn.edu.hcmut.linexo.presentation.view;

import android.os.Bundle;
import vn.edu.hcmut.linexo.R;

public class RoomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
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
