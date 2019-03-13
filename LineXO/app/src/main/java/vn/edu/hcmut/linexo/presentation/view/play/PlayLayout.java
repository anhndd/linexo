package vn.edu.hcmut.linexo.presentation.view.play;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import vn.edu.hcmut.linexo.R;

public class PlayLayout extends FrameLayout {

    private int statusBarHeight;
    private int defaultMargin;

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
    private View edtMessage;
    private View btnSend;

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
        edtMessage  = findViewById(R.id.edt_message);
        btnSend     = findViewById(R.id.btn_send);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width  = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        txtNetwork.measure(
                width | MeasureSpec.EXACTLY,
                statusBarHeight | MeasureSpec.EXACTLY
        );
        board.measure(
                ((width - 3 * defaultMargin) * 19 / 30) | MeasureSpec.EXACTLY,
                (((width - 3 * defaultMargin) * 19 / 30) * 5 / 9) | MeasureSpec.EXACTLY
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
        btnSend.measure(
                ((width - board.getMeasuredWidth() - 4 * defaultMargin) / 6) | MeasureSpec.EXACTLY,
                ((width - board.getMeasuredWidth() - 4 * defaultMargin) / 6) | MeasureSpec.EXACTLY
        );
        edtMessage.measure(
                (btnSend.getMeasuredWidth() * 5) | MeasureSpec.EXACTLY,
                btnSend.getMeasuredWidth() | MeasureSpec.EXACTLY
        );
        lstMessage.measure(
                (edtMessage.getMeasuredWidth() + btnSend.getMeasuredWidth() + defaultMargin) | MeasureSpec.EXACTLY,
                (height - statusBarHeight - 2 * defaultMargin - edtMessage.getMeasuredHeight()) | MeasureSpec.EXACTLY
        );
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = left;
        int t = top;
        txtNetwork.layout(l, t, l + txtNetwork.getMeasuredWidth(), t + txtNetwork.getMeasuredHeight());

        l = defaultMargin;
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

        l = defaultMargin + (board.getMeasuredWidth() - txtRoom.getMeasuredWidth()) / 2;
        t = avatar1.getTop() + (avatar1.getMeasuredHeight() - txtRoom.getMeasuredHeight()) / 2;
        txtRoom.layout(l, t, l + txtRoom.getMeasuredWidth(), t + txtRoom.getMeasuredHeight());

        l = board.getRight() + defaultMargin;
        t = avatar1.getTop();
        lstMessage.layout(l, t, l + lstMessage.getMeasuredWidth(), t + lstMessage.getMeasuredHeight());

        t += lstMessage.getMeasuredHeight();
        edtMessage.layout(l, t, l + edtMessage.getMeasuredWidth(), t + edtMessage.getMeasuredHeight());

        l += edtMessage.getMeasuredWidth() + defaultMargin;
        btnSend.layout(l, t, l + btnSend.getMeasuredWidth(), t + btnSend.getMeasuredHeight());
    }
}
