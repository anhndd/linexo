package vn.edu.hcmut.linexo.data.network;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import vn.edu.hcmut.linexo.data.mapper.Mapper;
import vn.edu.hcmut.linexo.data.mapper.RoomDB;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Message;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.FirebaseTree;
import vn.edu.hcmut.linexo.utils.Optional;

public class FirebaseDB implements NetworkSource {

    public FirebaseDB() {

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public Single<List<Board>> getBoard() {
//        return null;
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
                },0,0,100));
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
                        RoomDB post = postSnapshot.getValue(RoomDB.class);
                        if (post.getRoom_number() != null){
                            results.add(Mapper.convertRoomDB2Room(post));
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

    @Override
    public Single<Optional<User>> getUser(String uid) {
        return Single.create(emitter -> {
            DatabaseReference userRef = database.getReference("user").child(uid);
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    emitter.onSuccess(Optional.of(dataSnapshot.getValue(User.class)));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FIREBASE ERROR", databaseError.getMessage());
                    emitter.onSuccess(Optional.empty());
                }
            };
            userRef.addListenerForSingleValueEvent(listener);
            emitter.setCancellable(() -> userRef.removeEventListener(listener));
        });
    }

    @Override
    public Single<Boolean> setUser(User user) {
        return Single.create(emitter -> {
            DatabaseReference userRef = database.getReference("user").child(user.getUid());
            userRef.setValue(user, (databaseError, databaseReference) -> {
                if (databaseError != null){
                    emitter.onSuccess(true);
                }
                else {
                    Log.e("FIREBASE ERROR", databaseError.getMessage());
                    emitter.onSuccess(false);
                }
            });
        });
    }

    @Override
    public Single<List<User>> getScoreTable() {
        return Single.create(emitter -> {
            DatabaseReference userRef = database.getReference("user");
            List<User> resutls = new ArrayList<>();
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot post: dataSnapshot.getChildren()){
                        resutls.add(post.getValue(User.class));
                    }
                    emitter.onSuccess(resutls);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FIREBASE ERROR", databaseError.getMessage());
                }
            };
            userRef.orderByChild("score").addListenerForSingleValueEvent(listener);
            emitter.setCancellable(() -> userRef.removeEventListener(listener));
        });
    }

    @Override
    public Observable<Message> getMessage(int roomNumber) {
        return Observable.create(emitter -> {
            Query messRef = database
                    .getReference("message")
                    .child(String.valueOf(roomNumber))
                    .orderByChild("time");
            ChildEventListener listener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    emitter.onNext(dataSnapshot.getValue(Message.class));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            messRef.addChildEventListener(listener);
            emitter.setCancellable(() -> messRef.removeEventListener(listener));
        });
    }

    @Override
    public Single<Boolean> setMessage(int roomNumber, Message message) {
        return Single.create(emitter -> {
            DatabaseReference messRef = database.getReference("message")
                    .child(String.valueOf(roomNumber))
                    .child(message.getId() + "@" + message.getTime());

            messRef.setValue(message, ((databaseError, databaseReference) -> {
                if (databaseError == null){
                    emitter.onSuccess(true);
                }
                else{
                    Log.e("FIREBASE ERROR", databaseError.getMessage());
                    emitter.onSuccess(false);
                }
            }));
        });
    }
}
