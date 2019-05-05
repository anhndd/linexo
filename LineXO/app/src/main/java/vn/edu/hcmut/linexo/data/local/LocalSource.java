package vn.edu.hcmut.linexo.data.local;

import java.util.List;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Board;

public interface LocalSource {
    Single<List<Board>> loadBoard();
//    Single<Boolean> saveBoard(List<Board> boards);
}
