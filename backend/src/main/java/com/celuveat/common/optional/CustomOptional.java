package com.celuveat.common.optional;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class CustomOptional<T> {

    private final T value;

    private CustomOptional(T value) {
        this.value = value;
    }

    public static <T> CustomOptional<T> empty() {
        return new CustomOptional<>(null);
    }

    public static <T> CustomOptional<T> of(T value) {
        return new CustomOptional<>(Objects.requireNonNull(value));
    }

    public <R> R mapIfPresentOrElse(Function<T, R> f, Supplier<R> s) {
        if (value != null) {
            return f.apply(value);
        }
        return s.get();
    }
}
