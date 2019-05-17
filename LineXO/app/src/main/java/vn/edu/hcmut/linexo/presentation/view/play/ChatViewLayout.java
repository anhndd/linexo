package vn.edu.hcmut.linexo.presentation.view.play;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.custom.CircleImageView;
import vn.edu.hcmut.linexo.utils.GlideApp;

/**
 * Created by Anh on 3/12/2019.
 */

public class ChatViewLayout extends FrameLayout {

    private TextView txtMessageName;
    private CircleImageView civAvatar;
    private TextView txtMessage;
    private TextView txtMessageSystem;
    private String id;
    private String Uid;

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
        txtMessageSystem = findViewById(R.id.txt_message_system);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int roomBgWitdh = width;
        int rooomBgHeight = width / 4;

        if(this.id.equals(Uid)) {
            txtMessage.measure(
                    roomBgWitdh*4/5 | MeasureSpec.AT_MOST,
                    (rooomBgHeight - rooomBgHeight / 8) | MeasureSpec.UNSPECIFIED
            );
            setMeasuredDimension(width, txtMessage.getMeasuredHeight() + rooomBgHeight / 8);
        }
        else if (this.id.equals("0")){
            txtMessageSystem.measure(
                    (roomBgWitdh) | MeasureSpec.AT_MOST,
                    (rooomBgHeight - rooomBgHeight / 8) | MeasureSpec.UNSPECIFIED
            );
            setMeasuredDimension(width, txtMessageSystem.getMeasuredHeight() + rooomBgHeight / 8);
        }
        else{
            txtMessage.measure(
                    (roomBgWitdh*4/5 - (rooomBgHeight/2 + rooomBgHeight / 8)) | MeasureSpec.AT_MOST,
                    (rooomBgHeight - rooomBgHeight / 8 * 2) | MeasureSpec.UNSPECIFIED
            );

            civAvatar.measure(
                    (rooomBgHeight )/2 | MeasureSpec.EXACTLY,
                    (rooomBgHeight )/2 | MeasureSpec.EXACTLY
            );
            txtMessageName.measure(
                    (roomBgWitdh - (rooomBgHeight - rooomBgHeight / 8 * 2 - rooomBgHeight / 8)) | MeasureSpec.AT_MOST,
                    (rooomBgHeight / 2 - rooomBgHeight / 8) | MeasureSpec.EXACTLY
            );
            int height_message = txtMessage.getMeasuredHeight() > civAvatar.getMeasuredHeight() ? txtMessage.getMeasuredHeight() : civAvatar.getMeasuredHeight();
            setMeasuredDimension(width, height_message + rooomBgHeight / 4 + rooomBgHeight / 4);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(this.id.equals(Uid)) {
            int l = getMeasuredWidth() - txtMessage.getMeasuredWidth();
            int t = 0;
            txtMessage.layout(l, t, l + txtMessage.getMeasuredWidth(), t + txtMessage.getMeasuredHeight());
        }
        else if (this.id.equals("0")){
            int l = (getMeasuredWidth() - txtMessageSystem.getMeasuredWidth()) / 2;
            int t = 0;
            txtMessageSystem.layout(l, t, l + txtMessageSystem.getMeasuredWidth(), t + txtMessageSystem.getMeasuredHeight());
        }
        else{
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
        }
    }

    public void setMessageView(@NonNull  String id, String name, String link, @Nullable String message, String Uid) {
        this.id = id;
        this.Uid = Uid;
        if(this.id.equals(Uid)){
            txtMessage.setBackgroundResource(R.drawable.bg_message);
            txtMessage.setText(message);
            txtMessage.setTextColor(Color.BLACK);
            txtMessage.setTextSize(14);
            txtMessage.setVisibility(VISIBLE);
            civAvatar.setVisibility(GONE);
            txtMessageName.setVisibility(GONE);
            txtMessageSystem.setVisibility(GONE);
        }
        else if (this.id.equals("0")) {
            txtMessageSystem.setText(message);
            txtMessageSystem.setTextColor(Color.WHITE);
            txtMessageSystem.setTextSize(12);
            txtMessageSystem.setVisibility(VISIBLE);
            civAvatar.setVisibility(GONE);
            txtMessage.setVisibility(GONE);
            txtMessageName.setVisibility(GONE);
        }
        else {
            txtMessage.setBackgroundResource(R.drawable.bg_message_opposite);
            txtMessage.setText(message);
            GlideApp.with(getContext()).load(link).into(civAvatar);
            txtMessage.setTextColor(Color.BLACK);
            txtMessage.setTextSize(14);
            txtMessageName.setText(name);
            txtMessageName.setTextSize(12);
            txtMessage.setVisibility(VISIBLE);
            civAvatar.setVisibility(VISIBLE);
            txtMessageName.setVisibility(VISIBLE);
            txtMessageSystem.setVisibility(GONE);
        }
    }
}