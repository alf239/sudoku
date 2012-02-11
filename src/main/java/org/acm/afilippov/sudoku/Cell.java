package org.acm.afilippov.sudoku;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import static java.lang.Integer.bitCount;

public class Cell {
    private final Variation variation;
    private final Deque<Integer> fallback = new ArrayDeque<Integer>();
    private int mask;
    private final Group[] groups = new Group[3];
    private int g = 0;

    private Cell(Variation variation, int mask) {
        this.variation = variation;
        this.mask = mask;
    }

    public boolean filter(int drop) {
        int old = mask;
        mask &= ~drop;
        return mask != old;
    }

    protected void join(Group group) {
        groups[g++] = group;
    }

    public List<? extends Group> getGroups() {
        return Arrays.asList(groups);
    }

    public static Cell any(Variation variation) {
        int mask = -1 >>> (32 - variation.getSize());
        return new Cell(variation, mask);
    }

    public static Cell only(Variation variation, int x) {
        int mask = 1 << (x - variation.getBase());
        return new Cell(variation, mask);
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

    private String toString(int i) {
        if (i < 0)
            throw new IllegalArgumentException("Negative digits are not allowed");
        if (i < Character.MAX_RADIX)
            return "" + Character.forDigit(i, Character.MAX_RADIX);
        return "*";
    }

    public boolean isValid() {
        for (Group group : groups) {
            if (group == null)
                return false;
        }
        return bitCount(mask) > 0;
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
