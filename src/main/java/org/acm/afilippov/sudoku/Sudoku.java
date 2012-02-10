package org.acm.afilippov.sudoku;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.BitSet;

import static java.lang.Math.sqrt;

public class Sudoku {
    public static final int BOARD_SIZE = 9;
    public static final int NR_OF_CELLS = BOARD_SIZE * BOARD_SIZE;
    public static final int BLOCK_SIZE = (int) sqrt(BOARD_SIZE);

    private BitSet[][] task = new BitSet[BOARD_SIZE][BOARD_SIZE];

    public Sudoku(int[] cells) {
        if (cells.length != NR_OF_CELLS)
            throw new IllegalArgumentException("We expect a board of " + BOARD_SIZE + "x" + BOARD_SIZE
                    + ", which is " + NR_OF_CELLS + " cells");

        for (int i = 0, k = 0; i < task.length; i++) {
            BitSet[] row = task[i];
            for (int j = 0; j < row.length; j++, k++) {

                row[j] = maskFor(cells[k]);
            }
        }
    }

    private BitSet maskFor(int x) {
        BitSet cell = emptyCell();
        if (x == 0)
            cell.set(0, BOARD_SIZE, true);
        else
            cell.set(x - 1);
        return cell;
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
        for (int j = 0; j < BOARD_SIZE; j++) {
            BitSet acc = emptyCell();
            for (int i = 0; i < BOARD_SIZE; i++) {
                acc.or(task[i][j]);
            }
            if (acc.cardinality() < BOARD_SIZE)
                return false;
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            BitSet acc = emptyCell();
            for (int j = 0; j < BOARD_SIZE; j++) {
                acc.or(task[i][j]);
            }
            if (acc.cardinality() < BOARD_SIZE)
                return false;
        }

        for (int k = 0; k < BOARD_SIZE; k++) {
            int ri = BLOCK_SIZE * ((k / BLOCK_SIZE) % BLOCK_SIZE);
            int rj = BLOCK_SIZE * (k / BLOCK_SIZE);
            BitSet acc = emptyCell();
            for (int m = 0; m < BOARD_SIZE; m++) {
                int i = ri + m % BLOCK_SIZE;
                int j = rj + m / BLOCK_SIZE;
                acc.or(task[i][j]);
            }
            if (acc.cardinality() < BOARD_SIZE)
                return false;
        }

        return true;
    }

    private BitSet emptyCell() {
        return new BitSet(BOARD_SIZE);
    }

    public boolean isSolved() {
        for (BitSet[] row : task) {
            for (BitSet cell : row) {
                if (cell.cardinality() > 1)
                    return false;
            }
        }
        return isValid();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < task.length; i++) {
            BitSet[] row = task[i];
            for (int j = 0; j < row.length; j++) {
                BitSet cell = row[j];
                if (cell.cardinality() > 1)
                    sb.append('_');
                else
                    sb.append(cell.nextSetBit(0) + 1);
                if (isBlockBorder(j))
                    sb.append(" ");
            }
            sb.append("\n");
            if (isBlockBorder(i))
                sb.append("\n");
        }
        return sb.toString();
    }

    private boolean isBlockBorder(int i) {
        return i != BOARD_SIZE - 1 &&
                i % BLOCK_SIZE == BLOCK_SIZE - 1;
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
}
