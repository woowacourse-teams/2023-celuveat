package com.celuveat.celuveat.common.page;

import java.util.List;

public record SliceResponse<T>(
        boolean hasNextPage,
        List<T> contents
) {
}
