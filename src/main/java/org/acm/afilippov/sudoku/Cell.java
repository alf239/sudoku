package org.acm.afilippov.sudoku;

import java.util.*;

import static java.lang.Integer.bitCount;

public class Cell {
    private final Variation variation;
    private final Deque<Integer> fallback = new ArrayDeque<Integer>();
    private int mask;
    private final Group[] groups = new Group[3];
    private int g = 0;

    Cell(Variation variation, int value) {
        this.variation = variation;
        this.mask = variation.maskFor(value);
    }

    public boolean filter(int drop) {
        int old = mask;
        mask &= ~drop;
        return mask != old;
    }

    void join(Group group) {
        groups[g++] = group;
    }

    public List<? extends Group> getGroups() {
        return Arrays.asList(groups);
    }

    public boolean isDecided() {
        return bitCount(mask) == 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < variation.getSize(); i++) {
            sb.append(allows(i) ? toString(i + variation.getBase()) : ".");
        }
        return sb.toString();
    }

    public String result() {
        if (isDecided())
            return toString(Integer.numberOfTrailingZeros(mask) + variation.getBase());
        if (bitCount(mask) == variation.getSize())
            return "_";
        return "?";
    }

    private static String toString(int i) {
        if (i < 0)
            throw new IllegalArgumentException("Negative digits are not allowed");
        if (i < Character.MAX_RADIX)
            return "" + Character.forDigit(i, Character.MAX_RADIX);
        return "*";
    }

    public boolean isConflicting() {
        return mask == 0;
    }

    public int mask() {
        return mask;
    }

    public int cardinality() {
        return bitCount(mask);
    }

    public boolean allows(int i) {
        return (mask & (1 << i)) != 0;
    }

    public List<Integer> allowedValues() {
        List<Integer> result = new ArrayList<Integer>(bitCount(mask));
        for (int i = 0; i < variation.getSize(); i++) {
            if (allows(i))
                result.add(i);
        }
        return result;
    }

    public void set(int i) {
        mask = 1 << i;
    }

    public void checkpoint() {
        fallback.push(mask);
    }

    public void rollback() {
        mask = fallback.pop();
    }
}
