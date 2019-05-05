package vn.edu.hcmut.linexo.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Path;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Session;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Optional;

public class SharedPreferencesDB  implements CacheSource {

    private final String FILENAME = "linexo";
    private final String NODE_NAME = "users";
    private final String USER_ID = "";
    private final String NAME = "name";
    private final String SCORE = "score";
    private final String AVATAR = "avatar";
    private final String TIME = "time";
    private final String EMAIL = "email";


    private SharedPreferences   sharedPref;
    private Editor              editor;

    public SharedPreferencesDB(Context context) {
        sharedPref  = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor      = sharedPref.edit();
    }

    @Override
    public Single<Optional<User>> getUser() {
        return Single.create(emitter -> {
            String uid = sharedPref.getString(USER_ID, "");
            if (!uid.isEmpty()) {
                User user = new User(
                        sharedPref.getString(USER_ID, ""),
                        sharedPref.getString(EMAIL, ""),
                        sharedPref.getString(AVATAR, ""),
                        sharedPref.getString(NAME, ""),
                        sharedPref.getInt(SCORE, 0),
                        sharedPref.getLong(TIME, 0)
                );
                emitter.onSuccess(Optional.of(user));
            }
            else {
                emitter.onSuccess(Optional.empty());
            }
        });
    }

    @Override
    public Single<Boolean> setUser(User user) {
        if (user != null){
            return Single.create(emitter -> {
                editor.putString(NAME, user.getName());
                editor.putString(AVATAR, user.getAvatar());
                editor.putInt(SCORE, user.getScore());
                editor.putString(USER_ID, user.getUid());
                editor.putLong(TIME, user.getTime());
                editor.putString(EMAIL, user.getEmail());
                editor.commit();
                emitter.onSuccess(true);
            });
        }
        else {
            return Single.create(emitter -> {
                editor.remove(NAME);
                editor.remove(AVATAR);
                editor.remove(SCORE);
                editor.remove(USER_ID);
                editor.remove(TIME);
                editor.remove(EMAIL);
                editor.commit();
                emitter.onSuccess(false);
            });
        }
    }
}
