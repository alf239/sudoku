package org.acm.afilippov.sudoku;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static java.lang.Math.sqrt;

public class Sudoku {
    public static final int BOARD_SIZE = 9;
    public static final int NR_OF_CELLS = BOARD_SIZE * BOARD_SIZE;
    public static final int BLOCK_SIZE = (int) sqrt(BOARD_SIZE);

    private Cell[] cells = new Cell[NR_OF_CELLS];
    private Group[] groups = new Group[BOARD_SIZE * 3];

    public Sudoku(int[] cells) {
        if (cells.length != NR_OF_CELLS)
            throw new IllegalArgumentException("We expect a board of " + BOARD_SIZE + "x" + BOARD_SIZE
                    + ", which is " + NR_OF_CELLS + " cells");

        for (int i = 0; i < NR_OF_CELLS; i++) {
            this.cells[i] = cells[i] == 0 ? Cell.any() : Cell.only(cells[i]);
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            groups[i] = new Row(this, i);
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            groups[BOARD_SIZE + i] = new Column(this, i);
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            groups[2 * BOARD_SIZE + i] = new Block(this, i);
        }
    }

    /**
     * The notion of validity is very relaxed here: e.g., line 222 222 222 will be fine. What saves us is that
     * elimination step will bring this line into an invalid state on its next turn, so we're just postponing
     * the solution (we don't want out check for validity to be NP hard)
     *
     * @return true if all the cells are non-empty, and each line or block has places for any value;
     *         false otherwise.
     */
    public boolean isValid() {
        for (Cell cell : cells)
            if (!cell.isValid())
                return false;
        for (Group group : groups)
            if (!group.isValid())
                return false;
        return true;
    }

    public boolean isSolved() {
        for (Cell cell : cells)
            if (cell.isDecided())
                return false;

        return isValid();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            sb.append(groups[i]).append("\n");
            if (i != BOARD_SIZE - 1 && i % BLOCK_SIZE == BLOCK_SIZE - 1)
                sb.append("\n");
        }
        return sb.toString();
    }

    public static Sudoku readTask(Reader reader) throws IOException {
        int[] cells = new int[NR_OF_CELLS];
        for (int c, i = 0; (c = reader.read()) != -1; ) {
            if (c == '_')
                cells[i++] = 0;
            else if (c >= '0' && c <= '9')
                cells[i++] = c - '0';
        }
        return new Sudoku(cells);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java org.acm.afilippov.sudoku.Sudoku <filename>\n\n");
            System.exit(-1);
        }

        Sudoku sudoku = readTask(new FileReader(args[0]));
        System.out.println(sudoku.toString());
        System.out.println("sudoku.isValid() = " + sudoku.isValid());
    }

    public Cell get(int row, int col) {
        return cells[row * BOARD_SIZE + col];
    }
}
