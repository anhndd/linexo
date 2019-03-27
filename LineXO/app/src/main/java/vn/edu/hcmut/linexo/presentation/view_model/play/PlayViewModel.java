package vn.edu.hcmut.linexo.presentation.view_model.play;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.BR;
import vn.edu.hcmut.linexo.domain.interactor.Usecase;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Message;
import vn.edu.hcmut.linexo.presentation.view.play.ChatRecyclerViewAdapter;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.utils.Event;

public class PlayViewModel extends BaseObservable implements ViewModel {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private Usecase playUsecase;

    private Board board;

    private ChatRecyclerViewAdapter adapter = new ChatRecyclerViewAdapter(new ArrayList<>());
    private List<Message> messages;

    public PlayViewModel(Usecase playUsecase) {
        this.playUsecase = playUsecase;
        messages = new ArrayList<>();
        messages.add(new Message(1,"1",null,null,"1"));
        messages.add(new Message(2,"2","khuong tu nha","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","hinh nhu la chet roi do hahaha hahaha"));
        messages.add(new Message(2,"5","gii gii gii gii gii gii gii gii gii gii gii gii","https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg","hinh nhu la chet roi do hahaha hahaha hahaha hahaha hahaha hahaha hahaha"));
        messages.add(new Message(1,"3",null,null,"111"));
        messages.add(new Message(1,"4",null,null,"hinh nhu la chet roi do hahaha hahaha hahaha hahaha hahaha hahaha hahaha"));
        adapter = new ChatRecyclerViewAdapter(messages);
        messages = new ArrayList<>(messages);
    }

    @Override
    public void subscribeObserver(Observer<Event> observer) {
        publisher.subscribe(observer);
        if (board == null) {
            loadBoard();
        }
    }

    @Bindable
    public Board getBoard() {
        return board;
    }

    @Bindable
    public int[] getTouch() {
        return null;
    }

    public void setTouch(int[] touch) {
        if (board.getPlayerToMove() == 2) {
            playUsecase.execute(
                    new DisposableSingleObserver<Board>() {
                        @Override
                        public void onSuccess(Board board) {
                            PlayViewModel.this.board = board;
                            notifyPropertyChanged(BR.board);
                            if (board.getPlayerToMove() == 1) {
                                getOpponentMove();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    },
                    Event.SEND_MOVE,
                    touch[0], touch[1]
            );
        }
    }

    private void loadBoard() {
        playUsecase.execute(
                new DisposableSingleObserver<Board>() {
                    @Override
                    public void onSuccess(Board board) {
                        PlayViewModel.this.board = board;
                        notifyPropertyChanged(BR.board);
                        //TODO: count down time
                        if (board.getPlayerToMove() == 1) {
                            getOpponentMove();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                },
                Event.INIT_GAME,
                null
        );
    }

    private void getOpponentMove() {
        playUsecase.execute(
                new DisposableSingleObserver<Board>() {
                    @Override
                    public void onSuccess(Board board) {
                        PlayViewModel.this.board = board;
                        notifyPropertyChanged(BR.board);
                        if (board.getPlayerToMove() == 1) {
                            getOpponentMove();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                },
                Event.GET_MOVE,
                null
        );
    }

    @Bindable
    public ChatRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ChatRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void endTask() {
        playUsecase.endTask();
    }
}
