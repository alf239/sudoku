package org.acm.afilippov.sudoku;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Main runner: the application starts here.
 * <p/>
 * Added so that {@link Sudoku} would only contain logic
 */
public class Main {
    static Sudoku readTask(Reader reader, Variation variation) throws IOException {
        int[] cells = new int[variation.getTotal()];
        for (int c, i = 0; (c = reader.read()) != -1; ) {
            if (c == '_')
                cells[i++] = variation.getMissingValue();
            else {
                int digit = Character.digit(c, Character.MAX_RADIX);
                if (digit != -1)
                    cells[i++] = digit;
            }
        }
        return new Sudoku(cells, variation);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java org.acm.afilippov.sudoku.Main <filename> <variation>");
            System.out.println();
            System.out.println("Where  <filename>  is the file to read tasks from");
            System.out.println("       <variation> is either CLASSIC or SUPER4X4");
            System.out.println();
            System.exit(-1);
        }

        FileReader reader = null;
        try {
            reader = new FileReader(args[0]);
            Sudoku sudoku = readTask(reader, Variation.valueOf(args[1].toUpperCase()));

            System.out.println("task:\n" + sudoku.result());
            System.out.println("\n");

            sudoku.solve();

            System.out.println("result:\n" + sudoku.result());
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
