package vn.edu.hcmut.linexo.data.local;

public class DBBoard {
    private Integer cell_x;
    private Integer cell_o;
    private Integer max_cell;

    public static final int N_ROW = 11;
    public static final int N_COL = 19;

    private Integer[][] pattern;

    public DBBoard(Integer[][] pattern){
        cell_x = 0;
        cell_o = 0;
        max_cell = 0;
        this.pattern = pattern;
    }

    public int getMax_cell() {
        return max_cell;
    }

    public int getCell_x(){
        return cell_x;
    }

    public int getCell_o(){
        return cell_o;
    }

    public Integer[][] getPattern() {
        return pattern;
    }

    public void setPattern(Integer[][] pattern) {
        this.pattern = pattern;
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
