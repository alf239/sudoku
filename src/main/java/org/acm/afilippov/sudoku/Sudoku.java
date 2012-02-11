package org.acm.afilippov.sudoku;

import org.acm.afilippov.sudoku.strategies.EliminationRule;
import org.acm.afilippov.sudoku.strategies.IntersectionRule;
import org.acm.afilippov.sudoku.strategies.OnlyPlaceRule;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class Sudoku {

    private final Variation v;
    private final Cell[] cells;
    private final Group[] groups;

    public Sudoku(int[] task, Variation v) {
        if (task.length != v.getTotal())
            throw new IllegalArgumentException("We expect a board of " + v.getSize() + "x" + v.getSize() + ", "
                    + "that is, " + v.getTotal() + " cells");

        this.v = v;
        cells = new Cell[v.getTotal()];
        groups = new Group[v.getSize() * 3];

        for (int i = 0; i < cells.length; i++) {
            cells[i] = task[i] == v.getMissingValue() ? Cell.any(v) : Cell.only(v, task[i]);
        }

        for (int i = 0; i < groups.length; i++) {
            groups[i] = new Group(v);
        }

        for (int i = 0; i < cells.length; i++) {
            int row = i / v.getSize();
            int col = i % v.getSize();
            int block = round(row, v.getRegionSize()) + col / v.getRegionSize();

            groups[row].add(cells[i]);
            groups[v.getSize() + col].add(cells[i]);
            groups[2 * v.getSize() + block].add(cells[i]);
        }

        if (!isValid())
            throw new IllegalStateException("Invalid task");
    }

    public void solve() {
        Strategy[] strategies = {
                new EliminationRule(),
                new OnlyPlaceRule(),
                new IntersectionRule()
        };

        boolean works;
        outer:
        do {
            works = false;
            for (Strategy strategy : strategies) {
                if (strategy.apply(this)) {
                    works = true;

                    System.out.println("strategy = " + strategy);
                    System.out.println();
                    System.out.println(this);
                    hbar();
                    continue outer;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < v.getSize(); i++) {
            if (i != 0) {
                sb.append("\n");
                if (i % v.getRegionSize() == 0)
                    sb.append("\n");
            }
            sb.append(groups[i]);
        }
        return sb.toString();
    }

    public static Sudoku readTask(Reader reader, Variation variation) throws IOException {
        int[] cells = new int[variation.getTotal()];
        for (int c, i = 0; (c = reader.read()) != -1; ) {
            if (c == '_')
                cells[i++] = variation.getMissingValue();
            else {
                int digit = Character.digit(c, Character.MAX_RADIX);
                if (digit != -1)
                    cells[i++] = digit;
            }
        }
        return new Sudoku(cells, variation);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java org.acm.afilippov.sudoku.Sudoku <filename>\n\n");
            System.exit(-1);
        }

        Sudoku sudoku = readTask(new FileReader(args[0]), Variation.CLASSIC);

        System.out.println("sudoku = \n" + sudoku);
        hbar();

        sudoku.solve();

        System.out.println("sudoku.cardinality() = " + sudoku.cardinality());
    }

    private long cardinality() {
        long result = 1;
        for (Cell c : cells)
            result *= c.cardinality();
        return result;
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

    private static void hbar() {
        System.out.println("\n=============================\n");
    }
}
