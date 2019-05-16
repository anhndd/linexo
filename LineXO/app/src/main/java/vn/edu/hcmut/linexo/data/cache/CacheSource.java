package vn.edu.hcmut.linexo.data.cache;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Optional;

public interface CacheSource {

    Single<Optional<User>> getUser();
    Single<Boolean> setUser(User user);

}
