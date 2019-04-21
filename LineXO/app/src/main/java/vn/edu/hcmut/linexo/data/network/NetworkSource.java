package vn.edu.hcmut.linexo.data.network;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Optional;

public interface NetworkSource {

    Single<List<Board>> getBoard();
    Observable<List<Room>> getRoom();
    Single<Boolean> setRoom(Room room);

    Single<Optional<User>> getUser(String uid);
    Single<Boolean> setUser(User user);
}
