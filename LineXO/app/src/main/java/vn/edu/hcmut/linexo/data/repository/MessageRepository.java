package vn.edu.hcmut.linexo.data.repository;

import io.reactivex.Observable;
import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Message;

public interface MessageRepository {

    Observable<Message> getNetworkMessage(Integer roomNumber, String uid);
    Single<Boolean> setNetworkMessage(Integer roomNumber, Message message);

}
