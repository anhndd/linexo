package vn.edu.hcmut.linexo.data.repository;

import java.util.List;
import java.util.Optional;

import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import vn.edu.hcmut.linexo.data.cache.CacheSource;
import vn.edu.hcmut.linexo.data.local.LocalSource;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.presentation.model.Room;

public class RoomRepositoryImpl implements RoomRepository{
    private NetworkSource networkSource;

    @Override
    public DisposableObserver<Optional<List<Room>>> getListRooms() {
        return networkSource.getRoom();
    }

    @Override
    public void setNetworkRoom(Room room) {
        networkSource.setRoom(room);
    }
}


