package org.acm.afilippov.sudoku;

import java.io.IOException;
import java.io.InputStreamReader;

class BoardLoader {
    static Sudoku readSudoku(final String filename, final Variation variation) throws IOException {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(BoardLoader.class.getResourceAsStream("/" + filename));
            return Main.readTask(reader, variation);
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
