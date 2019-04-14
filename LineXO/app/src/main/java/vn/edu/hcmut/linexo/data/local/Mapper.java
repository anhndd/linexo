package vn.edu.hcmut.linexo.data.local;

import vn.edu.hcmut.linexo.presentation.model.Board;

import static vn.edu.hcmut.linexo.data.local.DBBoard.N_COL;
import static vn.edu.hcmut.linexo.data.local.DBBoard.N_ROW;

public class Mapper {
    public static DBBoard convertBoard2DBBoard(Board board){
        int temp;
        Integer[][] int_board = new Integer[N_ROW][N_COL];
        for(int i = 0; i < N_ROW; i++){
            for (int j = 0; j < N_COL; j++){
                temp = board.getPattern()[i][j];
                int_board[i][j] = temp;
            }
        }
        return new DBBoard(int_board);
    }

    public static Board convertDBBoard2Board(DBBoard dbBoard){
        int temp;
        byte[][] byte_board = new byte[N_ROW][N_COL];
        for(int i = 0; i < N_ROW; i++){
            for (int j = 0; j < N_COL; j++){
                temp = dbBoard.getPattern()[i][j];
                byte_board[i][j] = (byte) temp;
            }
        }
        return new Board(byte_board);
    }

    public static void main(String[] args){
        byte[][] byte_board = {
                {0,3,0,3,0,3,0,3,0,3,0,3,0,3,0,3,0,3,0}, // 1
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 2
                {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0}, // 3
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 4
                {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0}, // 5
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 6
                {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0}, // 7
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 8
                {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0}, // 9
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 10
                {0,3,0,3,0,3,0,3,0,3,0,3,0,3,0,3,0,3,0}, // 11
        };
        System.out.println(convertBoard2DBBoard(new Board(byte_board)));

        Integer[][] int_board = {
                {0,3,0,3,0,3,0,3,0,3,0,3,0,3,0,3,0,3,0}, // 1
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 2
                {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0}, // 3
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 4
                {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0}, // 5
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 6
                {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0}, // 7
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 8
                {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0}, // 9
                {3,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,1,4,3}, // 10
                {0,3,0,3,0,3,0,3,0,3,0,3,0,3,0,3,0,3,0}, // 11
        };
        System.out.println(convertDBBoard2Board(new DBBoard(int_board)));
    }
}
