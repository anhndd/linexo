package vn.edu.hcmut.linexo.data.repository;

import io.reactivex.Observable;
import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Message;

public interface MessageRepository {

    Observable<Message> getNetworkMessage(int roomNumber);
    Single<Boolean> setNetworkMessage(int roomNumber, Message message);

}
