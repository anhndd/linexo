package vn.edu.hcmut.linexo.presentation.model;

public class Session {

    private int roomNumber;

    public Session(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}
