package org.acm.afilippov.sudoku;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class Group implements Iterable<Cell> {
    private List<Cell> cells = new ArrayList<Cell>(Sudoku.BLOCK_SIZE);

    public Iterator<Cell> iterator() {
        return cells.iterator();
    }

    public void add(Cell cell) {
        cells.add(cell);
        cell.join(this);
    }

    public boolean isValid() {
        BitSet mask = Cell.any().mask();
        for (Cell cell : this) {
            mask.or(cell.mask());
        }
        return mask.cardinality() == Sudoku.BOARD_SIZE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Cell cell : this) {
            if (i++ % Sudoku.BLOCK_SIZE == 0)
                sb.append("   ");
            sb.append(cell).append("   ");
        }
        return sb.toString();
    }
}
