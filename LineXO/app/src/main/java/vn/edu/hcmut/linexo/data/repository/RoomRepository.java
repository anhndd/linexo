package vn.edu.hcmut.linexo.data.repository;

import java.util.List;
import java.util.Observable;
import java.util.Optional;

import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import vn.edu.hcmut.linexo.presentation.model.Room;

public interface RoomRepository {
    DisposableObserver<Optional<List<Room>>> getListRooms();
    void setNetworkRoom(Room room);
}
