package vn.edu.hcmut.linexo.data.network;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Message;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Optional;

public interface NetworkSource {

    Observable<List<Room>> getListRoom();
    Single<String> setRoom(Room room);
    Single<Boolean> updateRoom(Room room);
    Observable<Room> getRoom(String roomId);

    Single<Optional<User>> getUser(String uid);
    Single<Boolean> setUser(User user);
    Single<List<User>> getScoreTable();

    Observable<Message> getMessage(String roomId);
    Single<Boolean> setMessage(int roomNumber, Message message);
}
