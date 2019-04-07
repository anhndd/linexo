package vn.edu.hcmut.linexo.data.cache;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Session;
import vn.edu.hcmut.linexo.presentation.model.User;

public interface CacheSource {

    Single<Session> getSession();
    Single<Boolean> setSession(Session session);

    Single<User> getUser();
    void setUser(User user);

}
