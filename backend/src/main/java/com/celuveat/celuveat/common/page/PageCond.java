package com.celuveat.celuveat.common.page;

public record PageCond(
        int page,
        int size
) {

    private static final int MIN_PAGE = 1;
    private static final int MAX_SIZE = 50;
    private static final int DEFAULT_SIZE = 20;
    private static final int MIN_SIZE = 5;

    public int page() {
        return Math.max(page, MIN_PAGE);
    }

    public int size() {
        if (size < MIN_SIZE) {
            return DEFAULT_SIZE;
        }
        return Math.min(size, MAX_SIZE);
    }

    public int offset() {
        return (page() - 1) * size();
    }

    public int limit() {
        return size();
    }
}
