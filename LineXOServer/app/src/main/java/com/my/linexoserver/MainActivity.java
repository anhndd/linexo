package com.my.linexoserver;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database=  FirebaseDatabase.getInstance();
    TextView txt_log;
    public static final int MAX_ROOM = 1000;
    private boolean[] listRooms = new boolean[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNewRoomNumber();
        checkOnlineSignal();
        addScore();
    }

    public void addScore(){
        DatabaseReference roomRef = database.getReference("room");
        roomRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Room room = dataSnapshot.getValue(Room.class);
                if (room.getRoom_number() != null && room.getBoard() != null){
                    Board board = room.getBoard();
                    User user1 = room.getUser_1();
                    User user2 = room.getUser_2();

                    if (room.getAction()== Room.MOVE && board.getCell_o() + board.getCell_x() == board.getMax_cell()){
                        room.setAction(Room.END);
                        if (board.getCell_x() > board.getCell_o()){
                            user1.setScore(user1.getScore() + 1);
                            user2.setScore(user2.getScore() - 1);
                        }
                        else if (board.getCell_x() < board.getCell_o()){
                            user1.setScore(user1.getScore() - 1);
                            user2.setScore(user2.getScore() + 1);
                        }
                        updateUserAndRoom(room, user1, user2);
                    }
                    else if (room.getAction() == Room.LEAVE){
                        room.setAction(Room.NOT_USED);
                        updateUserAndRoom(room, user1, user2);
                    }
                    else if (room.getAction() == Room.DESTROY){
                        room.setAction(Room.NOT_USED);
                        updateUserAndRoom(room, user1, user2);
                    }
                    else if (room.getAction() == Room.TIMEOUT){
                        room.setAction(Room.END);
                        if (room.getNext_turn().equals(user1.getUid())){
                            user1.setScore(user1.getScore() - 1);
                            user2.setScore(user2.getScore() + 1);
                        }
                        else {
                            user1.setScore(user1.getScore() + 1);
                            user2.setScore(user2.getScore() - 1);
                        }
                        updateUserAndRoom(room, user1, user2);
                    }
                }
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
        });
    }

    public void updateUserAndRoom(Room room, User user1, User user2){
        DatabaseReference ref = database.getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/room/" + room.getRoom_id(), room);
        if (user1 != null && user2 != null) {
            childUpdates.put("/user/" + user1.getUid(), user1);
            childUpdates.put("/user/" + user2.getUid(), user2);
            Toast.makeText(MainActivity.this, "Update score of user1 and user2" + room.getRoom_number(), Toast.LENGTH_LONG).show();
        }
        ref.updateChildren(childUpdates);
    }

    public void deleteRoom(String roomNodeKey, int roomNumber){
        DatabaseReference ref = database.getReference();
        listRooms[roomNumber] = false;
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/room/" + roomNodeKey, null);
        childUpdates.put("/message/" + roomNodeKey, null);
        Toast.makeText(MainActivity.this, "Delete room and message" + roomNumber, Toast.LENGTH_LONG).show();
        ref.updateChildren(childUpdates);
    }

    public void setNewRoomNumber(){
        DatabaseReference roomRef = database.getReference("room");
        roomRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Room room = dataSnapshot.getValue(Room.class);
                if (room.getRoom_number() == null && room.getAction() == Room.CREATE){
                    for (int i = 1; i < MAX_ROOM; i++){
                        if (listRooms[i] == false){
                            room.setRoom_number(i);
                            DatabaseReference roomNumRef = database.getReference("room").child(dataSnapshot.getKey());
                            roomNumRef.setValue(room);
                            listRooms[i] = true;
                            Toast.makeText(MainActivity.this,"Set new room number " + i, Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }
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
        });
    }

    public void checkOnlineSignal(){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable runCheck = new Runnable() {
            DatabaseReference roomRef = database.getReference("room");
            @Override
            public void run() {
                roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot post: dataSnapshot.getChildren()){
                            Room room = post.getValue(Room.class);
                            if (room.getRoom_number() != null){
                                Long newTimestamp = room.getOnline_timestamp();
                                if (System.currentTimeMillis() - newTimestamp >= 60000){
                                    deleteRoom(post.getKey(), room.getRoom_number());
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        };
        service.scheduleAtFixedRate(runCheck, 0, 15, TimeUnit.SECONDS);
    }
}
