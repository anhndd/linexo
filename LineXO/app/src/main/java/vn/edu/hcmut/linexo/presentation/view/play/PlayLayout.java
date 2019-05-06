package vn.edu.hcmut.linexo.presentation.view.play;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import vn.edu.hcmut.linexo.R;

public class PlayLayout extends FrameLayout {

    private int statusBarHeight;
    private int actionBarHeight;
    private int defaultMargin;
    private int horizontalOffset;
    private int keyboardHeight;
    private int keyboardOrientation;

    private boolean isAI = false;

    private View txtNetwork;
    private View avatar1;
    private View scoreIcon1;
    private View txtScore1;
    private View txtClock1;
    private View txtRoom;
    private View avatar2;
    private View scoreIcon2;
    private View txtScore2;
    private View txtClock2;
    private View board;
    private View lstMessage;
    private View btnMessage;
    private View btnSend;
    private View edtMessage;
    private View imgRobot;

    public PlayLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public PlayLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        statusBarHeight = getResources().getDimensionPixelSize(
                getResources().getIdentifier(
                        "status_bar_height",
                        "dimen",
                        "android"
                )
        );

        TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[] {android.R.attr.actionBarSize}
        );
        actionBarHeight = styledAttributes.getDimensionPixelSize(0, 0);
        styledAttributes.recycle();

        defaultMargin = getResources().getDimensionPixelSize(R.dimen.dimen_10dp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        txtNetwork  = findViewById(R.id.txt_network);
        avatar1     = findViewById(R.id.avatar_1);
        scoreIcon1  = findViewById(R.id.score_icon_1);
        txtScore1   = findViewById(R.id.txt_score1);
        txtClock1   = findViewById(R.id.txt_clock_1);
        txtRoom     = findViewById(R.id.txt_room);
        avatar2     = findViewById(R.id.avatar_2);
        scoreIcon2  = findViewById(R.id.score_icon_2);
        txtScore2   = findViewById(R.id.txt_score2);
        txtClock2   = findViewById(R.id.txt_clock_2);
        board       = findViewById(R.id.board);
        lstMessage  = findViewById(R.id.lst_message);
        btnMessage  = findViewById(R.id.btn_message);
        btnSend     = findViewById(R.id.btn_send);
        edtMessage  = findViewById(R.id.edt_message);
        imgRobot    = findViewById(R.id.img_robot);
    }

    public void setKeyboardHeight(int height, int orientation) {
        keyboardHeight = height;
        keyboardOrientation = orientation;
        if (height > 0) {
            btnSend.setVisibility(VISIBLE);
            edtMessage.setVisibility(VISIBLE);
        } else {
            btnSend.setVisibility(GONE);
            edtMessage.setVisibility(GONE);
        }
    }

    public void setPlayType(boolean isAI) {
        this.isAI = isAI;
        if (isAI) {
            imgRobot.setVisibility(VISIBLE);
            btnSend.setVisibility(GONE);
            edtMessage.setVisibility(GONE);
            btnMessage.setVisibility(GONE);
            lstMessage.setVisibility(GONE);
        } else {
            imgRobot.setVisibility(GONE);
            btnSend.setVisibility(VISIBLE);
            edtMessage.setVisibility(VISIBLE);
            btnMessage.setVisibility(VISIBLE);
            lstMessage.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width  = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        if (height * 16 / 9 < width) {
            horizontalOffset = (width - height * 16 / 9) / 2;
        } else {
            horizontalOffset = 0;
        }
        txtNetwork.measure(
                width | MeasureSpec.EXACTLY,
                statusBarHeight | MeasureSpec.EXACTLY
        );
        board.measure(
                ((width - 2 * horizontalOffset - 3 * defaultMargin) * 7 / 10) | MeasureSpec.EXACTLY,
                (((width - 2 * horizontalOffset - 3 * defaultMargin) * 7 / 10) * 5 / 9) | MeasureSpec.EXACTLY
        );
        avatar1.measure(
                (height - statusBarHeight - board.getMeasuredHeight() - 3 * defaultMargin) | MeasureSpec.EXACTLY,
                (height - statusBarHeight - board.getMeasuredHeight() - 3 * defaultMargin) | MeasureSpec.EXACTLY
        );
        scoreIcon1.measure(
                ((avatar1.getMeasuredHeight() - defaultMargin) * 3 / 5) | MeasureSpec.EXACTLY,
                ((avatar1.getMeasuredHeight() - defaultMargin) * 3 / 5) | MeasureSpec.EXACTLY
        );
        txtScore1.measure(
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                (scoreIcon1.getMeasuredHeight() * 2 / 3) | MeasureSpec.EXACTLY

        );
        txtClock1.measure(
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                (scoreIcon1.getMeasuredHeight() * 2 / 3) | MeasureSpec.EXACTLY
        );
        txtRoom.measure(
                (avatar1.getMeasuredHeight() * 3 / 4) | MeasureSpec.EXACTLY,
                (avatar1.getMeasuredHeight() * 3 / 4) | MeasureSpec.EXACTLY
        );
        if (avatar2.getVisibility() != GONE) {
            avatar2.measure(
                    avatar1.getMeasuredWidth() | MeasureSpec.EXACTLY,
                    avatar1.getMeasuredHeight() | MeasureSpec.EXACTLY
            );
            scoreIcon2.measure(
                    scoreIcon1.getMeasuredWidth() | MeasureSpec.EXACTLY,
                    scoreIcon1.getMeasuredHeight() | MeasureSpec.EXACTLY
            );
            txtScore2.measure(
                    MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                    txtScore1.getMeasuredHeight() | MeasureSpec.EXACTLY

            );
            txtClock2.measure(
                    txtClock1.getMeasuredWidth() | MeasureSpec.EXACTLY,
                    txtClock1.getMeasuredHeight() | MeasureSpec.EXACTLY
            );
        }
        if (btnMessage.getVisibility() != GONE) {
            btnMessage.measure(
                    ((width - 2 * horizontalOffset - board.getMeasuredWidth() - 3 * defaultMargin)) | MeasureSpec.EXACTLY,
                    ((width - 2 * horizontalOffset - board.getMeasuredWidth() - 3 * defaultMargin) / 5) | MeasureSpec.EXACTLY
            );
            lstMessage.measure(
                    btnMessage.getMeasuredWidth() | MeasureSpec.EXACTLY,
                    (height - statusBarHeight - 2 * defaultMargin - btnMessage.getMeasuredHeight()) | MeasureSpec.EXACTLY
            );
        }
        if (imgRobot.getVisibility() != GONE) {
            imgRobot.measure(
                    ((width - 2 * horizontalOffset - board.getMeasuredWidth() - 3 * defaultMargin)) | MeasureSpec.EXACTLY,
                    (height - statusBarHeight - 2 * defaultMargin) | MeasureSpec.EXACTLY
            );
        }
        if (btnSend.getVisibility() != GONE) {
            btnSend.measure(
                    actionBarHeight | MeasureSpec.EXACTLY,
                    actionBarHeight | MeasureSpec.EXACTLY
            );
            edtMessage.measure(
                    (width - actionBarHeight) | MeasureSpec.EXACTLY,
                    actionBarHeight | MeasureSpec.EXACTLY
            );
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = left;
        int t = top;
        txtNetwork.layout(l, t, l + txtNetwork.getMeasuredWidth(), t + txtNetwork.getMeasuredHeight());

        l = horizontalOffset + defaultMargin;
        t = statusBarHeight + defaultMargin;
        avatar1.layout(l, t, l + avatar1.getMeasuredWidth(), t + avatar1.getMeasuredHeight());

        l += avatar1.getMeasuredWidth() + defaultMargin;
        scoreIcon1.layout(l, t, l + scoreIcon1.getMeasuredWidth(), t + scoreIcon1.getMeasuredHeight());

        t += scoreIcon1.getMeasuredHeight() + defaultMargin;
        txtClock1.layout(l, t, l + txtClock1.getMeasuredWidth(), t + txtClock1.getMeasuredHeight());

        l += scoreIcon1.getMeasuredWidth() - defaultMargin;
        t = scoreIcon1.getTop() + (scoreIcon1.getMeasuredHeight() - txtScore1.getMeasuredHeight()) / 2;
        txtScore1.layout(l, t, l + txtScore1.getMeasuredWidth(), t + txtScore1.getMeasuredHeight());

        l = avatar1.getLeft();
        t = avatar1.getBottom() + defaultMargin;
        board.layout(l, t, l + board.getMeasuredWidth(), t + board.getMeasuredHeight());

        if (avatar2.getVisibility() != GONE) {
            l = board.getRight() - avatar2.getMeasuredWidth();
            t = avatar1.getTop();
            avatar2.layout(l, t, l + avatar2.getMeasuredWidth(), t + avatar2.getMeasuredHeight());

            l -= scoreIcon2.getMeasuredWidth() + defaultMargin;
            scoreIcon2.layout(l, t, l + scoreIcon2.getMeasuredWidth(), t + scoreIcon2.getMeasuredHeight());

            l = avatar2.getLeft() - defaultMargin - txtClock2.getMeasuredWidth();
            t = txtClock1.getTop();
            txtClock2.layout(l, t, l + txtClock2.getMeasuredWidth(), t + txtClock2.getMeasuredHeight());

            l = scoreIcon2.getLeft() - txtScore2.getMeasuredWidth() + defaultMargin;
            t = txtScore1.getTop();
            txtScore2.layout(l, t, l + txtScore2.getMeasuredWidth(), t + txtScore2.getMeasuredHeight());
        }

        l = avatar1.getLeft() + (board.getMeasuredWidth() - txtRoom.getMeasuredWidth()) / 2;
        t = avatar1.getTop() + (avatar1.getMeasuredHeight() - txtRoom.getMeasuredHeight()) / 2;
        txtRoom.layout(l, t, l + txtRoom.getMeasuredWidth(), t + txtRoom.getMeasuredHeight());

        if (btnMessage.getVisibility() != GONE) {
            l = board.getRight() + defaultMargin;
            t = avatar1.getTop();
            lstMessage.layout(l, t, l + lstMessage.getMeasuredWidth(), t + lstMessage.getMeasuredHeight());

            t += lstMessage.getMeasuredHeight();
            btnMessage.layout(l, t, l + btnMessage.getMeasuredWidth(), t + btnMessage.getMeasuredHeight());
        }

        if (imgRobot.getVisibility() != GONE) {
            l = board.getRight() + defaultMargin;
            t = avatar1.getTop();
            imgRobot.layout(l, t, l + imgRobot.getMeasuredWidth(), t + imgRobot.getMeasuredHeight());
        }

        if (btnSend.getVisibility() != GONE) {
            l = left;
            t = bottom - keyboardHeight - actionBarHeight;
            edtMessage.layout(l, t, l + edtMessage.getMeasuredWidth(), t + edtMessage.getMeasuredHeight());
            l += edtMessage.getMeasuredWidth();
            btnSend.layout(l, t, l + btnSend.getMeasuredWidth(), t + btnSend.getMeasuredHeight());
        }
    }
}
