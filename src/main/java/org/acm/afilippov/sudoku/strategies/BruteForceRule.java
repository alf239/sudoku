package org.acm.afilippov.sudoku.strategies;

import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Strategy;
import org.acm.afilippov.sudoku.Sudoku;

public class BruteForceRule implements Strategy {

    public boolean apply(Sudoku sudoku) {
        Cell cell = firstUndecided(sudoku);
        if (cell == null)
            return false;

        for (int i : cell.allowedValues()) {
            sudoku.checkpoint();

            cell.set(i);

            sudoku.solve();

            if (sudoku.isSolved())
                return true;

            if (sudoku.isValid())
                throw new IllegalStateException("We should have brute forced it further!");

            sudoku.rollback();
        }
        return false;
    }

    private Cell firstUndecided(Sudoku sudoku) {
        for (Cell cell : sudoku.cells()) {
            if (!cell.isDecided()) {
                return cell;
            }
        }
        return null;
    }
}
