package vn.edu.hcmut.linexo.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class Tool {

    /**
     * Hide the soft keyboard.
     * @param activity  activity that need to hide keyboard.
     */
    public static void hideSoftKeyboard(Activity activity) {
        if(activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard.
     * @param view      object that need to show keyboard.
     * @param activity  activity that need to show keyboard.
     */
    public static void showSoftKeyboard(View view, Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }
}
