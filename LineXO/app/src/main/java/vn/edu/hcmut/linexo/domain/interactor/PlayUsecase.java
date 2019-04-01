package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;
import java.util.List;
import java.util.Random;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.observers.DisposableSingleObserver;
import vn.edu.hcmut.linexo.data.repository.BoardRepository;
import vn.edu.hcmut.linexo.data.repository.SessionRepository;
import vn.edu.hcmut.linexo.data.repository.UserRepository;
import vn.edu.hcmut.linexo.domain.AI.LineXOAlphaBetaSearch;
import vn.edu.hcmut.linexo.domain.AI.LineXOGame;
import vn.edu.hcmut.linexo.domain.AI.LineXOMove;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Session;
import vn.edu.hcmut.linexo.utils.Event;

public class PlayUsecase extends AbstractUsecase {

    private Board board;
    private List<Board> boards;
    private SessionRepository sessionRepository;
    private BoardRepository boardRepository;
    private UserRepository userRepository;
    private Session session;

    public PlayUsecase(SessionRepository sessionRepository, BoardRepository boardRepository, UserRepository userRepository) {
        this.sessionRepository  = sessionRepository;
        this.boardRepository    = boardRepository;
        this.userRepository     = userRepository;
    }

    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
        switch (flag) {
            case Event.INIT_GAME:
                initGame((DisposableSingleObserver<Board>) observer);
                break;
            case Event.SEND_MOVE:
                userMove((DisposableSingleObserver<Board>) observer, (int) params[0], (int) params[1]);
                break;
            case Event.GET_MOVE:
                opponentMove((DisposableSingleObserver<Board>) observer);
        }
    }

    private void initGame(DisposableSingleObserver<Board> observer) {
        addTask(
                sessionRepository
                        .getCacheSession()
                        .subscribeOn(this.getSubscribeScheduler())
                        .observeOn(this.getObserveScheduler())
                        .subscribeWith(new DisposableSingleObserver<Session>() {
                            @Override
                            public void onSuccess(Session session) {
                                PlayUsecase.this.session = session;
                                if (session.getRoomNumber() == 0) {
                                    addTask(
                                            boardRepository
                                                    .getLocalBoard()
                                                    .subscribeOn(getSubscribeScheduler())
                                                    .observeOn(getObserveScheduler())
                                                    .map(boards -> {
                                                        PlayUsecase.this.boards = boards;
                                                        PlayUsecase.this.board = boards.get(new Random().nextInt(boards.size()))
                                                                .updatePlayerToMove(2);
                                                        return PlayUsecase.this.board;
                                                    })
                                                    .subscribeWith(observer)
                                    );
                                } else {
                                    //TODO
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
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
            LineXOGame game = new LineXOGame();
            LineXOAlphaBetaSearch search = new LineXOAlphaBetaSearch(game);
            LineXOMove move = search.makeDecision(state);
            state.mark(move);
            board = state.duplicate();
            emitter.onSuccess(board);
        }).subscribeOn(getSubscribeScheduler()).observeOn(getObserveScheduler()).subscribeWith(observer));
    }
}
