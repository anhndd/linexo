package vn.edu.hcmut.linexo.data.repository;

import java.util.List;
import io.reactivex.Observable;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.utils.Optional;

public interface RoomRepository {
    Observable<Optional<List<Room>>> getListRooms();
    void setNetworkRoom(Room room);
}
