package org.acm.afilippov.sudoku.strategies;

import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Group;
import org.acm.afilippov.sudoku.Sudoku;

public class SimpleElimination {
    public boolean apply(Sudoku sudoku) {
        boolean flag = false;
        for (Cell cell : sudoku.cells()) {
            if (cell.isDecided()) {
                for (Group group : cell.getGroups()) {
                    for (Cell other : group) {
                        if (cell != other) {
                            int cardinality = other.mask().cardinality();
                            other.mask().andNot(cell.mask());
                            if (other.mask().cardinality() != cardinality)
                                flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }
}
