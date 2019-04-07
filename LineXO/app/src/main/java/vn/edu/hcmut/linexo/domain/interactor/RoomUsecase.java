package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;

import vn.edu.hcmut.linexo.utils.Event;

public class RoomUsecase extends AbstractUsecase {
    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
        switch (flag) {
            case Event.LOGIN_INFO:
                // TODO
                break;
        }
    }
}
