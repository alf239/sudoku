package org.acm.afilippov.sudoku;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static java.lang.Integer.toHexString;

public class Cell {
    private final Variation variation;
    private final BitSet mask;
    private final Group[] groups = new Group[3];
    private int g = 0;

    private Cell(Variation variation, BitSet mask) {
        this.variation = variation;
        this.mask = mask;
    }

    protected void join(Group group) {
        groups[g++] = group;
    }

    public List<? extends Group> getGroups() {
        return Arrays.asList(groups);
    }

    public static Cell any(Variation variation) {
        BitSet mask = new BitSet(variation.getSize());
        mask.set(0, variation.getSize());
        return new Cell(variation, mask);
    }

    public static Cell only(Variation variation, int x) {
        BitSet mask = new BitSet(variation.getSize());
        mask.set(x - variation.getBase());
        return new Cell(variation, mask);
    }

    public boolean isDecided() {
        return mask.cardinality() == 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < variation.getSize(); i++) {
            sb.append(mask.get(i) ? toString(i + variation.getBase()) : ".");
        }
        return sb.toString();
    }

    private String toString(int i) {
        if (i < 15)
            return toHexString(i);
        if (i < 10 + ('z' - 'a'))
            return Character.toString((char) (i - 10 + 'a'));
        return "*";
    }

    public boolean isValid() {
        for (Group group : groups) {
            if (group == null)
                return false;
        }
        return mask.cardinality() > 0;
    }

    public BitSet mask() {
        return mask;
    }

    public int cardinality() {
        return mask.cardinality();
    }
}
