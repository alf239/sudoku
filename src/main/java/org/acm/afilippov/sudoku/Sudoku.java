package org.acm.afilippov.sudoku;

import static java.lang.Math.sqrt;

public class Sudoku {
    public static final int BOARD_SIZE = 9;
    public static final int BLOCK_SIZE = (int) sqrt(BOARD_SIZE);

    private int[][] task = new int[BOARD_SIZE][BOARD_SIZE];

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
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
