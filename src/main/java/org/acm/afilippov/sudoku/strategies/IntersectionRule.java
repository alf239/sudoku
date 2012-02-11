package org.acm.afilippov.sudoku.strategies;

import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Group;
import org.acm.afilippov.sudoku.Strategy;
import org.acm.afilippov.sudoku.Sudoku;

import java.util.HashSet;
import java.util.Set;

public class IntersectionRule implements Strategy {
    public boolean apply(Sudoku sudoku) {
        boolean flag = false;
        for (Group a : sudoku.groups()) {
            for (Group b : sudoku.groups()) {
                if (a == b)
                    continue;

                Set<Cell> aonly = new HashSet<Cell>(a.cells());
                Set<Cell> both = new HashSet<Cell>(a.cells());

                both.retainAll(b.cells());
                aonly.removeAll(both);

                int aomask = or(aonly);
                int abmask = or(both);
                abmask &= ~aomask;
                if (abmask != 0) {
                    Set<Cell> bonly = new HashSet<Cell>(b.cells());
                    bonly.removeAll(both);
                    for (Cell c : bonly) {
                        flag |= c.filter(abmask);
                    }
                }
            }
        }
        return flag;
    }

    private static int or(Set<Cell> cells) {
        int result = 0;
        for (Cell cell : cells) {
            result |= cell.mask();
        }
        return result;
    }
}
