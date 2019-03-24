package vn.edu.hcmut.linexo.data.repository;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.data.cache.CacheSource;
import vn.edu.hcmut.linexo.presentation.model.Session;

public class SessionRepositoryImpl implements SessionRepository {

    private CacheSource cacheSource;

    public SessionRepositoryImpl(CacheSource cacheSource) {
        this.cacheSource = cacheSource;
    }

    @Override
    public Single<Session> getCacheSession() {
        return cacheSource.getSession();
    }

    @Override
    public Single<Boolean> setCacheSession(Session session) {
        return cacheSource.setSession(session);
    }
}
