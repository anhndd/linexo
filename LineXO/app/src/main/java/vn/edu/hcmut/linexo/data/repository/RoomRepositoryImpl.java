package vn.edu.hcmut.linexo.data.repository;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.presentation.model.Room;

public class RoomRepositoryImpl implements RoomRepository{
    private NetworkSource networkSource;

    public RoomRepositoryImpl(NetworkSource networkSource) {
        this.networkSource = networkSource;
    }

    @Override
    public Observable<List<Room>> getListRooms() {
        return networkSource.getListRoom();
    }

    @Override
    public Single<String> setNetworkRoom(Room room) {
        return networkSource.setRoom(room);
    }

    @Override
    public Single<Boolean> updateNetworkRoom(Room room) {
        return networkSource.updateRoom(room);
    }

    @Override
    public Observable<Room> getRoom(String roomId) {
        return networkSource.getRoom(roomId);
    }
}


