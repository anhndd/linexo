package vn.edu.hcmut.linexo.data.network;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Board;

public class FirebaseDB implements NetworkSource {

    public FirebaseDB() {

    }

    @Override
    public Single<List<Board>> getBoard() {
        return Single.create(emitter -> {
            List<Board> boards = new ArrayList<>();
            boards.add(new Board(new byte[][]{
                        {0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0}, // 1
                        {0,0,0,0,0,0,0,0,3,4,3,0,0,0,0,0,0,0,0}, // 2
                        {0,0,0,0,0,0,0,3,0,1,0,3,0,0,0,0,0,0,0}, // 3
                        {0,0,0,0,0,0,3,4,1,4,1,4,3,0,0,0,0,0,0}, // 4
                        {0,0,0,0,0,3,0,1,0,1,0,1,0,3,0,0,0,0,0}, // 5
                        {0,0,0,0,3,4,1,4,1,4,1,4,1,4,3,0,0,0,0}, // 6
                        {0,0,0,3,0,1,0,1,0,1,0,1,0,1,0,3,0,0,0}, // 7
                        {0,0,3,4,1,4,1,4,1,4,1,4,1,4,1,4,3,0,0}, // 8
                        {0,3,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,3,0}, // 9
                        {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 10
                        {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}, // 11
                }));
            emitter.onSuccess(boards);
        });
    }
}
