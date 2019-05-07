package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import vn.edu.hcmut.linexo.data.repository.RoomRepository;
import vn.edu.hcmut.linexo.data.repository.UserRepository;
import vn.edu.hcmut.linexo.presentation.model.Room;

import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.presentation.view.room.RankItem;
import vn.edu.hcmut.linexo.presentation.view.room.RoomItem;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.Optional;

public class RoomUsecase extends AbstractUsecase {
    private UserRepository userRepository;
    private RoomRepository roomRepository;

    public RoomUsecase(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    private Observable<List<RoomItem>> buildRoomObserverable() {
        return Observable.create(emitter -> {
            List<RoomItem> roomItems = new ArrayList<>();
            RoomItem roomItem = new RoomItem("AI",0, "LineXOAI", null, false);
            roomItems.add(0, roomItem);
            emitter.onNext(roomItems);
        });
    }

    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
        switch (flag) {
            case Event.LOGIN_USER:
                addTask(userRepository
                        .getNetworkUser(((User) params[0]).getUid())
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .map(userOptional -> {
                            if (userOptional.isPresent()) {
                                userRepository.setCacheUser(userOptional.get()).subscribe();
                                return userOptional.get();
                            } else {
                                User user = (User) params[0];
                                user.setScore(0);
                                userRepository.setCacheUser(user).subscribe();
                                userRepository.setNetworkUser(user).subscribe();
                                return user;
                            }
                        })
                        .subscribeWith((DisposableSingleObserver<User>) observer));
                break;
            case Event.LOGOUT:
                userRepository.setCacheUser(null);
                break;
            case Event.LOAD_LIST_ROOM:
                if (!(boolean) params[0]) {
                    addTask(buildRoomObserverable()
                            .subscribeOn(getSubscribeScheduler())
                            .observeOn(getObserveScheduler())
                            .subscribeWith((DisposableObserver<List<RoomItem>>) observer));
                } else {
                    addTask(roomRepository
                            .getListRooms()
                            .subscribeOn(getSubscribeScheduler())
                            .observeOn(getObserveScheduler())
                            .map(rooms -> {
                                List<RoomItem> roomItems = new ArrayList<>();
                                for (Room room : rooms) {
                                    RoomItem roomItem = new RoomItem(room.getRoom_id(),room.getRoom_number().intValue(), room.getUser_1().getAvatar(), room.getUser_2() == null ? null : room.getUser_2().getAvatar(), room.getIs_private().booleanValue());
                                    roomItems.add(roomItem);
                                }
                                RoomItem roomItem = new RoomItem("AI",0, "LineXOAI", null, false);
                                roomItems.add(0, roomItem);

                                return roomItems;
                            })
                            .subscribeWith((DisposableObserver<List<RoomItem>>) observer));
                }
                break;
            case Event.SHOW_RANK_DIALOG: {
                addTask(userRepository
                        .getNetworkScoreTable()
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .map(users -> {
                            List<RankItem> rankItems = new ArrayList<>();
                            for (int i = users.size() - 1; i > -1; i--) {
                                RankItem rankItem = new RankItem(users.get(i).getUid(), users.get(i).getName(), users.get(i).getScore(), users.get(i).getAvatar(), (users.size() - i ) + "");
                                rankItems.add(rankItem);
                            }
                            return rankItems;
                        })
                        .subscribeWith((DisposableSingleObserver<List<RankItem>>) observer));
                break;
            }
            case Event.LOGIN_INFO:{
                addTask(userRepository
                        .getCacheUser()
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith((DisposableSingleObserver<Optional<User>>) observer));
                break;
            }
            case Event.CREATE_ROOM:{
                addTask(roomRepository
                        .setNetworkRoom(((Room) params[0]))
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith((DisposableSingleObserver<String>) observer));
                break;
            }
        }
    }
}
