package vn.edu.hcmut.linexo.presentation.view.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import vn.edu.hcmut.linexo.R;

public class RankLayout extends FrameLayout {

    private int defaultMargin;

    private View lstRank;
    private View firstBackground;
    private View civFirstAvatar;
    private View kingIcon;
    private View txtFirstScore;

    public RankLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public RankLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RankLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        defaultMargin = getResources().getDimensionPixelSize(R.dimen.dimen_5dp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        lstRank = findViewById(R.id.lst_rank);
        firstBackground = findViewById(R.id.first_background);
        civFirstAvatar = findViewById(R.id.civ_first_avatar);
        kingIcon = findViewById(R.id.king_icon);
        txtFirstScore = findViewById(R.id.txt_first_score);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);

        firstBackground.measure(width | MeasureSpec.EXACTLY,
                width * 2 / 3 | MeasureSpec.EXACTLY);

        lstRank.measure(
                width | MeasureSpec.EXACTLY,
                height * 8 / 10 - firstBackground.getMeasuredHeight() | MeasureSpec.EXACTLY
        );

        civFirstAvatar.measure(
                firstBackground.getMeasuredHeight() / 4 | MeasureSpec.EXACTLY,
                firstBackground.getMeasuredHeight() / 4 | MeasureSpec.EXACTLY
        );

        kingIcon.measure(
                civFirstAvatar.getMeasuredWidth() / 3 | MeasureSpec.EXACTLY,
                civFirstAvatar.getMeasuredWidth() / 3 | MeasureSpec.EXACTLY
        );

        txtFirstScore.measure(
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
        );

        setMeasuredDimension(
                width,
                firstBackground.getMeasuredHeight() + lstRank.getMeasuredHeight()
        );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = 0;
        int t = 0;
        firstBackground.layout(l,t,l+firstBackground.getMeasuredWidth(),t+firstBackground.getMeasuredHeight());

        l = (firstBackground.getMeasuredWidth()-civFirstAvatar.getMeasuredWidth())/2;
        t = (firstBackground.getMeasuredHeight()-civFirstAvatar.getMeasuredHeight())/2;
        civFirstAvatar.layout(l,t,l+civFirstAvatar.getMeasuredWidth(),t+civFirstAvatar.getMeasuredHeight());

        l = (firstBackground.getMeasuredWidth()-civFirstAvatar.getMeasuredWidth())/2 + kingIcon.getMeasuredWidth();
        t = (firstBackground.getMeasuredHeight()-civFirstAvatar.getMeasuredHeight())/2 - kingIcon.getMeasuredHeight();
        kingIcon.layout(l,t,l+kingIcon.getMeasuredWidth(),t+kingIcon.getMeasuredHeight());

        l = (firstBackground.getMeasuredWidth()-txtFirstScore.getMeasuredWidth())/2;
        t = civFirstAvatar.getBottom() + txtFirstScore.getMeasuredHeight()/2;
        txtFirstScore.layout(l,t,l+txtFirstScore.getMeasuredWidth(),t+txtFirstScore.getMeasuredHeight());

        l = 0;
        t = firstBackground.getHeight();
        lstRank.layout(l, t, l + lstRank.getMeasuredWidth(), t + lstRank.getMeasuredHeight());
    }
}
