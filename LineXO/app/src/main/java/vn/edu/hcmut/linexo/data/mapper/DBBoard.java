package vn.edu.hcmut.linexo.data.mapper;

import java.util.List;

public class DBBoard {
    private Integer cell_x;
    private Integer cell_o;
    private Integer max_cell;

    private List<List<Integer>> pattern;

    public DBBoard(){}

    public DBBoard(Integer cell_x, Integer cell_o, Integer max_cell, List<List<Integer>> pattern) {
        this.cell_x = cell_x;
        this.cell_o = cell_o;
        this.max_cell = max_cell;
        this.pattern = pattern;
    }

    public Integer getCell_x() {
        return cell_x;
    }

    public void setCell_x(Integer cell_x) {
        this.cell_x = cell_x;
    }

    public Integer getCell_o() {
        return cell_o;
    }

    public void setCell_o(Integer cell_o) {
        this.cell_o = cell_o;
    }

    public Integer getMax_cell() {
        return max_cell;
    }

    public void setMax_cell(Integer max_cell) {
        this.max_cell = max_cell;
    }

    public List<List<Integer>> getPattern() {
        return pattern;
    }

    public void setPattern(List<List<Integer>> pattern) {
        this.pattern = pattern;
    }
}
