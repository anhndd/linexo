package vn.edu.hcmut.linexo.data.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.utils.Optional;

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
    public Observable<List<Room>> getRoom() {
        return Observable.create(emitter -> {
            DatabaseReference myRef = database.getReference("room");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Room> results = new ArrayList<>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                        Room post = postSnapshot.getValue(Room.class);
                        if (post.getBoard_number() != null){
                            results.add(post);
                        }
                    }
                    emitter.onNext(results);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FIREBASE ERROR", databaseError.getMessage());
                }
            };
            myRef.addValueEventListener(listener);
            emitter.setCancellable(() -> myRef.removeEventListener(listener));
        });
    }

    @Override
    public Single<Boolean> setRoom(Room room) {
        return Single.create(emitter -> {
            DatabaseReference roomRef = database.getReference("room").push();
            roomRef.setValue(room, (databaseError, databaseReference) -> {
                if (databaseError == null){
                    emitter.onSuccess(true);
                }
                else {
                    Log.e("FIREBASE ERROR", databaseError.getMessage());
                    emitter.onSuccess(false);
                }
            });
        });
    }
}
