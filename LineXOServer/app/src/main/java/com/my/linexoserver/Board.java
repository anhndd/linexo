package com.my.linexoserver;


public class Board {
    private Integer cell_x;
    private Integer cell_o;
    private Integer max_cell;

    public static final int N_ROW = 11;
    public static final int N_COL = 19;

    private Integer[][] pattern;

    public Board(){
        cell_x = null;
        cell_o = null;
        max_cell = 25;
        pattern = null;
    }

    public Integer getMax_cell() {
        return max_cell;
    }

    public Integer getCell_x(){
        return cell_x;
    }

    public Integer getCell_o(){
        return cell_o;
    }


    public Integer[][] convertBoard(byte[][] board){
        int temp;
        Integer[][] int_board = new Integer[N_ROW][N_COL];
        for(int i = 0; i < N_ROW; i++){
            for (int j = 0; j < N_COL; j++){
                temp = board[i][j];
                int_board[i][j] = temp;
            }
        }
        return int_board;
    }
}
