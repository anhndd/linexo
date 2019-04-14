package vn.edu.hcmut.linexo.data.mapper;

import vn.edu.hcmut.linexo.presentation.model.User;

public class RoomDB {
    private int action;
    private Integer room_number;
    private DBBoard board;
    private String last_turn;
    private String next_turn;
    private User user_1;
    private User user_2;
    private Boolean is_private;

    public RoomDB() {
    }

    public RoomDB(int action, Integer room_number, DBBoard board, String last_turn, String next_turn, User user_1, User user_2, Boolean is_private) {
        this.action = action;
        this.room_number = room_number;
        this.board = board;
        this.last_turn = last_turn;
        this.next_turn = next_turn;
        this.user_1 = user_1;
        this.user_2 = user_2;
        this.is_private = is_private;
    }

    public Integer getRoom_number() {
        return room_number;
    }

    public void setRoom_number(Integer room_number) {
        this.room_number = room_number;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public DBBoard getBoard() {
        return board;
    }

    public void setBoard(DBBoard board) {
        this.board = board;
    }

    public String getLast_turn() {
        return last_turn;
    }

    public void setLast_turn(String last_turn) {
        this.last_turn = last_turn;
    }

    public String getNext_turn() {
        return next_turn;
    }

    public void setNext_turn(String next_turn) {
        this.next_turn = next_turn;
    }

    public User getUser_1() {
        return user_1;
    }

    public void setUser_1(User user_1) {
        this.user_1 = user_1;
    }

    public User getUser_2() {
        return user_2;
    }

    public void setUser_2(User user_2) {
        this.user_2 = user_2;
    }

    public Boolean getIs_private() {
        return is_private;
    }

    public void setIs_private(Boolean is_private) {
        this.is_private = is_private;
    }
}
