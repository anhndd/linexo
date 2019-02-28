package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;
import vn.edu.hcmut.linexo.data.repository.UserRepository;

public class LoginUsecase extends AbstractUsecase {

    private UserRepository userRepository;

    public LoginUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Object observer, @Nullable int flag, @Nullable Object... params) {
        //TODO
    }
}
