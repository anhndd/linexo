package vn.edu.hcmut.linexo.data.repository;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.utils.Optional;

public interface RoomRepository {
    Observable<List<Room>> getListRooms();
    Single<Boolean> setNetworkRoom(Room room);
}
