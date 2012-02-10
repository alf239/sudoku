package org.acm.afilippov.sudoku;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static java.lang.Math.sqrt;

public class Sudoku {
    public static final int BOARD_SIZE = 9;
    public static final int NR_OF_CELLS = BOARD_SIZE * BOARD_SIZE;
    public static final int BLOCK_SIZE = (int) sqrt(BOARD_SIZE);

    private int[][] task = new int[BOARD_SIZE][BOARD_SIZE];

    public Sudoku(int[] cells) {
        if (cells.length != NR_OF_CELLS)
            throw new IllegalArgumentException("We expect a board of " + BOARD_SIZE + "x" + BOARD_SIZE
                    + ", which is " + NR_OF_CELLS + " cells");

        for (int i = 0, k = 0; i < task.length; i++) {
            int[] row = task[i];
            for (int j = 0; j < row.length; j++) {
                row[j] = cells[k++];
            }
        }
    }

    public boolean isValid() {
        return false;
    }

    public boolean isSolved() {
        for (int[] row : task) {
            for (int cell : row) {
                if (cell == 0)
                    return false;
            }
        }
        return isValid();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < task.length; i++) {
            int[] row = task[i];
            for (int j = 0; j < row.length; j++) {
                int cell = row[j];
                if (cell == 0)
                    sb.append('_');
                else
                    sb.append(cell);
                if (isBlockBorder(j))
                    sb.append(" ");
            }
            sb.append("\n");
            if (isBlockBorder(i))
                sb.append("\n");
        }
        return sb.toString();
    }

    private boolean isBlockBorder(int i) {
        return i != BOARD_SIZE - 1 &&
                i % BLOCK_SIZE == BLOCK_SIZE - 1;
    }

    public static Sudoku readTask(Reader reader) throws IOException {
        int[] cells = new int[NR_OF_CELLS];
        for (int c, i = 0; (c = reader.read()) != -1; ) {
            if (c == '_')
                cells[i++] = 0;
            else if (c >= '0' && c <= '9')
                cells[i++] = c - '0';
        }
        return new Sudoku(cells);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java org.acm.afilippov.sudoku.Sudoku <filename>\n\n");
            System.exit(-1);
        }

        System.out.println(readTask(new FileReader(args[0])).toString());
    }
}
