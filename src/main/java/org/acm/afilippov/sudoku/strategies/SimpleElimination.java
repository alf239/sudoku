package org.acm.afilippov.sudoku.strategies;

import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Group;
import org.acm.afilippov.sudoku.Strategy;
import org.acm.afilippov.sudoku.Sudoku;

public class SimpleElimination implements Strategy {
    public boolean apply(Sudoku sudoku) {
        boolean flag = false;
        for (Cell cell : sudoku.cells()) {
            if (cell.isDecided()) {
                for (Group group : cell.getGroups()) {
                    for (Cell other : group) {
                        if (cell != other)
                            flag |= other.filter(cell.mask());
                    }
                }
            }
        }
        return flag;
    }
}
