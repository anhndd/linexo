package vn.edu.hcmut.linexo.presentation.view_model.play;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.BR;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.domain.interactor.Usecase;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Message;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.presentation.view.play.ChatRecyclerViewAdapter;
import vn.edu.hcmut.linexo.presentation.view.play.PlayActivity;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.NetworkChangeReceiver;
import vn.edu.hcmut.linexo.utils.Optional;

public class PlayViewModel extends BaseObservable implements ViewModel, ViewModelCallback {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private Usecase playUsecase;
    private Usecase chatUsecase;

    private Room room;
    private String roomId;
    private ChatRecyclerViewAdapter adapter;
    private List<Message> messages;
    private User user;
    private boolean isConnected;
    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    private int[] arrayKeyboardChanged = {0, 0};
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String contentMessage = "";
    private long countTimeHost = 0;
    private long countTimeGuest = 0;
    private boolean isEnableHost = false;
    private boolean isEnableGuest = false;
    private int gameState = Event.PLAYING;
    private int timePlaying = 15;

    private Handler countDownHandler = new Handler();

    public PlayViewModel(Context context, Usecase playUsecase, Usecase chatUsecase) {
        this.playUsecase = playUsecase;
        this.chatUsecase = chatUsecase;

        networkChangeReceiver.initReceiver(context, new NetworkChangeReceiver.NetworkChangeListener() {
            @Override
            public void onNetworkChange(boolean networkState) {
                isConnected = networkState;
                notifyPropertyChanged(BR.networkVisibility);
            }
        });

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            user = new User(firebaseUser.getUid(), firebaseUser.getEmail(),
                    firebaseUser.getPhotoUrl().toString(), firebaseUser.getDisplayName(), 0, System.currentTimeMillis());
            onHelp(Event.create(Event.LOGIN_INFO, user));
        }
        if (user != null) {
            adapter = new ChatRecyclerViewAdapter(new ArrayList<>(), user.getUid());
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    publisher.onNext(Event.create(Event.SMOOTH_MESSAGE_LIST, adapter.getItemCount()));
                }
            });
        }
    }

    @Override
    public void subscribeObserver(Observer<Event> observer) {
        publisher.subscribe(observer);
        if (room == null) {
            loadRoom();
        }
        if (!roomId.equals("AI") && messages == null) {
            messages = new ArrayList<>();
            chatUsecase.execute(new ChatReceiverObserver(), Event.LOAD_MESSAGE, roomId);
        }
    }

    @Override
    public void onHelp(Event e) {
        switch (e.getType()) {
            case Event.LOAD_MESSAGE: {
                messages = new ArrayList<>((List<Message>) e.getData()[0]);
                adapter.updateMessageListItems(messages);
                break;
            }
            case Event.LOAD_PLAY_INFO:
                // load avatar url host and opponent, score by room ID
                // check url null
                roomId = (String) e.getData()[0];

//                notifyPropertyChanged(BR.roomId);
                break;
            case Event.KEYBOARD_CHANGED: {
                arrayKeyboardChanged = (int[]) e.getData()[0];
                notifyPropertyChanged(BR.arrayKeyboardChanged);
                break;
            }
            case Event.LEAVE_ROOM:{
                playUsecase.execute(null,Event.LEAVE_ROOM);
                break;
            }
            case Event.LOGIN_INFO: {
                playUsecase.execute(new LoginInfoObserver(), Event.LOGIN_INFO, user.getUid(), isConnected);
                break;
            }
        }
    }

    @Bindable
    public Board getBoard() {
        if (room == null)
            return null;
        return room.getBoard();
    }

    @Bindable
    public int[] getTouch() {
        return null;
    }

    public void setTouch(int[] touch) {
        if (PlayViewModel.this.room.getRoom_number() == 0) {
            if (!PlayViewModel.this.room.getNext_turn().equals("AI"))
                playUsecase.execute(new RoomReceiverOfflineObserver(),
                        Event.SEND_MOVE,
                        touch[0], touch[1]
                );
        } else if (PlayViewModel.this.room.getNext_turn().equals(user.getUid())) {
            playUsecase.execute(null,
                    Event.SEND_MOVE,
                    touch[0], touch[1]
            );
        }
    }

    private void loadRoom() {
        if (roomId.equals("AI")) {
            playUsecase.execute(
                    new RoomReceiverOfflineObserver(),
                    Event.INIT_GAME,
                    roomId
            );
        } else {
            playUsecase.execute(
                    new RoomReceiverOnlineObserver(),
                    Event.INIT_GAME,
                    roomId
            );
        }
    }

    private void getOpponentMove() {
        playUsecase.execute(
                new RoomReceiverOfflineObserver(),
                Event.GET_MOVE,
                null
        );
    }

    public void onClickSend(View view) {
        if (!contentMessage.equals("")) {
            messages = new ArrayList<>(messages);
            chatUsecase.execute(null, Event.PUSH_MESSAGE, room.getRoom_id(), new Message(user.getUid(), user.getName(), user.getAvatar(), contentMessage, System.currentTimeMillis()));
            contentMessage = "";
            notifyPropertyChanged(BR.contentMessage);

            onHelp(Event.create(Event.LOAD_MESSAGE, messages));
        }
    }


    public void onClickBtnMessage(View view) {
        if (room != null) {
            publisher.onNext(Event.create(Event.SHOW_KEYBOARD));
        }
    }

    @Bindable
    public ChatRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ChatRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Bindable
    public int getNetworkVisibility() {
        if (isConnected) {
            return View.GONE;
        }
        return View.VISIBLE;
    }

    @Bindable
    public int getRoomNumber() {
        if (room == null || room.getRoom_number() == 0)
            return -1;
        return room.getRoom_number();
    }

    @Bindable
    public Object getAvatar1() {
        if (room == null)
            return R.drawable.img_avatar_holder;
        if (roomId.equals("AI"))
            return R.drawable.ic_logo_round;
        return room.getUser_1().getAvatar();
    }

    @Bindable
    public Object getAvatar2() {
        if (room == null)
            return R.drawable.img_avatar_holder;
        if (roomId.equals("AI"))
            return user==null?R.drawable.img_avatar_holder:user.getAvatar();
        if (room.getUser_2() == null) {
            return R.drawable.img_avatar_holder;
        }
        return room.getUser_2().getAvatar();
    }

    @Bindable
    public Object getScore1() {
        if (room == null)
            return "";
        if (roomId.equals("AI"))
            return "9999";
        return room.getUser_1().getScore() + "";
    }

    @Bindable
    public Object getScore2() {
        if (room == null)
            return "";
        if (roomId.equals("AI"))
            return user==null?"":user.getScore()+"";
        if(room.getUser_2()==null){
            return "";
        }
        return room.getUser_2().getScore() + "";
    }

    @Bindable
    public boolean getPlayType() {
        return roomId.equals("AI");
    }

    @Bindable
    public int[] getArrayKeyboardChanged() {
        return arrayKeyboardChanged;
    }

    @Bindable
    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
        notifyPropertyChanged(BR.contentMessage);
    }

    @Bindable
    public long getCountTimeHost() {
        return countTimeHost;
    }

    @Bindable
    public boolean getEnableHost() {
        return isEnableHost;
    }

    @Bindable
    public long getCountTimeGuest() {
        return countTimeGuest;
    }

    @Bindable
    public boolean getEnableGuest() {
        return isEnableGuest;
    }

    @Override
    public void endTask() {
        playUsecase.endTask();
    }

    class RoomReceiverOfflineObserver extends DisposableSingleObserver<Room> {

        @Override
        public void onSuccess(Room room) {
            if (room.getAction() >= Room.START) {
                if (gameState == Event.PLAYING) {
                    countDownHandler.removeCallbacksAndMessages(null);
                    if ((room.getBoard().getO_cells() + room.getBoard().getX_cells()) == room.getBoard().getMax_cells()) {
                        if (room.getRoom_number() == 0) {
                            if (room.getBoard().getO_cells() > room.getBoard().getX_cells()) {
                                gameState = Event.WIN;
                            } else if (room.getBoard().getO_cells() < room.getBoard().getX_cells()) {
                                gameState = Event.LOSE;
                            } else {
                                gameState = Event.DRAW;
                            }
                            publisher.onNext(Event.create(Event.RESULT, gameState));
                        } else {
                            //TODO:
                        }
                        PlayViewModel.this.room = room;
                        notifyPropertyChanged(BR.board);
                        return;
                    }
                    if (PlayViewModel.this.room == null) {
                        for (int i = 3; i >= 0; --i) {
                            final int numCount = i;
                            countDownHandler.postDelayed(
                                    () -> publisher.onNext(Event.create(Event.COUNT_DOWN, numCount)),
                                    (3 - numCount) * 1000
                            );
                        }
                    }
                    countDownHandler.postDelayed(
                            () -> {
                                countTimeHost = 0;
                                countTimeGuest = 0;
                                notifyPropertyChanged(BR.countTimeHost);
                                notifyPropertyChanged(BR.countTimeGuest);
                                if (room.getRoom_number() == 0) {
                                    if (room.getNext_turn().equals("AI")) {
                                        isEnableHost = true;
                                        isEnableGuest = false;
                                        notifyPropertyChanged(BR.enableHost);
                                        notifyPropertyChanged(BR.enableGuest);
                                        getOpponentMove();
                                        for (int i = timePlaying; i >= 0; --i) {
                                            final int numCount = i;
                                            countDownHandler.postDelayed(
                                                    () -> {
                                                        countTimeHost = numCount;
                                                        notifyPropertyChanged(BR.countTimeHost);
                                                        if (countTimeHost == 0) {
                                                            gameState = Event.WIN;
                                                            publisher.onNext(Event.create(Event.RESULT, gameState));
                                                        }
                                                    },
                                                    (timePlaying - numCount) * 1000
                                            );
                                        }
                                    } else {
                                        isEnableHost = false;
                                        isEnableGuest = true;
                                        notifyPropertyChanged(BR.enableHost);
                                        notifyPropertyChanged(BR.enableGuest);
                                        for (int i = timePlaying; i >= 0; --i) {
                                            final int numCount = i;
                                            countDownHandler.postDelayed(
                                                    () -> {
                                                        countTimeGuest = numCount;
                                                        notifyPropertyChanged(BR.countTimeGuest);
                                                        if (countTimeGuest == 0) {
                                                            gameState = Event.LOSE;
                                                            publisher.onNext(Event.create(Event.RESULT, gameState));
                                                        }
                                                    },
                                                    (timePlaying - numCount) * 1000
                                            );
                                        }
                                    }
                                } else if (user != null && !room.getNext_turn().equals(user.getUid())) {
                                    getOpponentMove();
                                } else {

                                }
                            },
                            PlayViewModel.this.room == null ? 4000 : 0
                    );
                    PlayViewModel.this.room = room;
                    notifyPropertyChanged(BR.board);
                    notifyPropertyChanged(BR.avatar1);
                    notifyPropertyChanged(BR.avatar2);
                    notifyPropertyChanged(BR.score1);
                    notifyPropertyChanged(BR.score2);
                }
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    }

    class RoomReceiverOnlineObserver extends DisposableObserver<Room> {
        @Override
        public void onNext(Room room) {
            if (room.getAction() >= Room.START) {
                if(room.getAction() == Room.LEAVE || room.getAction() == Room.DESTROY){
                    publisher.onNext(Event.create(Event.OUT_ROOM));
                    return;
                }
                if (room.getAction() == Room.END) {
                    PlayViewModel.this.room = room;
                    notifyPropertyChanged(BR.board);
                    notifyPropertyChanged(BR.score1);
                    notifyPropertyChanged(BR.score2);
                    return;
                }
                if (gameState == Event.PLAYING) {
                    countDownHandler.removeCallbacksAndMessages(null);
                    if ((room.getBoard().getO_cells() + room.getBoard().getX_cells()) == room.getBoard().getMax_cells()) {
                        if(user.getUid().equals(room.getUser_1().getUid())){
                            if (room.getBoard().getO_cells() > room.getBoard().getX_cells()) {
                                gameState = Event.LOSE;
                            } else if (room.getBoard().getO_cells() < room.getBoard().getX_cells()) {
                                gameState = Event.WIN;
                            } else {
                                gameState = Event.DRAW;
                            }
                        }
                        else if (user.getUid().equals(room.getUser_2().getUid())){
                            if (room.getBoard().getO_cells() > room.getBoard().getX_cells()) {
                                gameState = Event.WIN;
                            } else if (room.getBoard().getO_cells() < room.getBoard().getX_cells()) {
                                gameState = Event.LOSE;
                            } else {
                                gameState = Event.DRAW;
                            }
                        }
                        else {
                            if (room.getBoard().getO_cells() > room.getBoard().getX_cells()) {
                                publisher.onNext(Event.create(Event.TOAST_USER_WIN, room.getUser_2().getName() + " win"));
                            } else if (room.getBoard().getO_cells() < room.getBoard().getX_cells()) {
                                publisher.onNext(Event.create(Event.TOAST_USER_WIN, room.getUser_1().getName() + " win"));
                            } else {
                                publisher.onNext(Event.create(Event.TOAST_USER_WIN, "Draw"));
                            }
                        }
                        publisher.onNext(Event.create(Event.RESULT, gameState));
                        PlayViewModel.this.room = room;
                        notifyPropertyChanged(BR.board);
                        return;
                    }
                    if(user.getUid().equals(room.getUser_1().getUid()) || user.getUid().equals(room.getUser_2().getUid())) {
                        if (PlayViewModel.this.room == null) {
                            for (int i = 3; i >= 0; --i) {
                                final int numCount = i;
                                countDownHandler.postDelayed(
                                        () -> publisher.onNext(Event.create(Event.COUNT_DOWN, numCount)),
                                        (3 - numCount) * 1000
                                );
                            }
                        }
                    }
                    countDownHandler.postDelayed(
                            () -> {
                                countTimeHost = 0;
                                countTimeGuest = 0;
                                notifyPropertyChanged(BR.countTimeHost);
                                notifyPropertyChanged(BR.countTimeGuest);
                                if (room.getNext_turn().equals(room.getUser_1().getUid())) {
                                    isEnableHost = true;
                                    isEnableGuest = false;
                                    notifyPropertyChanged(BR.enableHost);
                                    notifyPropertyChanged(BR.enableGuest);
                                    for (int i = timePlaying; i >= 0; --i) {
                                        final int numCount = i;
                                        countDownHandler.postDelayed(
                                                () -> {
                                                    countTimeHost = numCount;
                                                    notifyPropertyChanged(BR.countTimeHost);
                                                    if (countTimeHost == 0) {
                                                        playUsecase.execute(null,Event.TIME_OUT);
                                                        if(user.getUid().equals(room.getUser_1().getUid()))
                                                            gameState = Event.LOSE;
                                                        else if(user.getUid().equals(room.getUser_2().getUid()))
                                                            gameState = Event.WIN;
                                                        publisher.onNext(Event.create(Event.RESULT, gameState));
                                                    }
                                                },
                                                (timePlaying - numCount) * 1000
                                        );
                                    }
                                } else {
                                    isEnableHost = false;
                                    isEnableGuest = true;
                                    notifyPropertyChanged(BR.enableHost);
                                    notifyPropertyChanged(BR.enableGuest);
                                    for (int i = timePlaying; i >= 0; --i) {
                                        final int numCount = i;
                                        countDownHandler.postDelayed(
                                                () -> {
                                                    countTimeGuest = numCount;
                                                    notifyPropertyChanged(BR.countTimeGuest);
                                                    if (countTimeGuest == 0) {
                                                        playUsecase.execute(null,Event.TIME_OUT);
                                                        if(user.getUid().equals(room.getUser_1().getUid()))
                                                            gameState = Event.WIN;
                                                        else if(user.getUid().equals(room.getUser_2().getUid()))
                                                            gameState = Event.LOSE;
                                                        publisher.onNext(Event.create(Event.RESULT, gameState));
                                                    }
                                                },
                                                (timePlaying - numCount) * 1000
                                        );
                                    }
                                }
                            },
                            PlayViewModel.this.room == null ? 4000 : 0
                    );
                    PlayViewModel.this.room = room;
                    notifyPropertyChanged(BR.board);
                    notifyPropertyChanged(BR.avatar1);
                    notifyPropertyChanged(BR.avatar2);
                    notifyPropertyChanged(BR.score1);
                    notifyPropertyChanged(BR.score2);
                }
                return;
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    class ChatReceiverObserver extends DisposableObserver<Message> {

        @Override
        public void onNext(Message message) {
            messages = new ArrayList<>(messages);
            messages.add(message);
            onHelp(Event.create(Event.LOAD_MESSAGE, messages));
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    class LoginInfoObserver extends DisposableSingleObserver<Optional<User>> {
        @Override
        public void onSuccess(Optional<User> user) {
            if (user.isPresent()) {
                PlayViewModel.this.user = user.get();
                if (roomId.equals("AI")) {
                    notifyPropertyChanged(BR.score2);
                }
            } else {
                onHelp(Event.create(Event.LOGIN_USER, PlayViewModel.this.user));
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    }
}
