package vn.edu.hcmut.linexo.data.repository;


import io.reactivex.Single;
import vn.edu.hcmut.linexo.data.cache.CacheSource;
import vn.edu.hcmut.linexo.data.local.LocalSource;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Optional;

public class UserRepositoryImpl implements UserRepository{

    private NetworkSource networkSource;
    private CacheSource cacheSource;
    private LocalSource localSource;

    public UserRepositoryImpl(NetworkSource networkSource, CacheSource cacheSource, LocalSource localSource) {
        this.networkSource = networkSource;
        this.cacheSource = cacheSource;
        this.localSource = localSource;
    }

//    @Override
//    public Single<Optional<User>> getNetworkUser(String uid) {
//        return networkSource.getUser(uid);
//    }

    @Override
    public Single<Optional<User>> getCacheUser() {
        return cacheSource.getUser();
    }

    @Override
    public void setCacheUser(User user) {
        cacheSource.setUser(user);
    }
}
