package vn.edu.hcmut.linexo.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Session;

public class SharedPreferencesDB  implements CacheSource {

    private final String FILENAME = "linexo";

    private SharedPreferences   sharedPref;
    private Editor              editor;

    public SharedPreferencesDB(Context context) {
        sharedPref  = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor      = sharedPref.edit();
    }

    @Override
    public Single<Session> getSession() {
        return Single.create(emitter -> {
            emitter.onSuccess(new Session(0));
        });
    }

    @Override
    public Single<Boolean> setSession(Session sess) {
        return null;
    }
}
