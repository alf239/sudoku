package org.acm.afilippov.sudoku;

import org.junit.Test;

import java.io.IOException;

import static org.acm.afilippov.sudoku.BoardLoader.readSudoku;
import static org.acm.afilippov.sudoku.Variation.CLASSIC;
import static org.acm.afilippov.sudoku.Variation.SUPER4X4;
import static org.junit.Assert.assertTrue;

public class TestSampleBoards {
    public static final String[] CLASSIC_TASKS = {
            "tasks.txt",
            "hard.txt",
            "hard2.txt",
            "hard3.txt",
            "evil.txt",
            "evil2.txt",
            "diabolical.txt"
    };

    @Test
    public void classicTasks() throws IOException {
        for (String task : CLASSIC_TASKS)
            trySolving(task, readSudoku(task, CLASSIC));
    }

    @Test
    public void super4X4() throws IOException {
        trySolving("super4x4.txt", readSudoku("super4x4.txt", SUPER4X4));
    }

    private static void trySolving(String name, Sudoku sudoku) {
        sudoku.solve();
        assertTrue("Cannot solve " + name, sudoku.isSolved());
    }
}
