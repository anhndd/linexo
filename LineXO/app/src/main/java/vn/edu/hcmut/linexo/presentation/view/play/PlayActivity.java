package vn.edu.hcmut.linexo.presentation.view.play;

import android.os.Bundle;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.view.BaseActivity;

public class PlayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
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
