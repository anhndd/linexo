package vn.edu.hcmut.linexo.presentation.view.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import vn.edu.hcmut.linexo.R;

/**
 * Created by Anh on 3/12/2019.
 */

public class RankViewLayout extends FrameLayout {

    private View txtNumberRank;
    private View txtUserName;
    private View txtScore;
    private View avatar;
    private View lineSpaceRank;
    private int defaultMargin;

    public RankViewLayout(@NonNull Context context) {
        super(context);
    }

    public RankViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RankViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        txtUserName = findViewById(R.id.txt_username);
        txtScore = findViewById(R.id.txt_userscore);
        avatar = findViewById(R.id.avatar);
        lineSpaceRank = findViewById(R.id.line_space_rank);
        txtNumberRank = findViewById(R.id.txt_number_rank);
        defaultMargin = getResources().getDimensionPixelSize(R.dimen.dimen_10dp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int BgWitdh = width / 2;
        int BgHeight = width / 5;
        int space = width/30;

        txtNumberRank.measure(MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED);

        avatar.measure((BgHeight-space*2) | MeasureSpec.EXACTLY,
                (BgHeight-space*2) | MeasureSpec.EXACTLY
        );

        lineSpaceRank.measure(
                width - defaultMargin*2 - txtNumberRank.getMeasuredWidth() | MeasureSpec.EXACTLY,
                1 | MeasureSpec.EXACTLY
        );

        txtScore.measure(
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
        );
        txtUserName.measure(
                (width - avatar.getMeasuredWidth() - txtScore.getMeasuredWidth() - 4 * defaultMargin) | MeasureSpec.EXACTLY,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
        );
        setMeasuredDimension(width, BgHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int space = getMeasuredWidth() / 30;

        int l = defaultMargin;
        int t = defaultMargin + (avatar.getMeasuredHeight() - txtNumberRank.getMeasuredHeight()) / 2;;
        txtNumberRank.layout(l,t,l+txtNumberRank.getMeasuredWidth(),t+txtNumberRank.getMeasuredHeight());

        l = txtNumberRank.getMeasuredWidth() + defaultMargin*2;
        t = defaultMargin;
        avatar.layout(l, t, l + avatar.getMeasuredWidth(), t + avatar.getMeasuredHeight());

        l += avatar.getMeasuredWidth() + defaultMargin;
        t += (avatar.getMeasuredHeight() - txtUserName.getMeasuredHeight()) / 2;
        txtUserName.layout(l, t, l + txtUserName.getMeasuredWidth(), t + txtUserName.getMeasuredHeight());

        l = getMeasuredWidth() - txtScore.getMeasuredWidth() - defaultMargin;
        t = defaultMargin + (avatar.getMeasuredHeight() - txtScore.getMeasuredHeight()) / 2;
        txtScore.layout(l, t, l + txtScore.getMeasuredWidth(), t + txtScore.getMeasuredHeight());

        l = defaultMargin*2 + txtNumberRank.getMeasuredWidth();
        t = getMeasuredHeight()-1;
        lineSpaceRank.layout(l,t,l+lineSpaceRank.getMeasuredWidth(),t+lineSpaceRank.getMeasuredHeight());
    }
}
