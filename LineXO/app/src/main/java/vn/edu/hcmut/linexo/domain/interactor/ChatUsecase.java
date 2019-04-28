package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;

import io.reactivex.observers.DisposableObserver;
import vn.edu.hcmut.linexo.data.repository.MessageRepository;
import vn.edu.hcmut.linexo.presentation.model.Message;
import vn.edu.hcmut.linexo.utils.Event;

public class ChatUsecase extends AbstractUsecase {

    private MessageRepository messageRepository;

    public ChatUsecase(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
        switch (flag) {
            case Event.LOAD_MESSAGE:
                addTask(messageRepository.getNetworkMessage((int) params[0])
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribeWith((DisposableObserver) observer));
                break;

            case Event.PUSH_MESSAGE:
                addTask(messageRepository.setNetworkMessage((int) params[0], (Message) params[1])
                        .subscribeOn(getSubscribeScheduler())
                        .observeOn(getObserveScheduler())
                        .subscribe());
                break;
        }
    }
}
