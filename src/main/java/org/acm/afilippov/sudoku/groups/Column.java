package org.acm.afilippov.sudoku.groups;

import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Group;
import org.acm.afilippov.sudoku.Sudoku;

public class Column extends Group {
    private final int col;

    public Column(Sudoku sudoku, int col) {
        super(sudoku);
        this.col = col;
        init();
    }

    @Override
    protected Cell get(int i) {
        return sudoku.get(i, col);
    }
}
