package org.acm.afilippov.sudoku;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.BitSet;
import java.util.Iterator;

public abstract class Group implements Iterable<Cell> {
    protected Sudoku sudoku;
    protected int nr;

    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int i = 0;

            public boolean hasNext() {
                return i < Sudoku.BLOCK_SIZE;
            }

            public Cell next() {
                return Group.this.get(i++);
            }

            public void remove() {
                throw new NotImplementedException();
            }
        };
    }

    protected Group(Sudoku sudoku, int nr) {
        this.sudoku = sudoku;
        this.nr = nr;
    }

    protected abstract Cell get(int i);

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
            sb.append(cell).append("   ");
            if (++i % Sudoku.BLOCK_SIZE == Sudoku.BLOCK_SIZE - 1)
                sb.append("   ");
        }
        return sb.toString();
    }
}
