package org.acm.afilippov.sudoku;

import org.junit.Test;

import java.io.IOException;

import static org.acm.afilippov.sudoku.BoardLoader.readSudoku;
import static org.acm.afilippov.sudoku.Variation.CLASSIC;
import static org.junit.Assert.assertEquals;

public class TestPrinting {
    @Test
    public void basicToString() throws IOException {
        Sudoku sudoku = readSudoku("tasks.txt", CLASSIC);

        assertEquals("Invalid starting state",
                "" +
                        "........9   123456789   123456789      123456789   123456789   ..3......      123456789   .....6...   123456789\n" +
                        "123456789   .....6...   123456789      123456789   123456789   123456789      123456789   1........   .2.......\n" +
                        ".2.......   123456789   ....5....      1........   .......8.   123456789      123456789   ..3......   123456789\n" +
                        "\n" +
                        ".....6...   123456789   123456789      ....5....   123456789   123456789      123456789   .2.......   123456789\n" +
                        "1........   123456789   ........9      .....6...   123456789   .......8.      ..3......   123456789   ......7..\n" +
                        "123456789   ....5....   123456789      123456789   123456789   .2.......      123456789   123456789   ........9\n" +
                        "\n" +
                        "123456789   ..3......   123456789      123456789   .....6...   ...4.....      1........   123456789   .......8.\n" +
                        "......7..   1........   123456789      123456789   123456789   123456789      123456789   ........9   123456789\n" +
                        "123456789   ........9   123456789      .2.......   123456789   123456789      123456789   123456789   ..3......",
                sudoku.toString());
        sudoku.solve();

        assertEquals("Invalid result",
                "" +
                        "........9   ...4.....   1........      ......7..   .2.......   ..3......      .......8.   .....6...   ....5....\n" +
                        "..3......   .....6...   .......8.      ...4.....   ....5....   ........9      ......7..   1........   .2.......\n" +
                        ".2.......   ......7..   ....5....      1........   .......8.   .....6...      ........9   ..3......   ...4.....\n" +
                        "\n" +
                        ".....6...   .......8.   ..3......      ....5....   ........9   ......7..      ...4.....   .2.......   1........\n" +
                        "1........   .2.......   ........9      .....6...   ...4.....   .......8.      ..3......   ....5....   ......7..\n" +
                        "...4.....   ....5....   ......7..      ..3......   1........   .2.......      .....6...   .......8.   ........9\n" +
                        "\n" +
                        "....5....   ..3......   .2.......      ........9   .....6...   ...4.....      1........   ......7..   .......8.\n" +
                        "......7..   1........   ...4.....      .......8.   ..3......   ....5....      .2.......   ........9   .....6...\n" +
                        ".......8.   ........9   .....6...      .2.......   ......7..   1........      ....5....   ...4.....   ..3......",
                sudoku.toString());
    }

    @Test
    public void prettyResults() throws IOException {
        Sudoku sudoku = readSudoku("tasks.txt", CLASSIC);

        assertEquals("Invalid starting state",
                "" +
                        "9 _ _  _ _ 3  _ 6 _\n" +
                        "_ 6 _  _ _ _  _ 1 2\n" +
                        "2 _ 5  1 8 _  _ 3 _\n" +
                        "\n" +
                        "6 _ _  5 _ _  _ 2 _\n" +
                        "1 _ 9  6 _ 8  3 _ 7\n" +
                        "_ 5 _  _ _ 2  _ _ 9\n" +
                        "\n" +
                        "_ 3 _  _ 6 4  1 _ 8\n" +
                        "7 1 _  _ _ _  _ 9 _\n" +
                        "_ 9 _  2 _ _  _ _ 3",
                sudoku.result());

        sudoku.solve();

        assertEquals("Invalid result",
                "" +
                        "9 4 1  7 2 3  8 6 5\n" +
                        "3 6 8  4 5 9  7 1 2\n" +
                        "2 7 5  1 8 6  9 3 4\n" +
                        "\n" +
                        "6 8 3  5 9 7  4 2 1\n" +
                        "1 2 9  6 4 8  3 5 7\n" +
                        "4 5 7  3 1 2  6 8 9\n" +
                        "\n" +
                        "5 3 2  9 6 4  1 7 8\n" +
                        "7 1 4  8 3 5  2 9 6\n" +
                        "8 9 6  2 7 1  5 4 3",
                sudoku.result());
    }
}
