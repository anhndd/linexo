package vn.edu.hcmut.linexo.presentation.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import vn.edu.hcmut.linexo.R;

/**
 * Created by Anh on 3/12/2019.
 */

public class RoomViewLayout extends FrameLayout {

    private View roomBg;
    private CircleImageView civOpponent;
    private CircleImageView civHost;
    private View lockRoom;
    private View pen;
    private TextView txtRoomId;

    public RoomViewLayout(@NonNull Context context) {
        super(context);
    }

    public RoomViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        roomBg = findViewById(R.id.room_bg);
        civOpponent = findViewById(R.id.civ_opponent);
        civHost = findViewById(R.id.civ_host);
        lockRoom = findViewById(R.id.lock_room_icon);
        pen = findViewById(R.id.pen_icon);
        txtRoomId = findViewById(R.id.txt_room_id);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int roomBgWitdh = width-width/11;
        int rooomBgHeight = width-width/4-width/11;
        roomBg.measure(
                (roomBgWitdh) | MeasureSpec.EXACTLY,
                (rooomBgHeight) | MeasureSpec.EXACTLY
        );
        civOpponent.measure(
                (roomBgWitdh / 2 - roomBgWitdh/8) | MeasureSpec.EXACTLY,
                (roomBgWitdh / 2 - roomBgWitdh/8) | MeasureSpec.EXACTLY
        );
        civHost.measure(
                (roomBgWitdh / 2 - roomBgWitdh/8) | MeasureSpec.EXACTLY,
                (roomBgWitdh / 2 - roomBgWitdh/8) | MeasureSpec.EXACTLY
        );
        lockRoom.measure(
                (roomBgWitdh / 6) | MeasureSpec.EXACTLY,
                (roomBgWitdh / 6) | MeasureSpec.EXACTLY
        );
        txtRoomId.measure(
                (roomBgWitdh / 6) | MeasureSpec.EXACTLY,
                (roomBgWitdh / 6 / 2 + roomBgWitdh /6 /2 /2) | MeasureSpec.EXACTLY
        );
        pen.measure(
                (roomBgWitdh / 3) | MeasureSpec.EXACTLY,
                (roomBgWitdh / 3) | MeasureSpec.EXACTLY
        );

        setMeasuredDimension(width, width-width/4);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int space = getMeasuredWidth()/11;

        int l = 0;//(getMeasuredWidth() - roomBg.getMeasuredWidth()) / 2;
        int t = 0;//(getMeasuredHeight() - roomBg.getMeasuredHeight()) / 2;
        roomBg.layout(l, t, l + roomBg.getMeasuredWidth(), t + roomBg.getMeasuredHeight());

        l = (getMeasuredWidth() - space/2 - txtRoomId.getMeasuredWidth())/2;
        t = getMeasuredHeight() - space - space/2 - txtRoomId.getMeasuredHeight();
        txtRoomId.layout(l,t,l+txtRoomId.getMeasuredWidth(),t+txtRoomId.getMeasuredHeight());

        l = (space/2);
        t = getMeasuredHeight() - space - space/2 - lockRoom.getMeasuredHeight();
        lockRoom.layout(l,t,l + lockRoom.getMeasuredWidth(), t + lockRoom.getMeasuredHeight());

        l = getMeasuredWidth() - space - civOpponent.getMeasuredWidth()/3 - civOpponent.getMeasuredWidth()/2/3- civOpponent.getMeasuredWidth();
        t = civOpponent.getMeasuredHeight() / 3;
        civOpponent.layout(l,t,l + civOpponent.getMeasuredWidth(), t + civOpponent.getMeasuredHeight());

        l = civHost.getMeasuredWidth()/3 + civHost.getMeasuredWidth()/2/3;
        t = civHost.getMeasuredHeight() / 3;
        civHost.layout(l,t,l + civHost.getMeasuredWidth(), t + civHost.getMeasuredHeight());

        l = getMeasuredWidth() - pen.getMeasuredWidth() - space / 2;
        t = getMeasuredHeight() - space - pen.getMeasuredHeight();
        pen.layout(l, t, l + pen.getMeasuredWidth(), t + pen.getMeasuredHeight());
    }
}
