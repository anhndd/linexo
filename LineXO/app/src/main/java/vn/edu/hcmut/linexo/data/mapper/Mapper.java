package vn.edu.hcmut.linexo.data.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Room;

public class Mapper {
    public static Room convertRoomDB2Room(RoomDB roomDB) {
        return new Room(roomDB.getRoom_id(), roomDB.getAction(), roomDB.getRoom_number(), roomDB.getBoard() == null ? null : convertDBBoard2Board(roomDB.getBoard()), roomDB.getLast_turn(),
                roomDB.getNext_turn(), roomDB.getUser_1(), roomDB.getUser_2(), roomDB.getIs_private(), roomDB.getOnline_timestamp());
    }

    public static RoomDB convertRoom2RoomDB(Room room) {
        return new RoomDB(room.getRoom_id(), room.getAction(), room.getRoom_number(), room.getBoard() == null ? null : convertBoard2DBBoard(room.getBoard()), room.getLast_turn(),
                room.getNext_turn(), room.getUser_1(), room.getUser_2(), room.getIs_private(), room.getOnline_timestamp());
    }

    public static DBBoard convertBoard2DBBoard(Board board) {
        List<List<Integer>> dbboard = new ArrayList<>();
        for (int i = 0; i < board.getHeight(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < board.getWidth(); j++) {
                row.add(new Integer(board.getValueAt(j, i)));
            }
            dbboard.add(row);
        }
        return new DBBoard(board.getX_cells(), board.getO_cells(), board.getMax_cells(), dbboard);
    }

    public static Board convertDBBoard2Board(DBBoard dbBoard) {
        byte[][] board = new byte[dbBoard.getPattern().size()][dbBoard.getPattern().get(0).size()];
        for (int i = 0; i < dbBoard.getPattern().size(); i++) {
            for (int j = 0; j < dbBoard.getPattern().get(0).size(); j++) {
                board[i][j] = dbBoard.getPattern().get(i).get(j).byteValue();
            }
        }
        return new Board(board, dbBoard.getCell_x(), dbBoard.getCell_o(), dbBoard.getMax_cell());
    }
}
