package vn.edu.hcmut.linexo.presentation.view.room;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.custom.CircleImageView;
import vn.edu.hcmut.linexo.presentation.custom.RoomNumberView;
import vn.edu.hcmut.linexo.utils.GlideApp;

/**
 * Created by Anh on 3/12/2019.
 */

public class RoomViewLayout extends FrameLayout {

    private View roomBg;
    private CircleImageView civOpponent;
    private CircleImageView civHost;
    private View lockRoom;
    private View pen;
    private RoomNumberView roomNumberView;
    private int offsetPercent = 0;

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
        roomNumberView = findViewById(R.id.txt_room_id);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int roomBgWitdh = width - width / 11;
        int rooomBgHeight = width - width / 4 - width / 11;
        roomBg.measure(
                (roomBgWitdh) | MeasureSpec.EXACTLY,
                (rooomBgHeight) | MeasureSpec.EXACTLY
        );

        if (civOpponent.getVisibility() != GONE) {
            civOpponent.measure(
                    (roomBgWitdh / 2 - roomBgWitdh / 8) | MeasureSpec.EXACTLY,
                    (roomBgWitdh / 2 - roomBgWitdh / 8) | MeasureSpec.EXACTLY
            );
        }
        civHost.measure(
                (roomBgWitdh / 2 - roomBgWitdh / 8) | MeasureSpec.EXACTLY,
                (roomBgWitdh / 2 - roomBgWitdh / 8) | MeasureSpec.EXACTLY
        );


        if (lockRoom.getVisibility() != GONE) {
            lockRoom.measure(
                    (roomBgWitdh / 6 - roomBgWitdh / 18) | MeasureSpec.EXACTLY,
                    (roomBgWitdh / 6 - roomBgWitdh / 18) | MeasureSpec.EXACTLY
            );
        }
        roomNumberView.measure(
                (roomBgWitdh / 6) | MeasureSpec.EXACTLY,
                (roomBgWitdh / 6 / 2 + roomBgWitdh / 6 / 2 / 2) | MeasureSpec.EXACTLY
        );
        pen.measure(
                (roomBgWitdh / 3) | MeasureSpec.EXACTLY,
                (roomBgWitdh / 3) | MeasureSpec.EXACTLY
        );

        setMeasuredDimension(width, width - width / 4);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int space = getMeasuredWidth() / 11;

        int l = 0;//(getMeasuredWidth() - roomBg.getMeasuredWidth()) / 2;
        int t = 0;//(getMeasuredHeight() - roomBg.getMeasuredHeight()) / 2;
        roomBg.layout(l, t, l + roomBg.getMeasuredWidth(), t + roomBg.getMeasuredHeight());

        l = (getMeasuredWidth() - space - roomNumberView.getMeasuredWidth()) / 2;
        t = getMeasuredHeight() - space - space / 2 - roomNumberView.getMeasuredHeight();
        roomNumberView.layout(l, t, l + roomNumberView.getMeasuredWidth(), t + roomNumberView.getMeasuredHeight());

        if (lockRoom.getVisibility() != GONE) {
            l = (space / 2);
            t = getMeasuredHeight() - space - space / 2 - lockRoom.getMeasuredHeight();
            lockRoom.layout(l, t, l + lockRoom.getMeasuredWidth(), t + lockRoom.getMeasuredHeight());
        }

        int offset = ((civHost.getMeasuredWidth() + civOpponent.getMeasuredWidth()*2/3) - civHost.getMeasuredWidth()) / 2;
        if (civOpponent.getVisibility() != GONE) {
            l = (getMeasuredWidth() - space - civOpponent.getMeasuredWidth()) / 2 + offset * offsetPercent / 100;
            t = civOpponent.getMeasuredHeight() / 3;
            civOpponent.layout(l, t, l + civOpponent.getMeasuredWidth(), t + civOpponent.getMeasuredHeight());

            l = (getMeasuredWidth() - space - civHost.getMeasuredWidth()) / 2 - offset * offsetPercent / 100;
            t = civHost.getMeasuredHeight() / 3;
            civHost.layout(l, t, l + civHost.getMeasuredWidth(), t + civHost.getMeasuredHeight());
        } else {
            l = (getMeasuredWidth() - space - civHost.getMeasuredWidth()) / 2 - offset * offsetPercent / 100;
            t = civHost.getMeasuredHeight() / 3;
            civHost.layout(l, t, l + civHost.getMeasuredWidth(), t + civHost.getMeasuredHeight());
        }

        l = getMeasuredWidth() - pen.getMeasuredWidth() - space / 2;
        t = getMeasuredHeight() - space - pen.getMeasuredHeight();
        pen.layout(l, t, l + pen.getMeasuredWidth(), t + pen.getMeasuredHeight());
    }

    public void setRoomView(int id, @NonNull String link1, @Nullable String link2, boolean isPrivate) {
        roomNumberView.setNumber(id);

        civHost.setImageFromObject(link1);

        if (isPrivate) {
            lockRoom.setVisibility(VISIBLE);
        } else {
            lockRoom.setVisibility(GONE);
        }

        int beforeState = civOpponent.getVisibility();
        if (link2 == null) {
            civOpponent.setVisibility(View.GONE);
        } else {
            civOpponent.setVisibility(View.VISIBLE);
            civOpponent.setImageFromObject(link2);
        }

        int afterState = civOpponent.getVisibility();
        if (beforeState == afterState) {
            requestLayout();
        } else if (afterState == View.GONE) {
            ValueAnimator animator = ValueAnimator.ofInt(100,0);
            animator.setDuration(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    offsetPercent = (int)valueAnimator.getAnimatedValue();
                    requestLayout();
                }
            });
            animator.start();
        } else {
            ValueAnimator animator = ValueAnimator.ofInt(0,100);
            animator.setDuration(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    offsetPercent = (int)valueAnimator.getAnimatedValue();
                    requestLayout();
                }
            });
            animator.start();
        }
    }

    public RoomNumberView getRoomNumberView() {
        return roomNumberView;
    }

    public void setRoomNumberView(RoomNumberView roomNumberView) {
        this.roomNumberView = roomNumberView;
    }
}
