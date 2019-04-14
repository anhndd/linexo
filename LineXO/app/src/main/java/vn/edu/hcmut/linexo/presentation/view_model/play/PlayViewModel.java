package vn.edu.hcmut.linexo.presentation.view_model.play;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.NetworkChangeReceiver;

public class PlayViewModel extends BaseObservable implements ViewModel, ViewModelCallback {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private Usecase playUsecase;

    private Board board;

    private ChatRecyclerViewAdapter adapter = new ChatRecyclerViewAdapter(new ArrayList<>());
    private List<Message> messages;
    private boolean isConnected;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    int j = 0;

    public PlayViewModel(Context context, Usecase playUsecase) {
        this.playUsecase = playUsecase;

        networkChangeReceiver.initReceiver(context, new NetworkChangeReceiver.NetworkChangeListener() {
            @Override
            public void onNetworkChange(boolean networkState) {
                isConnected = networkState;
                notifyPropertyChanged(BR.networkVisibility);
            }
        });

        messages = new ArrayList<>();
        messages.add(new Message(1, j+++"", null, null, "alo"));
        messages.add(new Message(2, j+++"", "khuong tu nha", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "đi đường kia kìa :)"));
        messages.add(new Message(2, j+++"", "khuong tu nha", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "đi đường kia kìa :)"));
        messages.add(new Message(3, j+++"", null, null, "Lâm Nguyễn đang theo dõi"));
        messages.add(new Message(1, j+++"", null, null, "111"));
        messages.add(new Message(1, j+++"", null, null, "đường nào mày...... ha ha ha chết chưa m hả bưởi."));
        messages.add(new Message(3, j+++"", null, null, "Lâm Nguyễn đã thoát"));
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

    @Override
    public void onHelp(Event e) {
        switch (e.getType()) {
            case 1: {
                messages = new ArrayList<>((List<Message>) e.getData()[0]);
                adapter.updateMessageListItems(messages);
                break;
            }
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
        if (board.getValueAt(touch[0], touch[1]) == Board.LINE_NOT_DRAWN && board.getPlayerToMove() == 2) {
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

    public void onClickSend(View view) {
        messages = new ArrayList<>(messages);

        messages.add(new Message(2, j+++"", "khuong tu nha", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "đi đường kia kìa :)"));
        messages.add(new Message(3, j+++"", null, null, "Lâm Nguyễn đang theo dõi"));
        messages.add(new Message(1, j+++"", null, null, "đường nào mày...... ha ha ha chết chưa m hả bưởi."));

        onHelp(Event.create(1, messages));
    }

    @Bindable
    public ChatRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ChatRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Bindable
    public int getNetworkVisibility() {
        if (isConnected) {
            return View.GONE;
        }
        return View.VISIBLE;
    }

    @Override
    public void endTask() {
        playUsecase.endTask();
    }
}
