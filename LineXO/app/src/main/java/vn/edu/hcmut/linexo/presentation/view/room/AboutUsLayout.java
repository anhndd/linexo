package vn.edu.hcmut.linexo.presentation.view.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import vn.edu.hcmut.linexo.R;

public class AboutUsLayout extends FrameLayout {

    private int defaultMarrgin;

    private View backround;
    private View ciHung;
    private View txtHungName;
    private View txtHungRole;

    public AboutUsLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public AboutUsLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AboutUsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        defaultMarrgin = getResources().getDimensionPixelSize(R.dimen.dimen_10dp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        backround   = findViewById(R.id.background);
        ciHung      = findViewById(R.id.ci_hung);
        txtHungName = findViewById(R.id.txt_hung_name);
        txtHungRole = findViewById(R.id.txt_hung_role);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width  = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);

        backround.measure(
                width | MeasureSpec.EXACTLY,
                (width / 2) | MeasureSpec.EXACTLY
        );

        ciHung.measure(
                (width / 5) | MeasureSpec.EXACTLY,
                (width / 5) | MeasureSpec.EXACTLY
        );

        txtHungRole.measure(
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
        );

        txtHungName.measure(
                (width - ciHung.getMeasuredWidth() - txtHungRole.getMeasuredWidth()  - 4 * defaultMarrgin) | MeasureSpec.EXACTLY,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
        );

        setMeasuredDimension(
                width,
                backround.getMeasuredHeight() + ciHung.getMeasuredHeight() + 2 * defaultMarrgin
        );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = 0;
        int t = 0;
        backround.layout(l, t, l + backround.getMeasuredWidth(), t + backround.getMeasuredHeight());

        l += defaultMarrgin;
        t += backround.getMeasuredHeight() + defaultMarrgin;
        ciHung.layout(l, t, l + ciHung.getMeasuredWidth(), t + ciHung.getMeasuredHeight());

        l += ciHung.getMeasuredWidth() + defaultMarrgin;
        t += (ciHung.getMeasuredHeight() - txtHungName.getMeasuredHeight()) / 2;
        txtHungName.layout(l, t, l + txtHungName.getMeasuredWidth(), t + txtHungName.getMeasuredHeight());

        l += txtHungName.getMeasuredWidth() + defaultMarrgin;
        txtHungRole.layout(l, t, l + txtHungRole.getMeasuredWidth(), t + txtHungRole.getMeasuredHeight());
    }
}
