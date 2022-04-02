package com.qualizeal.community.asserts;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssertLazy {
    @Getter
    private List<String> errors;
    @Getter
    private List<String> scopedErrors;

    public AssertLazy() {
        this.errors = new ArrayList<>();
    }

    public AssertLazy lazyAssert(AssertStatement... assertStatements) {
        scopedErrors = new ArrayList<>();
        Arrays.stream(assertStatements).forEach(e -> {
            try {
                e.getExecutable().execute();
            } catch (Throwable t) {
                String message = e.getMessage() + " " + t.getMessage().replace("\n", "");
                scopedErrors.add(message);
                errors.add(message);
            }
        });
        return this;
    }

    public PostProcessor onFailure(PostProcessor operation) {
        operation.setErrors(scopedErrors);
        return operation;
    }

    public void eagerAssert(AssertStatement... assertStatements) {
        scopedErrors = new ArrayList<>();
        Arrays.stream(assertStatements).forEach(e -> {
            try {
                e.getExecutable().execute();
            } catch (Throwable t) {
                scopedErrors.add(e.getMessage() + t.getMessage().replace("\n", ""));
            }
        });
        if(!scopedErrors.isEmpty()) {
            throw new EagerAssertionError(scopedErrors);
        }
    }

    public void assertAll() {
        if (!errors.isEmpty())
            throw new AssertionError(errors.toString());
    }
}
