package vn.edu.hcmut.linexo.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesDB  implements CacheSource {

    private final String FILENAME = "linexo";

    private SharedPreferences   sharedPref;
    private Editor              editor;

    public SharedPreferencesDB(Context context) {
        sharedPref  = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor      = sharedPref.edit();
    }
}
