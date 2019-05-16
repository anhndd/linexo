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

    private View objBackground;
    private View objLineX;
    private View objLogo;
    private View ciHung;
    private View txtHungName;
    private View txtHungRole;
    private View objHungLine;
    private View ciAnh;
    private View txtAnhName;
    private View txtAnhRole;
    private View objAnhLine;
    private View ciTrong;
    private View txtTrongName;
    private View txtTrongRole;
    private View objTrongLine;
    private View ciSang;
    private View txtSangName;
    private View txtSangRole;

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
        objBackground   = findViewById(R.id.obj_background);
        objLineX        = findViewById(R.id.obj_line_x);
        objLogo         = findViewById(R.id.obj_logo);
        ciHung          = findViewById(R.id.ci_hung);
        txtHungName     = findViewById(R.id.txt_hung_name);
        txtHungRole     = findViewById(R.id.txt_hung_role);
        objHungLine     = findViewById(R.id.obj_hung_line);
        ciAnh           = findViewById(R.id.ci_anh);
        txtAnhName      = findViewById(R.id.txt_anh_name);
        txtAnhRole      = findViewById(R.id.txt_anh_role);
        objAnhLine      = findViewById(R.id.obj_anh_line);
        ciTrong         = findViewById(R.id.ci_trong);
        txtTrongName    = findViewById(R.id.txt_trong_name);
        txtTrongRole    = findViewById(R.id.txt_trong_role);
        objTrongLine    = findViewById(R.id.obj_trong_line);
        ciSang          = findViewById(R.id.ci_sang);
        txtSangName     = findViewById(R.id.txt_sang_name);
        txtSangRole     = findViewById(R.id.txt_sang_role);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width  = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);

        objBackground.measure(
                width | MeasureSpec.EXACTLY,
                (width / 2) | MeasureSpec.EXACTLY
        );
        objLineX.measure(
                (objBackground.getMeasuredHeight() / 4 * 175 / 75) | MeasureSpec.EXACTLY,
                (objBackground.getMeasuredHeight() / 4) | MeasureSpec.EXACTLY
        );
        objLogo.measure(
                objLineX.getMeasuredHeight() | MeasureSpec.EXACTLY,
                objLineX.getMeasuredHeight() | MeasureSpec.EXACTLY
        );

        ciHung.measure(
                (width / 6) | MeasureSpec.EXACTLY,
                (width / 6) | MeasureSpec.EXACTLY
        );
        txtHungRole.measure(
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
        );
        txtHungName.measure(
                (width - ciHung.getMeasuredWidth() - txtHungRole.getMeasuredWidth()  - 4 * defaultMarrgin) | MeasureSpec.EXACTLY,
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED
        );
        objHungLine.measure(
                (width - ciHung.getMeasuredWidth() - 2 * defaultMarrgin) | MeasureSpec.EXACTLY,
                1 | MeasureSpec.EXACTLY
        );

        ciAnh.measure(
                ciHung.getMeasuredWidth() | MeasureSpec.EXACTLY,
                ciHung.getMeasuredHeight() | MeasureSpec.EXACTLY
        );
        txtAnhRole.measure(
                MeasureSpec.UNSPECIFIED | MeasureSpec.UNSPECIFIED,
                txtHungRole.getMeasuredHeight() | MeasureSpec.EXACTLY
        );
        txtAnhName.measure(
                (width - ciHung.getMeasuredWidth() - txtAnhRole.getMeasuredWidth()  - 4 * defaultMarrgin) | MeasureSpec.EXACTLY,
                txtHungName.getMeasuredHeight() | MeasureSpec.UNSPECIFIED
        );
        objAnhLine.measure(
                objHungLine.getMeasuredWidth() | MeasureSpec.EXACTLY,
                objHungLine.getMeasuredHeight() | MeasureSpec.EXACTLY
        );

        ciTrong.measure(
                ciHung.getMeasuredWidth() | MeasureSpec.EXACTLY,
                ciHung.getMeasuredHeight() | MeasureSpec.EXACTLY
        );
        txtTrongRole.measure(
                txtAnhRole.getMeasuredWidth() | MeasureSpec.EXACTLY,
                txtAnhRole.getMeasuredHeight() | MeasureSpec.EXACTLY
        );
        txtTrongName.measure(
                txtAnhName.getMeasuredWidth() | MeasureSpec.EXACTLY,
                txtAnhName.getMeasuredHeight() | MeasureSpec.EXACTLY
        );

        objTrongLine.measure(
                objHungLine.getMeasuredWidth() | MeasureSpec.EXACTLY,
                objHungLine.getMeasuredHeight() | MeasureSpec.EXACTLY
        );

        ciSang.measure(
                ciHung.getMeasuredWidth() | MeasureSpec.EXACTLY,
                ciHung.getMeasuredHeight() | MeasureSpec.EXACTLY
        );
        txtSangRole.measure(
                txtAnhRole.getMeasuredWidth() | MeasureSpec.EXACTLY,
                txtAnhRole.getMeasuredHeight() | MeasureSpec.EXACTLY
        );
        txtSangName.measure(
                txtAnhName.getMeasuredWidth() | MeasureSpec.EXACTLY,
                txtAnhName.getMeasuredHeight() | MeasureSpec.EXACTLY
        );

        setMeasuredDimension(
                width,
                objBackground.getMeasuredHeight()
                        + 4 * ciHung.getMeasuredHeight()
                        + 8 * defaultMarrgin
                        + 3 * objHungLine.getMeasuredHeight()
        );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = 0;
        int t = 0;
        layoutView(objBackground, l, t);

        l = (getMeasuredWidth() - objLineX.getMeasuredWidth() - objLogo.getMeasuredWidth()) / 2;
        t = (objBackground.getMeasuredHeight() - objLineX.getMeasuredHeight()) / 2;
        layoutView(objLineX, l, t);

        l += objLineX.getMeasuredWidth();
        layoutView(objLogo, l, t);

        l = defaultMarrgin;
        t = objBackground.getMeasuredHeight() + defaultMarrgin;
        layoutView(ciHung, l, t);

        l += ciHung.getMeasuredWidth() + defaultMarrgin;
        t += (ciHung.getMeasuredHeight() - txtHungName.getMeasuredHeight()) / 2;
        layoutView(txtHungName, l, t);

        l += txtHungName.getMeasuredWidth() + defaultMarrgin;
        layoutView(txtHungRole, l, t);

        l = txtHungName.getLeft();
        t = ciHung.getBottom() + defaultMarrgin;
        layoutView(objHungLine, l, t);

        l = defaultMarrgin;
        t += objHungLine.getMeasuredHeight() + defaultMarrgin;
        layoutView(ciAnh, l, t);

        l = txtHungName.getLeft();
        t += (ciHung.getMeasuredHeight() - txtHungName.getMeasuredHeight()) / 2;
        layoutView(txtAnhName, l, t);

        l += txtAnhName.getMeasuredWidth() + defaultMarrgin;
        layoutView(txtAnhRole, l, t);

        l = objHungLine.getLeft();
        t = ciAnh.getBottom() + defaultMarrgin;
        layoutView(objAnhLine, l, t);

        l = defaultMarrgin;
        t += objAnhLine.getMeasuredHeight() + defaultMarrgin;
        layoutView(ciTrong, l, t);

        l = txtHungName.getLeft();
        t += (ciHung.getMeasuredHeight() - txtHungName.getMeasuredHeight()) / 2;
        layoutView(txtTrongName, l, t);

        l = txtAnhRole.getLeft();
        layoutView(txtTrongRole, l, t);

        l = objHungLine.getLeft();
        t = ciTrong.getBottom() + defaultMarrgin;
        layoutView(objTrongLine, l, t);

        l = defaultMarrgin;
        t += objTrongLine.getMeasuredHeight() + defaultMarrgin;
        layoutView(ciSang, l, t);

        l = txtHungName.getLeft();
        t += (ciHung.getMeasuredHeight() - txtHungName.getMeasuredHeight()) / 2;
        layoutView(txtSangName, l, t);

        l = txtAnhRole.getLeft();
        layoutView(txtSangRole, l, t);
    }

    private void layoutView(View view, int left, int top) {
        view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
    }
}
