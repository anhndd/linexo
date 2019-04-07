package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.observers.DisposableSingleObserver;
import vn.edu.hcmut.linexo.data.repository.UserRepository;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.Optional;

public class RoomUsecase extends AbstractUsecase {
    private UserRepository userRepository;

    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
        switch (flag) {
            case Event.LOGIN_INFO:
                addTask(userRepository
                        .getCacheUser()
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith((DisposableSingleObserver<Optional<User>>) observer));
                break;
        }
    }
}
