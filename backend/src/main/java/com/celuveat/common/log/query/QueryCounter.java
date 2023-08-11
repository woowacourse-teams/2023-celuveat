package com.celuveat.common.log.query;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class QueryCounter {

    private int count;

    public void increase() {
        count++;
    }

    public int count() {
        return count;
    }
}
