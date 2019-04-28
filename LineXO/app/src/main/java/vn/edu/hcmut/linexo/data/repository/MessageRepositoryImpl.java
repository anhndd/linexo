package vn.edu.hcmut.linexo.data.repository;

import io.reactivex.Observable;
import io.reactivex.Single;
import vn.edu.hcmut.linexo.data.cache.CacheSource;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.presentation.model.Message;

public class MessageRepositoryImpl implements MessageRepository {
    private NetworkSource networkSource;

    public MessageRepositoryImpl(NetworkSource networkSource) {
        this.networkSource = networkSource;
    }


    @Override
    public Observable<Message> getNetworkMessage(int roomNumber) {
        return networkSource.getMessage(roomNumber);
    }

    @Override
    public Single<Boolean> setNetworkMessage(int roomNumber, Message message) {
        return networkSource.setMessage(roomNumber, message);
    }
}
