package vn.edu.hcmut.linexo.presentation.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import vn.edu.hcmut.linexo.R;

public class RoomNumberView extends android.support.v7.widget.AppCompatTextView {

    public RoomNumberView(Context context) {
        super(context);
    }

    public RoomNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNumber(int number) {
        String strNumber = "";
        if (number == 0) {
            strNumber = getResources().getString(R.string.room_ai);
        } else if (number < 10) {
            strNumber = "00" + number;
        } else if (number < 100) {
            strNumber = "0" + number;
        } else if (number < 1000) {
            strNumber += number;
        }
        setText(strNumber);
    }
}
