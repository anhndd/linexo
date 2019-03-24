package vn.edu.hcmut.linexo.presentation.view_model.play;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.domain.interactor.Usecase;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.utils.Event;

public class PlayViewModel extends BaseObservable implements ViewModel {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private Usecase playUsecase;

    private Board board;

    public PlayViewModel(Usecase playUsecase) {
        this.playUsecase = playUsecase;
    }

    @Override
    public void subscribeObserver(Observer<Event> observer) {
        publisher.subscribe(observer);
        if (board == null) {
            playUsecase.execute(
                    new DisposableSingleObserver<Board>() {
                        @Override
                        public void onSuccess(Board board) {
                            PlayViewModel.this.board = board;
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    },
                    0,
                    null
            );
        }
    }

    @Bindable
    public Board getBoard() {
        return board;
    }

    @Override
    public void endTask() {
        playUsecase.endTask();
    }
}
