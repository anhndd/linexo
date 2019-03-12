package vn.edu.hcmut.linexo.presentation.custom;

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

public class SplashLayout extends FrameLayout {

    private View logo;
    private View loading;

    public SplashLayout(@NonNull Context context) {
        super(context);
    }

    public SplashLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SplashLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        logo    = findViewById(R.id.logo);
        loading = findViewById(R.id.loading);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width  = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        logo.measure(
                (width / 8) | MeasureSpec.EXACTLY,
                (width / 8) | MeasureSpec.EXACTLY
        );
        loading.measure(
                (width / 5) | MeasureSpec.EXACTLY,
                (width / 5 / 9) | MeasureSpec.EXACTLY
        );
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = (getMeasuredWidth() - logo.getMeasuredWidth()) / 2;
        int t = (getMeasuredHeight() - logo.getMeasuredHeight()) / 2;
        logo.layout(l, t, l + logo.getMeasuredWidth(), t + logo.getMeasuredHeight());
        l = (getMeasuredWidth() - loading.getMeasuredWidth()) / 2;
        t += logo.getMeasuredHeight() + 4 * loading.getMeasuredHeight();
        loading.layout(l, t, l + loading.getMeasuredWidth(), t + loading.getMeasuredHeight());
    }
}
