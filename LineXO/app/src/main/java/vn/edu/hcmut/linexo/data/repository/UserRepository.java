package vn.edu.hcmut.linexo.data.repository;


import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Optional;

public interface UserRepository {
    Single<Optional<User>> getNetworkUser(String username);

    Single<User> getCacheUser();

    void setCacheUser(User user);


}
