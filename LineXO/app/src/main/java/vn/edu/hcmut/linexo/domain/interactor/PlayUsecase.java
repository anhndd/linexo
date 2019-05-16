package vn.edu.hcmut.linexo.domain.interactor;

import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import java.util.List;
import java.util.Random;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import vn.edu.hcmut.linexo.data.repository.BoardRepository;
import vn.edu.hcmut.linexo.data.repository.MessageRepository;
import vn.edu.hcmut.linexo.data.repository.RoomRepository;
import vn.edu.hcmut.linexo.data.repository.UserRepository;
import vn.edu.hcmut.linexo.domain.AI.LineXOAlphaBetaSearch;
import vn.edu.hcmut.linexo.domain.AI.LineXOBoard;
import vn.edu.hcmut.linexo.domain.AI.LineXOGame;
import vn.edu.hcmut.linexo.domain.AI.LineXOMove;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Message;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.Optional;
import vn.edu.hcmut.linexo.utils.Tool;

public class PlayUsecase extends AbstractUsecase {

    private final long MOVE_INTERVAL = 500;

    private Room room;
    private String roomId;
    private User user;
    private List<Board> boards;
    private BoardRepository boardRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private MessageRepository messageRepository;
    private Handler playHandler = new Handler();
    private long lastTimeGetMove = 0;
    private boolean allowPushSysstemMessageForViewer = true;

    public PlayUsecase(BoardRepository boardRepository,
                       UserRepository userRepository,
                       RoomRepository roomRepository,
                       MessageRepository messageRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
        addTask(
                userRepository
                        .getCacheUser()
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith(new DisposableSingleObserver<Optional<User>>() {
                            @Override
                            public void onSuccess(Optional<User> userOptional) {
                                if (userOptional.isPresent()) {
                                    PlayUsecase.this.user = userOptional.get();
                                } else {
                                    PlayUsecase.this.user = new User("fake", "a@gmail.com", "fake.jpg", "Fucking guy", 0);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
        addTask(
                boardRepository
                        .getLocalBoard()
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith(new DisposableSingleObserver<List<Board>>() {
                            @Override
                            public void onSuccess(List<Board> boards) {
                                PlayUsecase.this.boards = boards;
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
    }

    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
        switch (flag) {
            case Event.INIT_GAME:
                playHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        roomId = (String) params[0];
                        if (user != null && boards != null) {
                            if (roomId.equals("AI")) {
                                initOfflineGame((DisposableSingleObserver<Room>) observer, roomId);
                            } else {
                                initOnlineGame((DisposableObserver<Room>) observer, roomId);
                            }
                        } else {
                            playHandler.postDelayed(this, 100);
                        }
                    }
                });
                break;
            case Event.SEND_MOVE:
                if (roomId.equals("AI")) {
                    userOfflineMove((DisposableSingleObserver<Room>) observer, (int) params[0], (int) params[1]);
                } else {
                    userOnlineMove((int) params[0], (int) params[1]);
                }
                break;
            case Event.GET_MOVE:
                opponentMove((DisposableSingleObserver<Room>) observer);
                break;
            case Event.LEAVE_ROOM:
                if (!roomId.equals("AI")) {
                    if (user.getUid().equals(room.getUser_1().getUid())) {
                        room.setAction(Room.DESTROY);
                        room.setOnline_timestamp(System.currentTimeMillis());
                        playHandler.removeCallbacksAndMessages(null);
                        addTask(
                                roomRepository
                                        .updateNetworkRoom(room)
                                        .subscribeOn(getSubscribeScheduler())
                                        .observeOn(getObserveScheduler())
                                        .subscribe()
                        );
                    } else if (user.getUid().equals(room.getUser_2().getUid())) {
                        PlayUsecase.this.room.setAction(Room.LEAVE);
                        room.setOnline_timestamp(System.currentTimeMillis());
                        addTask(
                                roomRepository
                                        .updateNetworkRoom(room)
                                        .subscribeOn(getSubscribeScheduler())
                                        .observeOn(getObserveScheduler())
                                        .subscribe()
                        );
                    }
                    addTask(
                            messageRepository
                                    .setNetworkMessage(room.getRoom_id(), new Message("0", "", "", Tool.getTagName(user.getName()) + " đã rời phòng", System.currentTimeMillis()))
                                    .subscribeOn(getSubscribeScheduler())
                                    .observeOn(getObserveScheduler())
                                    .subscribe()
                    );
                }
                break;
            case Event.LOGIN_INFO:{
                if ((boolean)params[1]) {
                    addTask(userRepository
                            .getNetworkUser((String)params[0])
                            .subscribeOn(getSubscribeScheduler())
                            .observeOn(getObserveScheduler())
                            .subscribeWith((DisposableSingleObserver<Optional<User>>) observer));
                } else {
                    addTask(userRepository
                            .getCacheUser()
                            .subscribeOn(getSubscribeScheduler())
                            .observeOn(getObserveScheduler())
                            .subscribeWith((DisposableSingleObserver<Optional<User>>) observer));
                }
                break;
            }
            case Event.TIME_OUT:{
                if (!roomId.equals("AI")) {
                    if (user.getUid().equals(room.getUser_1().getUid())) {
                        room.setAction(Room.TIMEOUT);
                        room.setOnline_timestamp(System.currentTimeMillis());
                        addTask(roomRepository.updateNetworkRoom(PlayUsecase.this.room).observeOn(getSubscribeScheduler()).subscribeOn(getSubscribeScheduler()).observeOn(getObserveScheduler()).subscribe());
                    }
                }
                break;
            }
            case Event.RE_START:{
                if (!roomId.equals("AI")) {
                    if (user.getUid().equals(room.getUser_1().getUid())) {
                        room.setAction(Room.CREATE);
                        room.setOnline_timestamp(System.currentTimeMillis());
                        addTask(roomRepository.updateNetworkRoom(PlayUsecase.this.room).observeOn(getSubscribeScheduler()).subscribeOn(getSubscribeScheduler()).observeOn(getObserveScheduler()).subscribe());
                    }
                }
                break;
            }
        }
    }

    private void initOfflineGame(DisposableSingleObserver<Room> observer, String roomId) {
        addTask(
                buildAIObservable()
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith(observer)
        );
    }

    private void initOnlineGame(DisposableObserver<Room> observer, String roomId) {
        addTask(
                roomRepository.getRoom(roomId)
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .map(newRoom -> {
                            Room copyRoom = new Room(newRoom.getRoom_id(), newRoom.getAction(), newRoom.getRoom_number(), newRoom.getBoard(), newRoom.getLast_turn(), newRoom.getNext_turn(), newRoom.getUser_1(), newRoom.getUser_2(), newRoom.getIs_private(), newRoom.getOnline_timestamp());
                            PlayUsecase.this.room = copyRoom;
                            switch (newRoom.getAction()) {
                                case Room.CREATE:
                                    if (user.getUid().equals(newRoom.getUser_1().getUid())) {
                                        if (newRoom.getRoom_number() != null) {
                                            if (newRoom.getBoard() == null) {
                                                addTask(
                                                        messageRepository
                                                                .setNetworkMessage(newRoom.getRoom_id(), new Message("0", "", "", Tool.getTagName(user.getName()) + " đã tạo phòng", System.currentTimeMillis()))
                                                                .subscribeOn(getSubscribeScheduler())
                                                                .observeOn(getObserveScheduler())
                                                                .subscribe()
                                                );
                                            }
                                            newRoom.setBoard(PlayUsecase.this.boards.get(new Random().nextInt(PlayUsecase.this.boards.size())));
                                            newRoom.setAction(Room.RANDOM);
                                            newRoom.setOnline_timestamp(System.currentTimeMillis());
                                            addTask(roomRepository.updateNetworkRoom(newRoom).subscribe());
                                        }
                                        playHandler.postDelayed(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        newRoom.setOnline_timestamp(System.currentTimeMillis());
                                                        addTask(roomRepository.updateNetworkRoom(newRoom).subscribe());
                                                        playHandler.postDelayed(this, 15000);
                                                    }
                                                }
                                                , 15000
                                        );
                                    }
                                    if (allowPushSysstemMessageForViewer & !user.getUid().equals(newRoom.getUser_1().getUid()) && !user.getUid().equals(newRoom.getUser_2().getUid())) {
                                        allowPushSysstemMessageForViewer = false;
                                        addTask(
                                                messageRepository
                                                        .setNetworkMessage(newRoom.getRoom_id(), new Message("0", "", "", Tool.getTagName(user.getName()) + " đang theo dõi", System.currentTimeMillis()))
                                                        .subscribeOn(getSubscribeScheduler())
                                                        .observeOn(getObserveScheduler())
                                                        .subscribe()
                                        );
                                    }
                                    break;
                                case Room.RANDOM:
                                    if (!user.getUid().equals(newRoom.getUser_1().getUid())) {
                                        if (newRoom.getUser_2() == null) {
                                            newRoom.setUser_2(user);
                                            addTask(
                                                    messageRepository
                                                            .setNetworkMessage(newRoom.getRoom_id(), new Message("0", "", "", Tool.getTagName(user.getName()) + " đã vào phòng", System.currentTimeMillis()))
                                                            .subscribeOn(getSubscribeScheduler())
                                                            .observeOn(getObserveScheduler())
                                                            .subscribe()
                                            );
                                        }
                                        if (user.getUid().equals(newRoom.getUser_2().getUid())) {
                                            newRoom.setAction(Room.JOIN);
                                            newRoom.setOnline_timestamp(System.currentTimeMillis());
                                            addTask(
                                                    roomRepository
                                                            .updateNetworkRoom(newRoom)
                                                            .subscribeOn(getSubscribeScheduler())
                                                            .observeOn(getObserveScheduler())
                                                            .subscribe()
                                            );
                                        }
                                    }
                                    if (allowPushSysstemMessageForViewer & !user.getUid().equals(newRoom.getUser_1().getUid()) && !user.getUid().equals(newRoom.getUser_2().getUid())) {
                                        allowPushSysstemMessageForViewer = false;
                                        addTask(
                                                messageRepository
                                                        .setNetworkMessage(newRoom.getRoom_id(), new Message("0", "", "", Tool.getTagName(user.getName()) + " đang theo dõi", System.currentTimeMillis()))
                                                        .subscribeOn(getSubscribeScheduler())
                                                        .observeOn(getObserveScheduler())
                                                        .subscribe()
                                        );
                                    }
                                    break;
                                case Room.JOIN:
                                    playHandler.removeCallbacksAndMessages(null);
                                    if (user.getUid().equals(newRoom.getUser_1().getUid())) {
                                        newRoom.setNext_turn(new Random().nextInt(2) == 0 ? newRoom.getUser_1().getUid() : newRoom.getUser_2().getUid());
                                        newRoom.setAction(Room.START);
                                        newRoom.setOnline_timestamp(System.currentTimeMillis());
                                        addTask(roomRepository.updateNetworkRoom(newRoom).subscribe());
                                    }
                                    if (allowPushSysstemMessageForViewer & !user.getUid().equals(newRoom.getUser_1().getUid()) && !user.getUid().equals(newRoom.getUser_2().getUid())) {
                                        allowPushSysstemMessageForViewer = false;
                                        addTask(
                                                messageRepository
                                                        .setNetworkMessage(newRoom.getRoom_id(), new Message("0", "", "", Tool.getTagName(user.getName()) + " đang theo dõi", System.currentTimeMillis()))
                                                        .subscribeOn(getSubscribeScheduler())
                                                        .observeOn(getObserveScheduler())
                                                        .subscribe()
                                        );
                                    }
                                    break;
                                case Room.START:
                                    if (allowPushSysstemMessageForViewer & !user.getUid().equals(newRoom.getUser_1().getUid()) && !user.getUid().equals(newRoom.getUser_2().getUid())) {
                                        allowPushSysstemMessageForViewer = false;
                                        addTask(
                                                messageRepository
                                                        .setNetworkMessage(newRoom.getRoom_id(), new Message("0", "", "", Tool.getTagName(user.getName()) + " đang theo dõi", System.currentTimeMillis()))
                                                        .subscribeOn(getSubscribeScheduler())
                                                        .observeOn(getObserveScheduler())
                                                        .subscribe()
                                        );
                                    }
                                    break;
                                case Room.MOVE:
                                    if (allowPushSysstemMessageForViewer & !user.getUid().equals(newRoom.getUser_1().getUid()) && !user.getUid().equals(newRoom.getUser_2().getUid())) {
                                        allowPushSysstemMessageForViewer = false;
                                        addTask(
                                                messageRepository
                                                        .setNetworkMessage(newRoom.getRoom_id(), new Message("0", "", "", Tool.getTagName(user.getName()) + " đang theo dõi", System.currentTimeMillis()))
                                                        .subscribeOn(getSubscribeScheduler())
                                                        .observeOn(getObserveScheduler())
                                                        .subscribe()
                                        );
                                    }
                                    break;
                                case Room.END:
                                    if (user.getUid().equals(room.getUser_1().getUid())) {
                                        addTask(
                                                userRepository
                                                        .setCacheUser(room.getUser_1())
                                                        .subscribeOn(getSubscribeScheduler())
                                                        .observeOn(getObserveScheduler())
                                                        .subscribe()
                                        );
                                    }
                                    if (user.getUid().equals(room.getUser_2().getUid())) {
                                        addTask(
                                                userRepository
                                                        .setCacheUser(room.getUser_2())
                                                        .subscribeOn(getSubscribeScheduler())
                                                        .observeOn(getObserveScheduler())
                                                        .subscribe()
                                        );
                                    }
                                    if (allowPushSysstemMessageForViewer & !user.getUid().equals(newRoom.getUser_1().getUid()) && !user.getUid().equals(newRoom.getUser_2().getUid())) {
                                        allowPushSysstemMessageForViewer = false;
                                        addTask(
                                                messageRepository
                                                        .setNetworkMessage(newRoom.getRoom_id(), new Message("0", "", "", Tool.getTagName(user.getName()) + " đang theo dõi", System.currentTimeMillis()))
                                                        .subscribeOn(getSubscribeScheduler())
                                                        .observeOn(getObserveScheduler())
                                                        .subscribe()
                                        );
                                    }
                                    break;
                                case Room.LEAVE:
                                    break;
                                case Room.DESTROY:
                                    break;
                            }
                            return copyRoom;
                        })
                        .subscribeWith(observer)
        );
    }

    private Single<Room> buildAIObservable() {
        return Single.create(emitter -> {
            PlayUsecase.this.room = new Room("AI",
                    Room.START,
                    0,
                    PlayUsecase.this.boards.get(new Random().nextInt(PlayUsecase.this.boards.size())),
                    "",
                    new Random().nextInt(2) == 0 ? "AI" : user.getUid(),
                    null,
                    user,
                    false);
            emitter.onSuccess(PlayUsecase.this.room);
        });
    }

    private void userOfflineMove(DisposableSingleObserver<Room> observer, int x, int y) {
        addTask(Single.create((SingleOnSubscribe<Room>) emitter -> {
            String currentMove = PlayUsecase.this.room.getNext_turn();
            int currentPlayer = PlayUsecase.this.room.getNext_turn().equals(PlayUsecase.this.room.getUser_2().getUid()) ? 2 : 1;
            Board state = PlayUsecase.this.room.getBoard();
            LineXOBoard lineXOBoard = new LineXOBoard(state.getPattern(), currentPlayer);
            if (lineXOBoard.getValueAt(x, y) == Board.LINE_NOT_DRAWN) {
                lineXOBoard.mark(new LineXOMove(x, y));
                PlayUsecase.this.room.setBoard(new Board(lineXOBoard.getBoard(), lineXOBoard.getX_cells(), lineXOBoard.getO_cells(), state.getMax_cells()));
                if (lineXOBoard.getPlayerToMove() != currentPlayer) {
                    User user1 = PlayUsecase.this.room.getUser_1();
                    User user2 = PlayUsecase.this.room.getUser_2();
                    if (user1 == null) {
                        currentMove = user2.getUid().equals(currentMove) ? "AI" : user2.getUid();
                    } else {
                        currentMove = user2.getUid().equals(currentMove) ? user1.getUid() : user2.getUid();
                    }
                }
                PlayUsecase.this.room.setNext_turn(currentMove);
                emitter.onSuccess(PlayUsecase.this.room);
            } else {
                emitter.onError(new Throwable());
            }
        }).subscribeOn(getSubscribeScheduler()).observeOn(getObserveScheduler()).subscribeWith(observer));
    }

    private void userOnlineMove(int x, int y) {
        addTask(Single.create((SingleOnSubscribe<Room>) emitter -> {
            String currentMove = PlayUsecase.this.room.getNext_turn();
            int currentPlayer = PlayUsecase.this.room.getNext_turn().equals(PlayUsecase.this.room.getUser_2().getUid()) ? 2 : 1;
            Board state = PlayUsecase.this.room.getBoard();
            LineXOBoard lineXOBoard = new LineXOBoard(state.getPattern(), currentPlayer);
            if (lineXOBoard.getValueAt(x, y) == Board.LINE_NOT_DRAWN) {
                lineXOBoard.mark(new LineXOMove(x, y));
                PlayUsecase.this.room.setBoard(new Board(lineXOBoard.getBoard(), lineXOBoard.getX_cells(), lineXOBoard.getO_cells(), state.getMax_cells()));
                if (lineXOBoard.getPlayerToMove() != currentPlayer) {
                    User user1 = PlayUsecase.this.room.getUser_1();
                    User user2 = PlayUsecase.this.room.getUser_2();
                    currentMove = user2.getUid().equals(currentMove) ? user1.getUid() : user2.getUid();
                }
                PlayUsecase.this.room.setNext_turn(currentMove);
                PlayUsecase.this.room.setOnline_timestamp(System.currentTimeMillis());
                PlayUsecase.this.room.setAction(Room.MOVE);
                addTask(roomRepository.updateNetworkRoom(PlayUsecase.this.room).subscribe());
            }
        }).subscribe());
    }

    private void opponentMove(DisposableSingleObserver<Room> observer) {
        addTask(Single.create((SingleOnSubscribe<Room>) emitter -> {
            String currentMove = PlayUsecase.this.room.getNext_turn();
            int currentPlayer = PlayUsecase.this.room.getNext_turn().equals(PlayUsecase.this.room.getUser_2().getUid()) ? 2 : 1;
            Board state = PlayUsecase.this.room.getBoard();
            LineXOBoard rootState = new LineXOBoard(state.getPattern(), currentPlayer);
            LineXOGame game = new LineXOGame();
            LineXOAlphaBetaSearch search = new LineXOAlphaBetaSearch(game);
            LineXOMove move = search.makeDecision(rootState);
            rootState.mark(move);
            PlayUsecase.this.room.setBoard(new Board(rootState.getBoard(), rootState.getX_cells(), rootState.getO_cells(), state.getMax_cells()));
            if (rootState.getPlayerToMove() != currentPlayer) {
                User user1 = PlayUsecase.this.room.getUser_1();
                User user2 = PlayUsecase.this.room.getUser_2();
                if (user1 == null) {
                    currentMove = user2.getUid().equals(currentMove) ? "AI" : user2.getUid();
                } else {
                    currentMove = user2.getUid().equals(currentMove) ? user1.getUid() : user2.getUid();
                }
            }
            PlayUsecase.this.room.setNext_turn(currentMove);
            long deltaTime = System.currentTimeMillis() - lastTimeGetMove;
            if (deltaTime < MOVE_INTERVAL) {
                SystemClock.sleep(MOVE_INTERVAL - deltaTime);
            }
            lastTimeGetMove = System.currentTimeMillis();
            emitter.onSuccess(PlayUsecase.this.room);
        }).subscribeOn(getSubscribeScheduler()).observeOn(getObserveScheduler()).subscribeWith(observer));
    }
}
