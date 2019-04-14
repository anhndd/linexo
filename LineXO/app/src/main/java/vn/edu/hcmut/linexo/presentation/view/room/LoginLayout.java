package vn.edu.hcmut.linexo.presentation.view.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import vn.edu.hcmut.linexo.R;

public class LoginLayout extends FrameLayout {

    private int defaultMargin;

    private View title;
    private View line;
    private View google;
    private View facebook;

    public LoginLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public LoginLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoginLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        defaultMargin = getResources().getDimensionPixelSize(R.dimen.dimen_10dp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        title    = findViewById(R.id.txt_title);
        line     = findViewById(R.id.line);
        google   = findViewById(R.id.btn_google);
        facebook = findViewById(R.id.btn_facebook);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width  = MeasureSpec.getSize(widthMeasureSpec) * 5 / 6;
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        title.measure(
                width | MeasureSpec.EXACTLY,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
        );
        line.measure(
                width | MeasureSpec.EXACTLY,
                1 | MeasureSpec.EXACTLY
        );
        google.measure(
                (width - 2 * defaultMargin) | MeasureSpec.EXACTLY,
                (width - 2 * defaultMargin) / 5 | MeasureSpec.EXACTLY
        );
        facebook.measure(
                (width - 2 * defaultMargin) | MeasureSpec.EXACTLY,
                (width - 2 * defaultMargin) / 5 | MeasureSpec.EXACTLY
        );
        setMeasuredDimension(
                width,
                title.getMeasuredHeight() + line.getMeasuredHeight() + google.getMeasuredHeight() + facebook.getMeasuredHeight() + 3 * defaultMargin
        );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = left;
        int t = top;
        title.layout(l, t, l + title.getMeasuredWidth(), t + title.getMeasuredHeight());

        t += title.getMeasuredHeight();
        line.layout(l, t, l + line.getMeasuredWidth(), t + line.getMeasuredHeight());

        t += line.getMeasuredHeight() + defaultMargin;
        l += defaultMargin;
        google.layout(l, t, l + google.getMeasuredWidth(), t + google.getMeasuredHeight());

        t += google.getMeasuredHeight() + defaultMargin;
        facebook.layout(l, t, l + facebook.getMeasuredWidth(), t + facebook.getMeasuredHeight());
    }
}
