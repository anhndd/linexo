package vn.edu.hcmut.linexo.presentation.view.play;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import vn.edu.hcmut.linexo.R;

public class ResultLayout extends FrameLayout {

    private View icCup;
    private View txtTime;

    public ResultLayout(@NonNull Context context) {
        super(context);
    }

    public ResultLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ResultLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        icCup   = findViewById(R.id.endgame_status);
        txtTime = findViewById(R.id.txt_time);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width  = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);

        icCup.measure(
                (width / 2) | MeasureSpec.EXACTLY,
                (width / 2) | MeasureSpec.EXACTLY
        );

        txtTime.measure(
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
        );

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = (getMeasuredWidth() - icCup.getMeasuredWidth()) / 2;
        int t = (getMeasuredHeight() - icCup.getMeasuredHeight() - txtTime.getMeasuredHeight()) / 2;
        icCup.layout(l, t , l + icCup.getMeasuredWidth(), t + icCup.getMeasuredHeight());

        l = (getMeasuredWidth() - txtTime.getMeasuredWidth()) / 2;
        t += icCup.getMeasuredHeight();
        txtTime.layout(l, t, l + txtTime.getMeasuredWidth(), t + txtTime.getMeasuredHeight());
    }
}
