package vn.edu.hcmut.linexo.data.network;

import java.util.List;
import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Board;

public interface NetworkSource {

    Single<List<Board>> getBoard();
}
