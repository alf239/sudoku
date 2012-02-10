package org.acm.afilippov.sudoku.strategies;

import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Group;
import org.acm.afilippov.sudoku.Strategy;
import org.acm.afilippov.sudoku.Sudoku;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class HiddenTwins implements Strategy {
    public boolean apply(Sudoku sudoku) {
        boolean flag = false;
        for (Group a : sudoku.groups()) {
            for (Group b : sudoku.groups()) {
                if (a == b)
                    continue;

                Set<Cell> aonly = new HashSet<Cell>(a.cells());
                Set<Cell> both = new HashSet<Cell>(a.cells());
                Set<Cell> bonly = new HashSet<Cell>(b.cells());

                both.retainAll(bonly);
                aonly.removeAll(both);

                BitSet aomask = or(aonly);
                BitSet abmask = or(both);
                abmask.andNot(aomask);
                if (abmask.cardinality() > 0) {
                    bonly.removeAll(both);
                    for (Cell c : bonly) {
                        flag |= SimpleElimination.filter(abmask, c.mask());
                    }
                }
            }
        }
        return flag;
    }

    private static BitSet or(Set<Cell> cells) {
        BitSet result = new BitSet();
        for (Cell cell : cells) {
            result.or(cell.mask());
        }
        return result;
    }
}
