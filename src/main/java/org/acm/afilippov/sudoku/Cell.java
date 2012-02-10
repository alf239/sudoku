package org.acm.afilippov.sudoku;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static java.lang.Integer.toHexString;

public class Cell {
    private BitSet mask;
    private List<Group> groups = new ArrayList<Group>(3);

    private Cell(BitSet mask) {
        this.mask = mask;
    }

    protected void join(Group group) {
        groups.add(group);
    }

    public List<? extends Group> getGroups() {
        return groups;
    }

    public static Cell any() {
        BitSet mask = new BitSet(Sudoku.BOARD_SIZE);
        mask.set(0, Sudoku.BOARD_SIZE);
        return new Cell(mask);
    }

    public static Cell only(int x) {
        BitSet mask = new BitSet(Sudoku.BOARD_SIZE);
        mask.set(x - 1);
        return new Cell(mask);
    }

    public boolean isDecided() {
        return mask.cardinality() == 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Sudoku.BOARD_SIZE; i++) {
            sb.append(mask.get(i) ? toString(i + 1) : ".");
        }
        return sb.toString();
    }

    private String toString(int i) {
        if (i < 15)
            return toHexString(i);
        if (i < 10 + ('z' - 'a'))
            return Character.toString((char)(i - 10 + 'a'));
        return "*";
    }

    public boolean isValid() {
        return mask.cardinality() > 0;
    }

    public BitSet mask() {
        return mask;
    }
}
