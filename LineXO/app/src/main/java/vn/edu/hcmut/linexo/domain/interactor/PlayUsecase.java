package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import vn.edu.hcmut.linexo.data.repository.BoardRepository;
import vn.edu.hcmut.linexo.data.repository.SessionRepository;
import vn.edu.hcmut.linexo.data.repository.UserRepository;
import vn.edu.hcmut.linexo.domain.AI.LineXOAlphaBetaSearch;
import vn.edu.hcmut.linexo.domain.AI.LineXOGame;
import vn.edu.hcmut.linexo.domain.AI.LineXOMove;
import vn.edu.hcmut.linexo.domain.AI.LineXOBoard;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Session;

public class PlayUsecase extends AbstractUsecase {

    private Board board;
    private List<Board> boards;
    private LineXOBoard state;
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
            case 0:
                initGame((DisposableSingleObserver<Board>) observer);
                break;
            case 1:
                
                break;

        }
////        if (flag == 0) {
//            //load session: lay so phong, kiem tra choi voi nguoi hay choi voi may
//            //load tat ca ban co
//            //random chon ban co ngau nhien
//            //truyen ban co len view
////        }
////        LineXOMove move = new LineXOMove((int) params[0], (int) params[1]);
//
////        if (!board.isValid(move))
////        byte[][] board = (byte[][]) params[0];
////        String playerToMove = (String) params[1];
////        Single<LineXOMove> observable = Single.create(emitter -> {
////            LineXOBoard rootState = new LineXOBoard(board, playerToMove);
////            LineXOGame game = new LineXOGame();
////            LineXOAlphaBetaSearch search = new LineXOAlphaBetaSearch(game);
////            LineXOMove move = search.makeDecision(rootState);
////            if (!emitter.isDisposed()) {
////                emitter.onSuccess(move);
////            }
////        });
////        observable.subscribeOn(this.getSubscribeScheduler()).observeOn(this.getObserveScheduler()).subscribe((SingleObserver<? super LineXOMove>) observer);
//    }
//    private void userMove(Object observer, @Nullable int flag, @Nullable Object... params) {
//
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
                                                                .updateLastPlayer(new Random().nextInt(2) + 1);
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

}
