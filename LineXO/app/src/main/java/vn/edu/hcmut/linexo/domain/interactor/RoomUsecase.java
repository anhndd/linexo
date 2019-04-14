package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import vn.edu.hcmut.linexo.data.repository.RoomRepository;
import vn.edu.hcmut.linexo.data.repository.UserRepository;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.Optional;

public class RoomUsecase extends AbstractUsecase {
    private UserRepository userRepository;
    private RoomRepository roomRepository;

    public RoomUsecase(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
        switch (flag) {
            case Event.LOGIN_INFO:
                addTask(userRepository
                        .getCacheUser()
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith((DisposableSingleObserver<Optional<User>>) observer));
                break;
            case Event.LOGOUT:
                userRepository.setCacheUser(null);
                break;
            case Event.LOAD_LIST_ROOM:
                addTask(roomRepository
                        .getListRooms()
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith((DisposableObserver<List<Room>>)observer));
                break;
        }
    }
}
