package vn.edu.hcmut.linexo.data.network;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import io.reactivex.Single;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Room;

public class FirebaseDB implements NetworkSource {

    public FirebaseDB() {

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();

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

    @Override
    public Single<Optional<List<Room>>> getRoom() {
        return null;

    }

    @Override
    public void setRoom(Room room) {

    }
}
