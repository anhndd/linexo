package vn.edu.hcmut.linexo.presentation.view.room;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import vn.edu.hcmut.linexo.R;

public class RoomLayout extends FrameLayout {

    private int statusBarHeight;
    private int actionBarHeight;
    private int navigaBarHeight;
    private int defaultMargin;

    private View actionBar;
    private View avatar;
    private View scoreIcon;
    private View txtScore;
    private View txtName;
    private View txtNetwork;
    private View edtSearch;
    private View lstRoom;
    private View btnCreate;
    private View advertise;

    public RoomLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public RoomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        navigaBarHeight = getResources().getDimensionPixelSize(
                getResources().getIdentifier(
                        "navigation_bar_height",
                        "dimen",
                        "android"
                )
        );

        defaultMargin = getResources().getDimensionPixelSize(R.dimen.dimen_10dp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        actionBar   = findViewById(R.id.action_bar);
        avatar      = findViewById(R.id.avatar);
        scoreIcon   = findViewById(R.id.score_icon);
        txtScore    = findViewById(R.id.txt_score);
        txtName     = findViewById(R.id.txt_name);
        txtNetwork  = findViewById(R.id.txt_network);
        edtSearch   = findViewById(R.id.edt_search);
        lstRoom     = findViewById(R.id.lst_room);
        btnCreate   = findViewById(R.id.btn_create_room);
        advertise   = findViewById(R.id.advertisement);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width  = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        actionBar.measure(
                width | MeasureSpec.EXACTLY,
                (statusBarHeight + actionBarHeight) | MeasureSpec.EXACTLY
        );
        avatar.measure(
                (actionBarHeight - 2 * defaultMargin) | MeasureSpec.EXACTLY,
                (actionBarHeight - 2 * defaultMargin) | MeasureSpec.EXACTLY
        );
        if (scoreIcon.getVisibility() != GONE && txtScore.getVisibility() != GONE) {
            scoreIcon.measure(
                    avatar.getMeasuredWidth() | MeasureSpec.EXACTLY,
                    avatar.getMeasuredHeight() | MeasureSpec.EXACTLY
            );
            txtScore.measure(
                    MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                    (scoreIcon.getMeasuredHeight() * 2 / 3) | MeasureSpec.EXACTLY
            );
            txtName.measure(
                    (width - avatar.getMeasuredWidth() - scoreIcon.getMeasuredWidth() - txtScore.getMeasuredWidth() - 3 * defaultMargin) | MeasureSpec.EXACTLY,
                    MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
            );
        } else {
            txtName.measure(
                    (width - avatar.getMeasuredWidth() - 3 * defaultMargin) | MeasureSpec.EXACTLY,
                    MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
            );
        }
        edtSearch.measure(
                (width - 2 * defaultMargin) | MeasureSpec.EXACTLY,
                avatar.getMeasuredHeight() | MeasureSpec.EXACTLY
        );
        btnCreate.measure(
                (width / 3) | MeasureSpec.EXACTLY,
                avatar.getMeasuredHeight() | MeasureSpec.EXACTLY
        );
        advertise.measure(
                width | MeasureSpec.EXACTLY,
                navigaBarHeight | MeasureSpec.EXACTLY
        );
        if (txtNetwork.getVisibility() != GONE) {
            txtNetwork.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            lstRoom.measure(
                    (width - 2 * defaultMargin) | MeasureSpec.EXACTLY,
                    (height - actionBar.getMeasuredHeight() - txtNetwork.getMeasuredHeight() - edtSearch.getMeasuredHeight() - advertise.getMeasuredHeight() - 3 * defaultMargin) | MeasureSpec.EXACTLY
            );
        } else {
            lstRoom.measure(
                    (width - 2 * defaultMargin) | MeasureSpec.EXACTLY,
                    (height - actionBar.getMeasuredHeight() - edtSearch.getMeasuredHeight() - advertise.getMeasuredHeight() - 2 * defaultMargin) | MeasureSpec.EXACTLY
            );
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = left;
        int t = top;
        actionBar.layout(l, t, l + actionBar.getMeasuredWidth(), t + actionBar.getMeasuredHeight());

        l += defaultMargin;
        t += statusBarHeight + defaultMargin;
        avatar.layout(l, t, l + avatar.getMeasuredWidth(), t + avatar.getMeasuredHeight());

        l += avatar.getMeasuredWidth() + defaultMargin;
        t += (avatar.getMeasuredHeight() - txtName.getMeasuredHeight()) / 2;
        txtName.layout(l, t, l + txtName.getMeasuredWidth(), t + txtName.getMeasuredHeight());

        if (scoreIcon.getVisibility() != GONE && txtScore.getVisibility() != GONE) {
            l += txtName.getMeasuredWidth() + defaultMargin;
            t = avatar.getTop();
            scoreIcon.layout(l, t, l + scoreIcon.getMeasuredWidth(), t + scoreIcon.getMeasuredHeight());

            l += scoreIcon.getMeasuredWidth() - defaultMargin;
            t += (scoreIcon.getMeasuredHeight() - txtScore.getMeasuredHeight()) / 2;
            txtScore.layout(l, t, l + txtScore.getMeasuredWidth(), t + txtScore.getMeasuredHeight());
        }

        t = actionBar.getBottom() + defaultMargin;
        if (txtNetwork.getVisibility() != GONE) {
            l = (actionBar.getMeasuredWidth() - txtNetwork.getMeasuredWidth()) / 2;
            txtNetwork.layout(l, t, l + txtNetwork.getMeasuredWidth(), t + txtNetwork.getMeasuredHeight());
            t += txtNetwork.getMeasuredHeight() + defaultMargin;
        }

        l = defaultMargin;
        edtSearch.layout(l, t, l + edtSearch.getMeasuredWidth(), t + edtSearch.getMeasuredHeight());

        t += edtSearch.getMeasuredHeight() + defaultMargin;
        lstRoom.layout(l, t, l + lstRoom.getMeasuredWidth(), t + lstRoom.getMeasuredHeight());

        t += lstRoom.getMeasuredHeight();
        l = left;
        advertise.layout(l, t, l + advertise.getMeasuredWidth(), t + advertise.getMeasuredHeight());

        t -= btnCreate.getMeasuredHeight() + 2 * defaultMargin;
        l += (actionBar.getMeasuredWidth() - btnCreate.getMeasuredWidth()) / 2;
        btnCreate.layout(l, t, l + btnCreate.getMeasuredWidth(), t + btnCreate.getMeasuredHeight());
    }
}
