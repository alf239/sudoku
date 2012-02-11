package org.acm.afilippov.sudoku;

import static java.lang.Math.sqrt;

public enum Variation {
    CLASSIC(9, 1),
    SUPER4X4(16, 0);

    private final int size;
    private final int base;

    private final int total;
    private final int region;

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
