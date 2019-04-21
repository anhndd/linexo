package vn.edu.hcmut.linexo.domain.interactor;

import android.os.Handler;
import android.support.annotation.Nullable;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
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

    private Board board;
    private Room room;
    private User user;
    private List<Board> boards;
    private BoardRepository boardRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private Handler playHandler = new Handler();

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
                                PlayUsecase.this.user = userOptional.get();
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
                        if (user != null && boards != null) {
                            initGame((DisposableObserver<Room>) observer, (int) params[0]);
                        } else {
                            playHandler.postDelayed(this, 100);
                        }
                    }
                });
                break;
            case Event.SEND_MOVE:
                userMove((DisposableSingleObserver<Board>) observer, (int) params[0], (int) params[1]);
                break;
            case Event.GET_MOVE:
                opponentMove((DisposableSingleObserver<Board>) observer);
        }
    }

    private void initGame(DisposableObserver<Room> observer, int roomNumber) {
        if (roomNumber == 0) {
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

    private Observable<Room> buildAIObservable() {
        return Observable.create(emitter -> {
            PlayUsecase.this.room = new Room(
                    0,
                    0,
                    PlayUsecase.this.boards.get(new Random().nextInt(PlayUsecase.this.boards.size())),
                    "",
                    String next_turn, User user_1, User user_2, Boolean is_private)
        });
    }

    private void userMove(DisposableSingleObserver<Board> observer, int x, int y) {
        addTask(Single.create(new SingleOnSubscribe<Board>() {
            @Override
            public void subscribe(SingleEmitter<Board> emitter) throws Exception {
                Board state = board.duplicate();
                state.mark(new LineXOMove(x, y));
                board = state.duplicate();
                emitter.onSuccess(board);
            }
        }).subscribeOn(getSubscribeScheduler()).observeOn(getObserveScheduler()).subscribeWith(observer));
    }

    private void opponentMove(DisposableSingleObserver<Board> observer) {
        addTask(Single.create((SingleOnSubscribe<Board>) emitter -> {
            Board state = board.duplicate();
            LineXOBoard state = new LineXOBoard(board.getPattern(), )
            LineXOGame game = new LineXOGame();
            LineXOAlphaBetaSearch search = new LineXOAlphaBetaSearch(game);
            LineXOMove move = search.makeDecision(state);
            state.mark(move);
            board = state.duplicate();
            emitter.onSuccess(board);
        }).subscribeOn(getSubscribeScheduler()).observeOn(getObserveScheduler()).subscribeWith(observer));
    }
}
