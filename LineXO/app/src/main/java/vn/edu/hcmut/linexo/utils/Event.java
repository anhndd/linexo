package vn.edu.hcmut.linexo.utils;

/**
 * A container object which contain an event code and {@code @nullable} data of it.
 * It may be use to emit event to some listener.
 */

public class Event {

    /**
     * Used when need to return an event but do nothing or default action.
     */
    public static final int NONE = -1;
    public static final int INIT_GAME = 0;
    public static final int SEND_MOVE = 1;
    public static final int GET_MOVE = 2;
    public static final int CLICK_ROOM = 3;
    public static final int LOAD_LIST_ROOM = 4;
    public static final int LOGIN_INFO = 5;
    public static final int LOGOUT = 6;
    public static final int END_SPLASH = 7;
    public static final int LOAD_FULL_USER_INFO = 8;
    public static final int SHOW_LOGIN = 9;
    public static final int SHOW_PLAY_ACTIVITY = 10;
    public static final int LOGIN_USER = 11;
    public static final int SHOW_POPUP_USER_ON = 12;
    public static final int SHOW_POPUP_USER_OFF = 13;
    public static final int LOAD_MESSAGE = 14;
    public static final int LOAD_PLAY_INFO = 15;
    public static final int KEYBOARD_CHANGED = 16;
    public static final int SMOOTH_MESSAGE_LIST = 17;
    public static final int PUSH_MESSAGE = 18;
    public static final int COUNT_DOWN = 19;
    public static final int PLAYING = 20;
    public static final int LOSE = 21;
    public static final int DRAW = 22;
    public static final int WIN = 23;
    public static final int RESULT = 24;


    /***********************************************************************************************
     * Class implementation.
     **********************************************************************************************/

    private int type;
    private Object[]    data;

    public static Event create(int type) {
        return new Event(type, (Object)null);
    }

    public static Event create(int type, Object... data) {
        return new Event(type, data);
    }

    private Event(int type, Object... data) {
        this.type = type;
        this.data = data;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setData(Object... data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public Object[] getData() {
        return data;
    }
}
