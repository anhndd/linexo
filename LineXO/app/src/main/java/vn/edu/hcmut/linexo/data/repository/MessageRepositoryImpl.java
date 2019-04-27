package vn.edu.hcmut.linexo.data.repository;

import io.reactivex.Observable;
import io.reactivex.Single;
import vn.edu.hcmut.linexo.data.cache.CacheSource;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.presentation.model.Message;

public class MessageRepositoryImpl implements MessageRepository {
    private NetworkSource networkSource;
    private CacheSource cacheSource;

    public MessageRepositoryImpl(NetworkSource networkSource, CacheSource cacheSource) {
        this.networkSource = networkSource;
        this.cacheSource = cacheSource;
    }


    @Override
    public Observable<Message> getNetworkMessage(Integer roomNumber, String uid) {
        return networkSource.getMessage(roomNumber, uid);
    }

    @Override
    public Single<Boolean> setNetworkMessage(Integer roomNumber, Message message) {
        return networkSource.setMessage(roomNumber, message);
    }
}
