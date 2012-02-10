package org.acm.afilippov.sudoku;

public class Row extends Group {
    public Row(Sudoku sudoku, int i) {
        super(sudoku, i);
    }

    @Override
    protected Cell get(int i) {
        return sudoku.get(nr, i);
    }
}
