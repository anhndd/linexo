package com.my.linexoserver;

public class Room {
    public final static int CREATE = 0;
    public final static int RANDOM = 1;
    public final static int JOIN = 2;
    public final static int START = 3;
    public final static int MOVE = 4;
    public final static int END = 5;
    public final static int LEAVE = 6;
    public final static int DESTROY = 7;

    private int action;
    private Integer room_number;
    private Board board;
    private String last_turn;
    private String next_turn;
    private User user_1;
    private User user_2;
    private Boolean isPrivate;
    private Long online_timestamp;

    public Room() {
    }

    public Room(int action, Integer room_number, Board board, String last_turn, String next_turn, User user_1, User user_2, Boolean isPrivate) {
        this.action = action;
        this.room_number = room_number;
        this.board = board;
        this.last_turn = last_turn;
        this.next_turn = next_turn;
        this.user_1 = user_1;
        this.user_2 = user_2;
        this.isPrivate = isPrivate;
    }

    public Room(int action, Integer room_number, Board board, String last_turn, String next_turn, User user_1, User user_2, Boolean isPrivate, Long online_timestamp) {
        this.action = action;
        this.room_number = room_number;
        this.board = board;
        this.last_turn = last_turn;
        this.next_turn = next_turn;
        this.user_1 = user_1;
        this.user_2 = user_2;
        this.isPrivate = isPrivate;
        this.online_timestamp = online_timestamp;
    }

    public Long getOnline_timestamp() {
        return online_timestamp;
    }

    public void setOnline_timestamp(Long online_timestamp) {
        this.online_timestamp = online_timestamp;
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
