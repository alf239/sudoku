package org.acm.afilippov.sudoku;

import org.acm.afilippov.sudoku.strategies.LastSurvivor;
import org.acm.afilippov.sudoku.strategies.SimpleElimination;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class Sudoku {

    private final Variation v;
    private final Cell[] cells;
    private Group[] groups;

    public Sudoku(int[] task, Variation variation) {
        if (task.length != variation.getTotal())
            throw new IllegalArgumentException("We expect a board of "
                    + variation.getSize() + "x" + variation.getSize() + ", "
                    + "that is, " + variation.getTotal() + " cells");

        v = variation;
        cells = new Cell[variation.getTotal()];
        groups = new Group[variation.getSize() * 3];

        for (int i = 0; i < cells.length; i++) {
            cells[i] = task[i] == variation.getMissingValue() ? Cell.any(variation) : Cell.only(variation, task[i]);
        }

        for (int i = 0; i < groups.length; i++) {
            groups[i] = new Group(variation.getSize());
        }

        for (int i = 0; i < cells.length; i++) {
            int row = i / v.getSize();
            int col = i % v.getSize();
            int block = round(row, v.getRegionSize()) + col / v.getRegionSize();

            groups[row].add(cells[i]);
            groups[v.getSize() + col].add(cells[i]);
            groups[2 * variation.getSize() + block].add(cells[i]);
        }

        if (!isValid())
            throw new IllegalStateException("Invalid task");
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
                    works = true;

                    System.out.println(this);
                    System.out.println("\n");
                    System.out.println("=============================");
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
        for (int i = 0; i < v.getSize(); i++) {
            if (i % v.getRegionSize() == 0)
                sb.append("\n");
            sb.append(groups[i]).append("\n");
        }
        return sb.toString();
    }

    public static Sudoku readTask(Reader reader, Variation variation) throws IOException {
        int[] cells = new int[variation.getTotal()];
        for (int c, i = 0; (c = reader.read()) != -1; ) {
            if (c == '_')
                cells[i++] = variation.getMissingValue();
            else if (c >= '0' && c <= '9')
                cells[i++] = c - '0';
            else if (c >= 'a' && c <= 'f')
                cells[i++] = c + 10 - 'a';
            else if (c >= 'A' && c <= 'F')
                cells[i++] = c + 10 - 'A';
        }
        return new Sudoku(cells, variation);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java org.acm.afilippov.sudoku.Sudoku <filename>\n\n");
            System.exit(-1);
        }

        Sudoku sudoku = readTask(new FileReader(args[0]), Variation.CLASSIC);
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
