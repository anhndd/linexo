package vn.edu.hcmut.linexo.data.network;

import java.util.List;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Board;

public class FirebaseDB implements NetworkSource {

    public FirebaseDB() {

    }

    @Override
    public Single<List<Board>> getBoard() {
        return Single.create(emitter -> {
            emitter.onSuccess(null);
        });
    }
}
