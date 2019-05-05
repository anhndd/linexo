package vn.edu.hcmut.linexo.data.repository;

import java.util.List;
import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Board;

public interface BoardRepository {

    Single<List<Board>> getLocalBoard();
    Single<List<Board>> getNetworkBoard();
}
