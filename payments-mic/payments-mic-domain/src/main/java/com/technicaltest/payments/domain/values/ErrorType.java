package com.technicaltest.payments.domain.values;

import java.util.stream.Stream;

import lombok.NonNull;

public enum ErrorType {
    DATABASE, NETWORK, OTHER;

    public static ErrorType of(@NonNull String type) {
        return Stream.of(values())
            .filter(pt -> pt.name().equalsIgnoreCase(type))
            .findFirst()
            .orElseThrow();
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
