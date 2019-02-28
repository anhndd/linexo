package vn.edu.hcmut.linexo.data.repository;

import vn.edu.hcmut.linexo.data.cache.CacheSource;
import vn.edu.hcmut.linexo.data.local.LocalSource;
import vn.edu.hcmut.linexo.data.network.NetworkSource;

public class UserRepositoryImpl implements UserRepository{

    private CacheSource   cacheSource;
    private LocalSource   localSource;
    private NetworkSource networkSource;

    public UserRepositoryImpl(CacheSource cacheSource,
                              LocalSource localSource,
                              NetworkSource networkSource) {
        this.cacheSource   = cacheSource;
        this.localSource   = localSource;
        this.networkSource = networkSource;
    }
}
