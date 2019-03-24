package vn.edu.hcmut.linexo.data.repository;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Session;

public interface SessionRepository {

    Single<Session> getCacheSession();
    Single<Boolean> setCacheSession(Session session);
}
