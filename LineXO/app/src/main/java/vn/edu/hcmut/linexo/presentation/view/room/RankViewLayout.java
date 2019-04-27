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

/**
 * Created by Anh on 3/12/2019.
 */

public class RankViewLayout extends FrameLayout {

    private View txtName;
    private View txtScore;
    private View bgRankItem;

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
        txtName = findViewById(R.id.user_name);
        txtScore = findViewById(R.id.user_score);
        bgRankItem = findViewById(R.id.bg_rank_item);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int BgWitdh = width / 2;
        int BgHeight = width / 5;
        int space = width/30;

        bgRankItem.measure((width-space*2) | MeasureSpec.EXACTLY,
                (BgHeight-space) | MeasureSpec.EXACTLY
        );

        txtName.measure(
                ((width*2 / 3)-space) | MeasureSpec.EXACTLY,
                (BgHeight-space) | MeasureSpec.EXACTLY
        );

        txtScore.measure(
                ((width / 3)-space*2) | MeasureSpec.EXACTLY,
                (BgHeight-space) | MeasureSpec.EXACTLY
        );

        setMeasuredDimension(width, BgHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int space = getMeasuredWidth() / 30;

        int l = (getMeasuredWidth() - bgRankItem.getMeasuredWidth())/2;
        int t = 0;
        bgRankItem.layout(l,t,l+bgRankItem.getMeasuredWidth(),t+bgRankItem.getMeasuredHeight());
        l = 0;
        txtName.layout(l+space, t, l + txtName.getMeasuredWidth(), t + txtScore.getMeasuredHeight());

        l = getMeasuredWidth() - space*2 - txtScore.getMeasuredWidth();
        txtScore.layout(l, t, l + txtScore.getMeasuredWidth(), t + txtScore.getMeasuredHeight());
    }
}
