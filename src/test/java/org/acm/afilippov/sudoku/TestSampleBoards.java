package org.acm.afilippov.sudoku;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.acm.afilippov.sudoku.Variation.CLASSIC;
import static org.acm.afilippov.sudoku.Variation.SUPER4X4;
import static org.junit.Assert.assertTrue;

public class TestSampleBoards {
    @Test
    public void sampleTaskBySearchSpring() throws IOException {
        Sudoku sudoku = readSudoku("tasks.txt", CLASSIC);
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void hardTask() throws IOException {
        Sudoku sudoku = readSudoku("hard.txt", CLASSIC);
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void hardTask2() throws IOException {
        Sudoku sudoku = readSudoku("hard2.txt", CLASSIC);
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void hardTask3() throws IOException {
        Sudoku sudoku = readSudoku("hard3.txt", CLASSIC);
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void evilTask() throws IOException {
        Sudoku sudoku = readSudoku("evil.txt", CLASSIC);
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void evilTask2() throws IOException {
        Sudoku sudoku = readSudoku("evil2.txt", CLASSIC);
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void super4X4() throws IOException {
        Sudoku sudoku = readSudoku("super4x4.txt", SUPER4X4);
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    private Sudoku readSudoku(final String filename, final Variation variation) throws IOException {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(getClass().getResourceAsStream("/" + filename));
            return Main.readTask(reader, variation);
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
