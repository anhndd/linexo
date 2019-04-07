package vn.edu.hcmut.linexo.data.repository;

import java.util.List;
import java.util.Optional;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Room;

public interface RoomRepository {
    Single<Optional<List<Room>>> getListRooms();
    void setNetworkRoom(Room room);
}
