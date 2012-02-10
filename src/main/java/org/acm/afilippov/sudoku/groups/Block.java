package org.acm.afilippov.sudoku.groups;


import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Group;
import org.acm.afilippov.sudoku.Sudoku;

import static org.acm.afilippov.sudoku.Sudoku.BLOCK_SIZE;

public class Block extends Group {
    private final int col;
    private final int row;

    public Block(Sudoku sudoku, int nr) {
        super(sudoku);
        row = BLOCK_SIZE * (nr / BLOCK_SIZE);
        col = BLOCK_SIZE * (nr % BLOCK_SIZE);
        init();
    }

    @Override
    protected Cell get(int i) {
        return sudoku.get(row + (i / BLOCK_SIZE), col + (i % BLOCK_SIZE));
    }
}
