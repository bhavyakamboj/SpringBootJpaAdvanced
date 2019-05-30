package com.bhavyakamboj.springboot2.springboot2jpacrudexample.model;

import javafx.util.Pair;

import java.util.Map;

public class OperationStatus extends Pair<String, Boolean> {
    /**
     * Creates a new pair
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public OperationStatus(String key, Boolean value) {
        super(key, value);
    }
}
