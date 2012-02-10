package org.acm.afilippov.sudoku;

import org.acm.afilippov.sudoku.strategies.LastSurvivor;
import org.acm.afilippov.sudoku.strategies.SimpleElimination;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

import static java.lang.Math.sqrt;

public class Sudoku {
    public static final int BOARD_SIZE = 9;
    public static final int NR_OF_CELLS = BOARD_SIZE * BOARD_SIZE;
    public static final int BLOCK_SIZE = (int) sqrt(BOARD_SIZE);

    private Cell[] cells = new Cell[NR_OF_CELLS];
    private Group[] groups = new Group[BOARD_SIZE * 3];

    public Sudoku(int[] task) {
        if (task.length != cells.length)
            throw new IllegalArgumentException("We expect a board of " + BOARD_SIZE + "x" + BOARD_SIZE + ", "
                    + "that is, " + NR_OF_CELLS + " cells");

        for (int i = 0; i < cells.length; i++) {
            cells[i] = task[i] == 0 ? Cell.any() : Cell.only(task[i]);
        }

        for (int i = 0; i < groups.length; i++) {
            groups[i] = new Group();
        }

        for (int i = 0; i < cells.length; i++) {
            int row = i / BOARD_SIZE;
            int col = i % BOARD_SIZE;
            int block = round(row, BLOCK_SIZE) + col / BLOCK_SIZE;

            groups[row].add(cells[i]);
            groups[BOARD_SIZE + col].add(cells[i]);
            groups[2 * BOARD_SIZE + block].add(cells[i]);
        }

        for (Cell cell : cells) {
            if (cell.getGroups().size() != 3)
                throw new IllegalStateException("Invalid number of groups! " + cell);
        }
    }

    public void solve() {
        Strategy[] strategies = {
                new SimpleElimination(),
                new LastSurvivor()
        };

        boolean works;
        do {
            works = false;
            for (Strategy strategy : strategies) {
                while (strategy.apply(this)) {
                    System.out.println(toString());
                    System.out.println("\n=============================");
                    works = true;
                }
            }
        } while (works);
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
            if (!cell.isDecided())
                return false;

        return isValid();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (i % BLOCK_SIZE == 0)
                sb.append("\n");
            sb.append(groups[i]).append("\n");
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
            else if (c >= 'a' && c <= 'f')
                cells[i++] = c + 10 - 'a';
            else if (c >= 'A' && c <= 'F')
                cells[i++] = c + 10 - 'A';
        }
        return new Sudoku(cells);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java org.acm.afilippov.sudoku.Sudoku <filename>\n\n");
            System.exit(-1);
        }

        Sudoku sudoku = readTask(new FileReader(args[0]));
        sudoku.solve();

        System.out.println(sudoku.toString());
        System.out.println("sudoku.isSolved() = " + sudoku.isSolved());
    }

    public Iterable<? extends Cell> cells() {
        return Arrays.asList(cells);
    }

    public Iterable<? extends Group> groups() {
        return Arrays.asList(groups);
    }

    private static int round(int a, int r) {
        return a - a % r;
    }
}
