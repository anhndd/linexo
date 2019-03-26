package vn.edu.hcmut.linexo.presentation.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.utils.GlideApp;

public class CircleImageView extends AppCompatImageView {

    private GradientDrawable drawable;

    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //background
        drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(Float.MAX_VALUE);
        setBackground(drawable);
        setScaleType(ScaleType.CENTER_CROP);
        setClipToOutline(true);
        setPadding(5,5,5,5);
        //Outline
        drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(Float.MAX_VALUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPaddingTop() > 0) {
            drawable.setStroke(getPaddingTop(), Color.WHITE);
            drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            drawable.draw(canvas);
        }
    }

    public void setImageFromObject(Object object) {
        GlideApp
                .with(getContext())
                .load(object)
                .placeholder(R.drawable.img_avatar_holder)
                .into(this);
    }
}
