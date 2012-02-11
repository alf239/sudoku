package org.acm.afilippov.sudoku;

import static java.lang.Math.sqrt;

public enum Variation {
    CLASSIC(9, 1),
    SUPER4X4(16, 0);

    private int size;
    private int base;

    private int total;
    private int region;

    private final int maskForAny;

    Variation(int size, int base) {
        this.size = size;
        this.base = base;
        total = size * size;
        region = (int) sqrt(size);
        maskForAny = -1 >>> (32 - size);
    }

    int maskFor(int value) {
        if (value == getMissingValue())
            return maskForAny;
        else
            return 1 << (value - getBase());
    }

    public int getSize() {
        return size;
    }

    public int getBase() {
        return base;
    }

    public int getTotal() {
        return total;
    }

    public int getRegionSize() {
        return region;
    }

    public int getMissingValue() {
        return -1;
    }
}
