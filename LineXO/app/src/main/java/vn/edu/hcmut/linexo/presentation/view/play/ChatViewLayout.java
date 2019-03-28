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
    private int type;

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
        switch (type) {
            case 1: {
                txtMessage.measure(
                        (roomBgWitdh - rooomBgHeight / 8 * 2) | MeasureSpec.AT_MOST,
                        (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.UNSPECIFIED
                );
                setMeasuredDimension(width, txtMessage.getMeasuredHeight() + rooomBgHeight / 8 * 2);
                break;
            }
            case 2: {
                txtMessage.measure(
                        (roomBgWitdh - (rooomBgHeight + rooomBgHeight / 8)) | MeasureSpec.AT_MOST,
                        (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.UNSPECIFIED
                );

                civAvatar.measure(
                        (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.EXACTLY,
                        (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.EXACTLY
                );
                txtMessageName.measure(
                        (roomBgWitdh - (rooomBgHeight - rooomBgHeight / 8 * 2 - rooomBgHeight / 8)) | MeasureSpec.AT_MOST,
                        (rooomBgHeight / 2 - rooomBgHeight / 8) | MeasureSpec.EXACTLY
                );
                int height_message = txtMessage.getMeasuredHeight() > civAvatar.getMeasuredHeight() ? txtMessage.getMeasuredHeight() : civAvatar.getMeasuredHeight();
                setMeasuredDimension(width, height_message + rooomBgHeight / 4 + rooomBgHeight / 4);
                break;
            }
            case 3:
                txtMessage.measure(
                        (roomBgWitdh) | MeasureSpec.AT_MOST,
                        (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.UNSPECIFIED
                );
                setMeasuredDimension(width, txtMessage.getMeasuredHeight() + rooomBgHeight / 8 * 2);
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        switch (type) {
            case 1: {
                int l = getMeasuredWidth() - txtMessage.getMeasuredWidth();
                int t = getMeasuredWidth() / 4 / 8;
                txtMessage.layout(l, t, l + txtMessage.getMeasuredWidth(), t + txtMessage.getMeasuredHeight());
                break;
            }
            case 2: {
                int l = civAvatar.getMeasuredWidth() + getMeasuredWidth() / 4 / 8;
                int t = getMeasuredWidth() / 4 / 4 + getMeasuredWidth() / 4 / 8;
                if ((l + txtMessage.getMeasuredWidth()) > getMeasuredWidth()) {
                    txtMessage.layout(l, t, getMeasuredWidth(), t + txtMessage.getMeasuredHeight());
                } else {
                    txtMessage.layout(l, t, l + txtMessage.getMeasuredWidth(), t + txtMessage.getMeasuredHeight());
                }

                l = 0;
                t = getMeasuredWidth() / 4 / 4 + getMeasuredWidth() / 4 / 8;
                civAvatar.layout(l, t, l + civAvatar.getMeasuredWidth(), t + civAvatar.getMeasuredHeight());

                l = civAvatar.getMeasuredWidth() + getMeasuredWidth() / 4 / 8;
                t = 0;

                txtMessageName.layout(l, t, l + txtMessageName.getMeasuredWidth(), t + txtMessageName.getMeasuredHeight());
                break;
            }
            case 3: {
                int l = (getMeasuredWidth() - txtMessage.getMeasuredWidth()) / 2;
                int t = getMeasuredWidth() / 4 / 8;
                txtMessage.layout(l, t, l + txtMessage.getMeasuredWidth(), t + txtMessage.getMeasuredHeight());
                break;
            }
        }
    }

    public void setMessageView(@NonNull int type, String id, String name, String link, @Nullable String message) {
        this.type = type;
        switch (type) {
            case 1:
                txtMessage.setBackgroundResource(R.drawable.bg_message);
                txtMessage.setText(message);
                txtMessage.setTextSize(14);
                civAvatar.setVisibility(GONE);
                txtMessageName.setVisibility(GONE);
                break;
            case 2:
                txtMessage.setBackgroundResource(R.drawable.bg_message_opposite);
                txtMessage.setText(message);
                GlideApp.with(getContext()).load(link).into(civAvatar);
                txtMessageName.setText(name);
                txtMessage.setTextSize(14);
                civAvatar.setVisibility(VISIBLE);
                txtMessageName.setVisibility(VISIBLE);
                break;
            case 3:
                txtMessage.setBackgroundResource(R.drawable.bg_message_system);
                txtMessage.setText(message);
                txtMessage.setTextSize(10);
                civAvatar.setVisibility(GONE);
                txtMessageName.setVisibility(GONE);
                break;
        }
    }
}