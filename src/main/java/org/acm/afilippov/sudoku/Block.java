package org.acm.afilippov.sudoku;


import static org.acm.afilippov.sudoku.Sudoku.BLOCK_SIZE;

public class Block extends Group {
    private final int col;
    private final int row;

    public Block(Sudoku sudoku, int nr) {
        super(sudoku, nr);
        col = BLOCK_SIZE * ((nr / BLOCK_SIZE) % BLOCK_SIZE);
        row = BLOCK_SIZE * (nr / BLOCK_SIZE);
    }

    @Override
    protected Cell get(int i) {
        return sudoku.get(row + i / BLOCK_SIZE, col + i % BLOCK_SIZE);
    }
}
