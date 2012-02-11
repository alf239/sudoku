package org.acm.afilippov.sudoku.strategies;

import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Strategy;
import org.acm.afilippov.sudoku.Sudoku;
import org.acm.afilippov.sudoku.Utils;

public class BruteForceRule implements Strategy {

    public boolean apply(Sudoku sudoku) {
        Cell cell = firstUndecided(sudoku);
        if (cell == null)
            return false;

        for (int i = 0; i < sudoku.size(); i++) {
            if (!cell.allows(i))
                continue;

            System.out.println("Cell:  " + cell);
            sudoku.checkpoint();
            cell.set(i);

            System.out.println("Trying " + cell + "\n\n");
            System.out.println(sudoku);
            Utils.hbar();

            sudoku.solve();

            if (sudoku.isSolved())
                return true;

            if (sudoku.isValid())
                throw new IllegalStateException("We should've brute forced it further!");

            System.out.println("Wrong way, rolling back\n");
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
