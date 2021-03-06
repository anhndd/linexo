package vn.edu.hcmut.linexo.data.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;
import vn.edu.hcmut.linexo.data.mapper.Mapper;
import vn.edu.hcmut.linexo.data.mapper.RoomDB;
import vn.edu.hcmut.linexo.presentation.model.Message;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.utils.Optional;

public class FirebaseDB implements NetworkSource {

    public FirebaseDB() {

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public Observable<List<Room>> getListRoom() {
        return Observable.create(emitter -> {
            DatabaseReference myRef = database.getReference("room");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Room> results = new ArrayList<>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                        RoomDB post = postSnapshot.getValue(RoomDB.class);
                        if (post.getRoom_number() != null  && post.getAction() != Room.NOT_USE){
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
    public Single<String> setRoom(Room room) {
        return Single.create(emitter -> {
            DatabaseReference roomRef = database.getReference("room").push();
            room.setRoom_id(roomRef.getKey());
            roomRef.setValue(room, (databaseError, databaseReference) -> {
                if (databaseError == null){
                    emitter.onSuccess(roomRef.getKey());
                }
                else {
                    Log.e("FIREBASE ERROR", databaseError.getMessage());
                    emitter.onError(new Throwable());
                }
            });
        });
    }

    @Override
    public Single<Boolean> updateRoom(Room room) {
        return Single.create(emitter -> {

            DatabaseReference roomRef = database.getReference("room").child(room.getRoom_id());
            roomRef.setValue(Mapper.convertRoom2RoomDB(room), (databaseError, databaseReference) -> {
                if (databaseError == null){
                    emitter.onSuccess(true);
                }
                else{
                    Log.e("FIREBASE ERROR", databaseError.getMessage());
                    emitter.onSuccess(false);
                }
            });
        });
    }

    @Override
    public Observable<Room> getRoom(String roomId) {
        return Observable.create(emitter -> {
            DatabaseReference roomRef = database.getReference("room").child(roomId);
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    RoomDB room = dataSnapshot.getValue(RoomDB.class);
                    emitter.onNext(Mapper.convertRoomDB2Room(room));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FIREBASE ERROR", databaseError.getMessage());
                }
            };
            roomRef.addValueEventListener(listener);
            emitter.setCancellable(() -> roomRef.removeEventListener(listener));
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
    public Observable<Message> getMessage(String roomId) {
        return Observable.create(emitter -> {
            Query messRef = database
                    .getReference("message")
                    .child(roomId)
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
    public Single<Boolean> setMessage(String roomId, Message message) {
        return Single.create(emitter -> {
            DatabaseReference messRef = database.getReference("message")
                    .child(roomId)
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
