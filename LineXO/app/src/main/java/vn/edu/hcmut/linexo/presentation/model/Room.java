package vn.edu.hcmut.linexo.presentation.model;

public class Room {
    private int action;

    public Room() {
    }

    public Room(int action, Integer board_number, Board board, String last_turn, String next_turn, User user_1, User user_2) {
        this.action = action;
        this.board_number = board_number;
        this.board = board;
        this.last_turn = last_turn;
        this.next_turn = next_turn;
        this.user_1 = user_1;
        this.user_2 = user_2;
    }

    private Integer board_number;
    private Board board;
    private String last_turn;
    private String next_turn;
    private User user_1;
    private User user_2;

    public Integer getBoard_number() {
        return board_number;
    }

    public void setBoard_number(Integer board_number) {
        this.board_number = board_number;
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
}
