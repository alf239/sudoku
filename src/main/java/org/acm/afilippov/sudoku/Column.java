package org.acm.afilippov.sudoku;

public class Column extends Group {
    public Column(Sudoku sudoku, int nr) {
        super(sudoku, nr);
    }

    @Override
    protected Cell get(int i) {
        return sudoku.get(i, nr);
    }
}
