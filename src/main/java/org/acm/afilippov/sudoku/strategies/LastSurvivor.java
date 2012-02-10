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
            if (cell.mask().get(i)) {
                if (cell.isDecided())
                    return false;

                cell.mask().clear();
                cell.mask().set(i);
                return true;
            }
        }

        throw new IllegalStateException("Survivor not found");
    }

    private int[] countOptions(Group group) {
        int counts[] = new int[Sudoku.BOARD_SIZE];
        for (Cell cell : group) {
            for (int i = 0; i < counts.length; i++)
                if (cell.mask().get(i))
                    counts[i]++;
        }
        return counts;
    }
}
