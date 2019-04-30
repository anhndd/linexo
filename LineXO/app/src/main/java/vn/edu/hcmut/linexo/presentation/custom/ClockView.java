package vn.edu.hcmut.linexo.presentation.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class ClockView extends android.support.v7.widget.AppCompatTextView{

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCountTime(long time) {
        long minute = time / 60;
        long second = time % 60;
        String displayTime = "";
        if (minute == 0) {
            displayTime += "00";
        } else if (minute < 10) {
            displayTime += ("0" + minute);
        } else {
            displayTime += minute;
        }
        displayTime += ":";
        if (second < 10) {
            displayTime += ("0" + second);
        } else {
            displayTime += second;
        }
        setText(displayTime);
    }
}
