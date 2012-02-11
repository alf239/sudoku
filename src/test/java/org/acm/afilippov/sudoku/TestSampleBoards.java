package org.acm.afilippov.sudoku;

import org.junit.Test;

import java.io.IOException;

import static org.acm.afilippov.sudoku.BoardLoader.readSudoku;
import static org.acm.afilippov.sudoku.Variation.CLASSIC;
import static org.acm.afilippov.sudoku.Variation.SUPER4X4;
import static org.junit.Assert.assertTrue;

public class TestSampleBoards {
    @Test
    public void sampleTaskBySearchSpring() throws IOException {
        Sudoku sudoku = classic("tasks.txt");
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void hardTask() throws IOException {
        Sudoku sudoku = classic("hard.txt");
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void hardTask2() throws IOException {
        Sudoku sudoku = classic("hard2.txt");
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void hardTask3() throws IOException {
        Sudoku sudoku = classic("hard3.txt");
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void evilTask() throws IOException {
        Sudoku sudoku = classic("evil.txt");
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void evilTask2() throws IOException {
        Sudoku sudoku = classic("evil2.txt");
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void diabolicalTask() throws IOException {
        Sudoku sudoku = classic("diabolical.txt");
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    @Test
    public void super4X4() throws IOException {
        Sudoku sudoku = super4x4("super4x4.txt");
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    private Sudoku classic(final String filename) throws IOException {
        return readSudoku(filename, CLASSIC);
    }

    private Sudoku super4x4(final String filename) throws IOException {
        return readSudoku(filename, SUPER4X4);
    }
}
