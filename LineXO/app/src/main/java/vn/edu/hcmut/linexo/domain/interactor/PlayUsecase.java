package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import vn.edu.hcmut.linexo.data.repository.BoardRepository;
import vn.edu.hcmut.linexo.data.repository.SessionRepository;
import vn.edu.hcmut.linexo.data.repository.UserRepository;
import vn.edu.hcmut.linexo.domain.AI.LineXOAlphaBetaSearch;
import vn.edu.hcmut.linexo.domain.AI.LineXOGame;
import vn.edu.hcmut.linexo.domain.AI.LineXOMove;
import vn.edu.hcmut.linexo.domain.AI.LineXOBoard;
import vn.edu.hcmut.linexo.presentation.model.Board;

public class PlayUsecase extends AbstractUsecase {

    private Board board;
    private LineXOBoard state;
    private SessionRepository sessionRepository;
    private BoardRepository boardRepository;
    private UserRepository userRepository;

    public PlayUsecase(SessionRepository sessionRepository, BoardRepository boardRepository, UserRepository userRepository) {
        this.sessionRepository  = sessionRepository;
        this.boardRepository    = boardRepository;
        this.userRepository     = userRepository;
    }

    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
//        switch (flag) {
//            case 0:
//                byte[][] myArray = new byte[][]{
//                        {0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0}, // 1
//                        {0,0,0,0,0,0,0,0,3,4,3,0,0,0,0,0,0,0,0}, // 2
//                        {0,0,0,0,0,0,0,3,0,1,0,3,0,0,0,0,0,0,0}, // 3
//                        {0,0,0,0,0,0,3,4,1,4,1,4,3,0,0,0,0,0,0}, // 4
//                        {0,0,0,0,0,3,0,1,0,1,0,1,0,3,0,0,0,0,0}, // 5
//                        {0,0,0,0,3,4,1,4,1,4,1,4,1,4,3,0,0,0,0}, // 6
//                        {0,0,0,3,0,1,0,1,0,1,0,1,0,1,0,3,0,0,0}, // 7
//                        {0,0,3,4,1,4,1,4,1,4,1,4,1,4,1,4,3,0,0}, // 8
//                        {0,3,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,3,0}, // 9
//                        {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 10
//                        {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}, // 11
//                };
//                board = new Board(myArray);
//                int lastPlayer = 1;
//                roomNumber = 0;
//                break;
//            case 1:
//                if (roomNumber == 0) {
//                    Single.create()
//                    LineXOBoard rootState = new LineXOBoard(board.gerPattern(), board.getPlayerToMove() == 1 ? "X" : "O");
//                    rootState.mark(new LineXOMove((int) params[0], (int) params[1]));
//                }
//                else {
//                    //choi voi nguoi
//                }
//                break;
//
//        }
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

}
