package vn.edu.hcmut.linexo.domain.interactor;

import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import vn.edu.hcmut.linexo.data.repository.BoardRepository;
import vn.edu.hcmut.linexo.data.repository.RoomRepository;
import vn.edu.hcmut.linexo.data.repository.UserRepository;
import vn.edu.hcmut.linexo.domain.AI.LineXOAlphaBetaSearch;
import vn.edu.hcmut.linexo.domain.AI.LineXOBoard;
import vn.edu.hcmut.linexo.domain.AI.LineXOGame;
import vn.edu.hcmut.linexo.domain.AI.LineXOMove;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.Session;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.Optional;

public class PlayUsecase extends AbstractUsecase {

    private final long MOVE_INTERVAL = 500;

    private Room room;
    private User user;
    private List<Board> boards;
    private BoardRepository boardRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private Handler playHandler = new Handler();
    private long lastTimeGetMove = 0;

    public PlayUsecase(BoardRepository boardRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.boardRepository    = boardRepository;
        this.userRepository     = userRepository;
        this.roomRepository = roomRepository;
        addTask(
                userRepository
                        .getCacheUser()
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith(new DisposableSingleObserver<Optional<User>>() {
                            @Override
                            public void onSuccess(Optional<User> userOptional) {
                                if(userOptional.isPresent()){
                                    PlayUsecase.this.user = userOptional.get();
                                }
                                else {
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
//        user =
    }

    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
        switch (flag) {
            case Event.INIT_GAME:
                playHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (user != null && boards != null) {
                            initGame((DisposableSingleObserver<Room>) observer, (String) params[0]);
                        } else {
                            playHandler.postDelayed(this, 100);
                        }
                    }
                });
                break;
            case Event.SEND_MOVE:
                userMove((DisposableSingleObserver<Room>) observer, (int) params[0], (int) params[1]);
                break;
            case Event.GET_MOVE:
                opponentMove((DisposableSingleObserver<Room>) observer);
        }
    }

    private void initGame(DisposableSingleObserver<Room> observer, String roomId) {
        if (roomId.equals("AI")) {
            addTask(
                    buildAIObservable()
                            .subscribeOn(getSubscribeScheduler())
                            .observeOn(getObserveScheduler())
                            .subscribeWith(observer)
            );
        }
        else {

        }
    }

    private Single<Room> buildAIObservable() {
        return Single.create(emitter -> {
            PlayUsecase.this.room = new Room("AI",
                    0,
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

    private void userMove(DisposableSingleObserver<Room> observer, int x, int y) {
        Log.e("Test", "userMove");
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
            }
            else {
                emitter.onError(new Throwable());
            }
        }).subscribeOn(getSubscribeScheduler()).observeOn(getObserveScheduler()).subscribeWith(observer));
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
                }
                else {
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
