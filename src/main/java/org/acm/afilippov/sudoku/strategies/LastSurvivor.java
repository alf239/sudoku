package org.acm.afilippov.sudoku.strategies;

import org.acm.afilippov.sudoku.Cell;
import org.acm.afilippov.sudoku.Group;
import org.acm.afilippov.sudoku.Strategy;
import org.acm.afilippov.sudoku.Sudoku;

public class LastSurvivor implements Strategy {
    public boolean apply(Sudoku sudoku) {
        boolean flag = false;
        for (Group group : sudoku.groups()) {
            int[] counts = countOptions(group);
            for (int i = 0; i < counts.length; i++) {
                if (counts[i] == 1) {
                    flag |= selectSurvivor(group, i);
                }
            }
        }
        return flag;
    }

    private boolean selectSurvivor(Group group, int i) {
        for (Cell cell : group) {
            if (cell.allows(i)) {
                if (cell.isDecided())
                    return false;

                cell.filter(~(1 << i));
                return true;
            }
        }

        throw new IllegalStateException("Survivor not found");
    }

    private int[] countOptions(Group group) {
        int counts[] = new int[group.getSize()];
        for (Cell cell : group) {
            for (int i = 0, m = 1; i < group.getSize(); i++) {
                if ((cell.mask() & m) != 0)
                    counts[i]++;
                m <<= 1;
            }
        }
        return counts;
    }
}
