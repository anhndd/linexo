package vn.edu.hcmut.linexo.utils;

import android.app.Activity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Generate a name tag
     */
    public static String getTagName(String name) {

        String[] words = name.split(" ");

        if (words.length == 1) {
            return "@" + name;
        }

        List<String> wordFilter1 = new ArrayList<>();

        for (String word : words) {
            if (word.equalsIgnoreCase("nguyễn")
                    || word.equalsIgnoreCase("trần")
                    || word.equalsIgnoreCase("lê")) {
                continue;
            }
            wordFilter1.add(word);
        }

        if (wordFilter1.size() == 0) {
            return "@" + words[words.length - 1];
        }

        List<String> wordFilter2 = new ArrayList<>();

        for (String word : wordFilter1) {
            if (word.equalsIgnoreCase("văn")
                    || word.equalsIgnoreCase("thị")) {
                continue;
            }
            wordFilter2.add(word);
        }

        if (wordFilter2.size() == 0) {
            return "@" + wordFilter1.get(wordFilter1.size() - 1);
        }

        List<String> wordFilter3 = new ArrayList<>();

        for (String word : wordFilter2) {
            if (word.equalsIgnoreCase("phạm")
                    || word.equalsIgnoreCase("huỳnh")
                    || word.equalsIgnoreCase("hoàng")
                    || word.equalsIgnoreCase("phan")
                    || word.equalsIgnoreCase("vũ")
                    || word.equalsIgnoreCase("võ")) {
                continue;
            }
            wordFilter3.add(word);
        }

        if (wordFilter3.size() == 0) {
            return "@" + wordFilter2.get(wordFilter2.size() - 1);
        } else {
            return "@" + wordFilter3.get(wordFilter3.size() - 1);
        }
    }
}
