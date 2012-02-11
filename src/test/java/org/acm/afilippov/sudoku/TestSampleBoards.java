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
        trySolving(classic("tasks.txt"));
    }

    @Test
    public void hardTask() throws IOException {
        trySolving(classic("hard.txt"));
    }

    @Test
    public void hardTask2() throws IOException {
        trySolving(classic("hard2.txt"));
    }

    @Test
    public void hardTask3() throws IOException {
        trySolving(classic("hard3.txt"));
    }

    @Test
    public void evilTask() throws IOException {
        trySolving(classic("evil.txt"));
    }

    @Test
    public void evilTask2() throws IOException {
        trySolving(classic("evil2.txt"));
    }

    @Test
    public void diabolicalTask() throws IOException {
        trySolving(classic("diabolical.txt"));
    }

    @Test
    public void super4X4() throws IOException {
        trySolving(super4x4("super4x4.txt"));
    }

    private static void trySolving(Sudoku sudoku) {
        sudoku.solve();
        assertTrue(sudoku.isSolved());
    }

    private static Sudoku classic(final String filename) throws IOException {
        return readSudoku(filename, CLASSIC);
    }

    private static Sudoku super4x4(final String filename) throws IOException {
        return readSudoku(filename, SUPER4X4);
    }
}
