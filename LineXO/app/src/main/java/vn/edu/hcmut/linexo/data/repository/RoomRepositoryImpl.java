package vn.edu.hcmut.linexo.data.repository;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import vn.edu.hcmut.linexo.data.cache.CacheSource;
import vn.edu.hcmut.linexo.data.local.LocalSource;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.utils.Optional;

public class RoomRepositoryImpl implements RoomRepository{
    private NetworkSource networkSource;

    public RoomRepositoryImpl(NetworkSource networkSource) {
        this.networkSource = networkSource;
    }

    @Override
    public Observable<List<Room>> getListRooms() {
        return networkSource.getRoom();
    }

    @Override
    public Single<Boolean> setNetworkRoom(Room room) {
        return networkSource.setRoom(room);
    }
}


