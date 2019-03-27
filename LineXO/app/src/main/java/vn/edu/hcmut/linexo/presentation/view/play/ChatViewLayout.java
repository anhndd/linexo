package vn.edu.hcmut.linexo.presentation.view.play;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.custom.CircleImageView;
import vn.edu.hcmut.linexo.presentation.custom.RoomNumberView;
import vn.edu.hcmut.linexo.utils.GlideApp;

/**
 * Created by Anh on 3/12/2019.
 */

public class ChatViewLayout extends FrameLayout {

    private TextView txtMessageName;
    private CircleImageView civAvatar;
    private TextView txtMessage;

    public ChatViewLayout(@NonNull Context context) {
        super(context);
    }

    public ChatViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        txtMessageName = findViewById(R.id.txt_message_name);
        civAvatar = findViewById(R.id.civ_avatar);
        txtMessage = findViewById(R.id.txt_message);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int roomBgWitdh = width;
        int rooomBgHeight = width / 4;
        if (civAvatar.getVisibility() == VISIBLE) {
            txtMessage.measure(
                    (roomBgWitdh - (rooomBgHeight + rooomBgHeight / 8)) | MeasureSpec.AT_MOST,
                    (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.UNSPECIFIED
            );
        } else {
            txtMessage.measure(
                    (roomBgWitdh - rooomBgHeight / 8 * 2) | MeasureSpec.AT_MOST,
                    (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.UNSPECIFIED
            );
        }
        civAvatar.measure(
                (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.EXACTLY,
                (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.EXACTLY
        );
        txtMessageName.measure(
                (roomBgWitdh - (rooomBgHeight - rooomBgHeight / 8 * 2 - rooomBgHeight / 8)) | MeasureSpec.AT_MOST,
                (rooomBgHeight / 2 - rooomBgHeight/8) | MeasureSpec.EXACTLY
        );
//
//        if (civOpponent.getVisibility() != GONE) {
//            civOpponent.measure(
//                    (roomBgWitdh / 2 - roomBgWitdh / 8) | MeasureSpec.EXACTLY,
//                    (roomBgWitdh / 2 - roomBgWitdh / 8) | MeasureSpec.EXACTLY
//            );
//        }
//        civHost.measure(
//                (roomBgWitdh / 2 - roomBgWitdh / 8) | MeasureSpec.EXACTLY,
//                (roomBgWitdh / 2 - roomBgWitdh / 8) | MeasureSpec.EXACTLY
//        );
//
//
//        if (lockRoom.getVisibility() != GONE) {
//            lockRoom.measure(
//                    (roomBgWitdh / 6 - roomBgWitdh / 18) | MeasureSpec.EXACTLY,
//                    (roomBgWitdh / 6 - roomBgWitdh / 18) | MeasureSpec.EXACTLY
//            );
//        }
//        roomNumberView.measure(
//                (roomBgWitdh / 6) | MeasureSpec.EXACTLY,
//                (roomBgWitdh / 6 / 2 + roomBgWitdh / 6 / 2 / 2) | MeasureSpec.EXACTLY
//        );
//        pen.measure(
//                (roomBgWitdh / 3) | MeasureSpec.EXACTLY,
//                (roomBgWitdh / 3) | MeasureSpec.EXACTLY
//        );
        if (civAvatar.getVisibility() == VISIBLE) {
            setMeasuredDimension(width, txtMessage.getMeasuredHeight() + rooomBgHeight / 4 + rooomBgHeight / 4);
        } else {
            setMeasuredDimension(width, txtMessage.getMeasuredHeight() + rooomBgHeight / 4);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        int space = getMeasuredWidth() / 11;
//
        if (civAvatar.getVisibility() == VISIBLE) {
            txtMessage.setBackgroundResource(R.drawable.bg_message_opposite);
            int l = civAvatar.getMeasuredWidth() + getMeasuredWidth() / 4 / 8;
            int t = getMeasuredWidth() / 4 / 4 + getMeasuredWidth() / 4 / 8;
            if ((l + txtMessage.getMeasuredWidth()) > getMeasuredWidth()) {
                txtMessage.layout(l, t, getMeasuredWidth(), t + txtMessage.getMeasuredHeight());
            } else {
                txtMessage.layout(l, t, l + txtMessage.getMeasuredWidth(), t + txtMessage.getMeasuredHeight());
            }

            l = 0;
            t = +getMeasuredWidth() / 4 / 4 + getMeasuredWidth() / 4 / 8;
            civAvatar.layout(l, t, l + civAvatar.getMeasuredWidth(), t + civAvatar.getMeasuredHeight());

            l = civAvatar.getMeasuredWidth() + getMeasuredWidth() / 4 / 8;
            t = 0;
//            if ((l + txtMessageName.getMeasuredWidth()) > getMeasuredWidth()) {
//                txtMessageName.layout(l, t, getMeasuredWidth()- l, t + txtMessageName.getMeasuredHeight());
//            } else {
            txtMessageName.layout(l, t, l + txtMessageName.getMeasuredWidth(), t + txtMessageName.getMeasuredHeight());
//            }
        } else if (txtMessage.getVisibility() == VISIBLE) {
            txtMessage.setBackgroundResource(R.drawable.bg_message);
            int l = getMeasuredWidth() - txtMessage.getMeasuredWidth();
            int t = getMeasuredWidth() / 4 / 4 - getMeasuredWidth() / 4 / 8;
//            if ((l + txtMessage.getMeasuredWidth()) > getMeasuredWidth()) {
//                txtMessage.layout(l, t, getMeasuredWidth(), t + txtMessage.getMeasuredHeight());
//            } else {
            txtMessage.layout(l, t, l + txtMessage.getMeasuredWidth(), t + txtMessage.getMeasuredHeight());
//            }
        }
//        roomBg.layout(l, t, l + roomBg.getMeasuredWidth(), t + roomBg.getMeasuredHeight());
//
//        l = (getMeasuredWidth() - space - roomNumberView.getMeasuredWidth()) / 2;
//        t = getMeasuredHeight() - space - space / 2 - roomNumberView.getMeasuredHeight();
//        roomNumberView.layout(l, t, l + roomNumberView.getMeasuredWidth(), t + roomNumberView.getMeasuredHeight());
//
//        if (lockRoom.getVisibility() != GONE) {
//            l = (space / 2);
//            t = getMeasuredHeight() - space - space / 2 - lockRoom.getMeasuredHeight();
//            lockRoom.layout(l, t, l + lockRoom.getMeasuredWidth(), t + lockRoom.getMeasuredHeight());
//        }
//
//        int offset = ((civHost.getMeasuredWidth() + civOpponent.getMeasuredWidth()*2/3) - civHost.getMeasuredWidth()) / 2;
//        if (civOpponent.getVisibility() != GONE) {
//            l = (getMeasuredWidth() - space - civOpponent.getMeasuredWidth()) / 2 + offset * offsetPercent / 100;
//            t = civOpponent.getMeasuredHeight() / 3;
//            civOpponent.layout(l, t, l + civOpponent.getMeasuredWidth(), t + civOpponent.getMeasuredHeight());
//
//            l = (getMeasuredWidth() - space - civHost.getMeasuredWidth()) / 2 - offset * offsetPercent / 100;
//            t = civHost.getMeasuredHeight() / 3;
//            civHost.layout(l, t, l + civHost.getMeasuredWidth(), t + civHost.getMeasuredHeight());
//        } else {
//            l = (getMeasuredWidth() - space - civHost.getMeasuredWidth()) / 2 - offset * offsetPercent / 100;
//            t = civHost.getMeasuredHeight() / 3;
//            civHost.layout(l, t, l + civHost.getMeasuredWidth(), t + civHost.getMeasuredHeight());
//        }
//
//        l = getMeasuredWidth() - pen.getMeasuredWidth() - space / 2;
//        t = getMeasuredHeight() - space - pen.getMeasuredHeight();
//        pen.layout(l, t, l + pen.getMeasuredWidth(), t + pen.getMeasuredHeight());
    }

    public void setMessageView(@NonNull int type, String id, String name, String link, @Nullable String message) {
        switch (type) {
            case 1:
                txtMessage.setText(message);
                civAvatar.setVisibility(GONE);
                txtMessageName.setVisibility(GONE);
                break;
            case 2:
                txtMessage.setText(message);
                GlideApp.with(getContext()).load(link).into(civAvatar);
                txtMessageName.setText(name);
                break;
            case 3:
                break;
        }
    }
}