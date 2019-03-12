package vn.edu.hcmut.linexo.presentation.custom;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class CircleImageView extends AppCompatImageView {

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
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(Float.MAX_VALUE);
        setBackground(shape);
        setScaleType(ScaleType.CENTER_CROP);
        setClipToOutline(true);
    }

    /*public void setImageFromObject(Object object) {
        GlideApp
                .with(getContext())
                .load(object)
                .placeholder(R.drawable.img_avatar_holder)
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                .into(this);
    }*/
}
