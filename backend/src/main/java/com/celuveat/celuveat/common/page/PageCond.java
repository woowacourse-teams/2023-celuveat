package com.celuveat.celuveat.common.page;

public record PageCond(
        int page,
        int size
) {

    public int offset() {
        return (page() - 1) * size();
    }

    public int limit() {
        return size();
    }
}
