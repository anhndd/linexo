package com.my.linexoserver;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database=  FirebaseDatabase.getInstance();
    TextView txt_log;
    public static final int MAX_ROOM = 1000;
    private long[] roomTimestamp = new long[1000];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNewRoomNumber();
        checkOnlineSignal();
    }

    public void deleteRoom(String roomNodeKey, int roomNumber){
        DatabaseReference roomRef = database.getReference("room");
        roomRef.child(roomNodeKey).removeValue((databaseError, databaseReference) -> {
            if (databaseError == null){
                Toast.makeText(MainActivity.this,"Delete room number " + roomNumber, Toast.LENGTH_LONG).show();
                DatabaseReference messRef = database.getReference("message");
                messRef.child(String.valueOf(roomNumber)).removeValue((databaseError1, databaseReference1) -> {
                    if (databaseError1 == null){
                        Toast.makeText(MainActivity.this,"Delete message with room number " + roomNumber, Toast.LENGTH_LONG).show();
                        Log.e("FIREBASE SUCCESS","Deleted message with room");
                    }
                    else{
                        Log.e("FIREBASE ERROR","Deleted message with room");
                    }
                });

                roomTimestamp[roomNumber] = 0;
            }
            else {
                Log.e("LineXO", databaseError.getMessage());

            }
        });
    }

    public void setNewRoomNumber(){
        DatabaseReference roomRef = database.getReference("room");
        roomRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Room room = dataSnapshot.getValue(Room.class);
                if (room.getRoom_number() == null && room.getAction() == Room.CREATE){
                    for (int i = 1; i < MAX_ROOM; i++){
                        if (roomTimestamp[i] == 0){
                            room.setRoom_number(i);
                            DatabaseReference roomNumRef = database.getReference("room").child(dataSnapshot.getKey());
                            roomNumRef.setValue(room);
                            roomTimestamp[i] = System.currentTimeMillis();
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
                                if (newTimestamp > roomTimestamp[room.getRoom_number()]){
                                    roomTimestamp[room.getRoom_number()] = newTimestamp;
                                }
                                else {
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
        service.scheduleAtFixedRate(runCheck, 0, 10, TimeUnit.SECONDS);
    }
}
