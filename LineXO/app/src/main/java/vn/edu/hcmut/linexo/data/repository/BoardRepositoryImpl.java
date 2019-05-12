package vn.edu.hcmut.linexo.data.repository;

import java.util.List;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.data.local.LocalSource;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.presentation.model.Board;

public class BoardRepositoryImpl implements BoardRepository {

    private NetworkSource networkSource;
    private LocalSource localSource;

    public BoardRepositoryImpl(NetworkSource networkSource, LocalSource localSource) {
        this.networkSource = networkSource;
        this.localSource = localSource;
    }

    public Single<List<Board>> getLocalBoard(){
        return localSource.loadBoard();
    }
}
