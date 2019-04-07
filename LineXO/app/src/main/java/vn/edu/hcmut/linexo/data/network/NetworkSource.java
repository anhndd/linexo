package vn.edu.hcmut.linexo.data.network;

import java.util.List;
import java.util.Optional;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Room;

public interface NetworkSource {

    Single<List<Board>> getBoard();
    Single<Optional<List<Room>>> getRoom();
    void setRoom(Room room);
}
