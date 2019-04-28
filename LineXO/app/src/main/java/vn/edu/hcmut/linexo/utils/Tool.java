package vn.edu.hcmut.linexo.utils;

import android.app.Activity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Tool {

    /**
     * Hide the soft keyboard.
     * @param activity  activity that need to hide keyboard.
     */
    public static void hideSoftKeyboard(Activity activity) {
        Log.e("Test", "hide");
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

    /**
     * Force given {@code PopupMenu} show icon.
     */
    public static void forcePopupMenuShowIcon(PopupMenu popupMenu) {
        try {
            Field mPopup = PopupMenu.class.getDeclaredField("mPopup");
            mPopup.setAccessible(true);
            Object menuHelper = mPopup.get(popupMenu);
            menuHelper
                    .getClass()
                    .getDeclaredMethod("setForceShowIcon", boolean.class)
                    .invoke(menuHelper, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
