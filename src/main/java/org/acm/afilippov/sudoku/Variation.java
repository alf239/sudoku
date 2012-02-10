package org.acm.afilippov.sudoku;

import static java.lang.Math.sqrt;

public enum Variation {
    CLASSIC(9, 1),
    SUPER4X4(16, 0);

    private int size;
    private int base;

    private int total;
    private int region;

    Variation(int size, int base) {
        this.size = size;
        this.base = base;
        total = size * size;
        region = (int) sqrt(size);
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
}
