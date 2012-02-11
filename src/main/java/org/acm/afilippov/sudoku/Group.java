package org.acm.afilippov.sudoku;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static java.lang.Integer.bitCount;

public class Group implements Iterable<Cell> {
    private final Variation v;
    private final Cell[] cells;
    private int c = 0;

    Group(Variation v) {
        this.v = v;
        cells = new Cell[v.getSize()];
    }

    public Iterator<Cell> iterator() {
        return Arrays.asList(cells).iterator();
    }

    public void add(Cell cell) {
        cells[c++] = cell;
        cell.join(this);
    }

    public int getSize() {
        return cells.length;
    }

    public boolean isValid() {
        return bitCount(maskDisjunction(this)) == cells.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Cell cell : this) {
            if (i != 0) {
                sb.append("   ");
                if (i % v.getRegionSize() == 0)
                    sb.append("   ");
            }
            sb.append(cell);
            i++;
        }
        return sb.toString();
    }

    public String result() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Cell cell : this) {
            if (i != 0) {
                sb.append(" ");
                if (i % v.getRegionSize() == 0)
                    sb.append(" ");
            }
            sb.append(cell.result());
            i++;
        }
        return sb.toString();
    }

    public Collection<? extends Cell> cells() {
        return Arrays.asList(cells);
    }

    public static int maskDisjunction(Iterable<Cell> cells) {
        int result = 0;
        for (Cell cell : cells) {
            result |= cell.mask();
        }
        return result;
    }
}
