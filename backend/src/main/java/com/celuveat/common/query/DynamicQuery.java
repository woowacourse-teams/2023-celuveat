package com.celuveat.common.query;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DynamicQuery {

    private final boolean condition;
    private final String query;
    private final Object[] params;

    public static Builder builder() {
        return new Builder();
    }

    public String createQuery() {
        return query.formatted(params);
    }

    public boolean condition() {
        return condition;
    }

    public static class Builder {
        private boolean condition;
        private String query;
        private Object[] params;

        public Builder condition(boolean condition) {
            this.condition = condition;
            return this;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder params(Object... params) {
            this.params = params;
            return this;
        }

        public DynamicQuery build() {
            return new DynamicQuery(condition, query, params);
        }
    }
}

