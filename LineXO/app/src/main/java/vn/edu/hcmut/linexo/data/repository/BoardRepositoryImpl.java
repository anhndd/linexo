package vn.edu.hcmut.linexo.data.repository;

import java.util.List;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.presentation.model.Board;

public class BoardRepositoryImpl implements BoardRepository {

    private NetworkSource networkSource;

    public BoardRepositoryImpl(NetworkSource networkSource) {
        this.networkSource = networkSource;
    }

    @Override
    public Single<List<Board>> getLocalBoard() {
        return networkSource.getBoard();
    }
}
