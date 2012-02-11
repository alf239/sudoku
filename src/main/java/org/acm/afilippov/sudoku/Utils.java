package org.acm.afilippov.sudoku;

public class Utils {
    public static void hbar() {
        System.out.println("\n=============================\n");
    }

    static int round(int a, int r) {
        return a - a % r;
    }
}
