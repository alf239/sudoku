package org.acm.afilippov.sudoku;

import org.acm.afilippov.sudoku.strategies.BruteForceRule;
import org.acm.afilippov.sudoku.strategies.EliminationRule;
import org.acm.afilippov.sudoku.strategies.IntersectionRule;
import org.acm.afilippov.sudoku.strategies.OnlyPlaceRule;

import java.util.Arrays;

import static org.acm.afilippov.sudoku.Utils.round;

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
            cells[i] = new Cell(v, task[i]);
        }

        for (int i = 0; i < groups.length; i++) {
            groups[i] = new Group(v);
        }

        for (int i = 0; i < cells.length; i++) {
            final int row = i / v.getSize();
            final int col = i % v.getSize();
            final int block = round(row, v.getRegionSize()) + col / v.getRegionSize();

            groups[row].add(cells[i]);
            groups[col + v.getSize()].add(cells[i]);
            groups[block + 2 * v.getSize()].add(cells[i]);
        }

        if (!isValid())
            throw new IllegalStateException("Invalid task");
    }

    public void solve() {
        Strategy[] strategies = {
                new EliminationRule(),
                new OnlyPlaceRule(),
                new IntersectionRule(),
                new BruteForceRule()
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
                    Utils.hbar();
                    if (isSolved())
                        return;
                    continue outer;
                }
            }
        } while (works);
    }

    public boolean isSolved() {
        return cardinality() == 1 && isValid();
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

    long cardinality() {
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

    public void checkpoint() {
        for (Cell c : cells)
            c.checkpoint();
    }

    public void rollback() {
        for (Cell c : cells)
            c.rollback();
    }
}
