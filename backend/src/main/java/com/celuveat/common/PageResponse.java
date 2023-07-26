package com.celuveat.common;

import java.util.List;
import org.springframework.data.domain.Page;

public record PageResponse<T>(
        List<T> content,
        int totalPage,
        int currentPage,
        int pageSize,
        int totalElementsCount,
        int currentElementsCount
) {
    
    public static <T> PageResponse<T> from(Page<T> result) {
        return new PageResponse<>(
                result.getContent(),
                result.getTotalPages(),
                result.getNumber(),
                result.getSize(),
                (int) result.getTotalElements(),
                result.getNumberOfElements()
        );
    }
}
