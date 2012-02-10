package org.acm.afilippov.sudoku.groups;

import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Group;
import org.acm.afilippov.sudoku.Sudoku;

public class Row extends Group {
    private final int row;

    public Row(Sudoku sudoku, int row) {
        super(sudoku);
        this.row = row;
        init();
    }

    @Override
    protected Cell get(int i) {
        return sudoku.get(row, i);
    }
}
