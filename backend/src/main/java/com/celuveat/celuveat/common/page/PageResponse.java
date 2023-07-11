package com.celuveat.celuveat.common.page;

import java.util.List;

public record PageResponse<T>(
        boolean hasNextPage,
        List<T> contents
) {
}
