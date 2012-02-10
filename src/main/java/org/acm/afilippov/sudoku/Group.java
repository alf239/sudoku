package org.acm.afilippov.sudoku;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.BitSet;
import java.util.Iterator;

public abstract class Group implements Iterable<Cell> {
    protected Sudoku sudoku;

    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int i = 0;

            public boolean hasNext() {
                return i < Sudoku.BOARD_SIZE;
            }

            public Cell next() {
                return Group.this.get(i++);
            }

            public void remove() {
                throw new NotImplementedException();
            }
        };
    }

    protected Group(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    protected void init() {
        for (Cell cell : this) {
            cell.join(this);
        }
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
            if (i++ % Sudoku.BLOCK_SIZE == 0)
                sb.append("   ");
            sb.append(cell).append("   ");
        }
        return sb.toString();
    }
}
