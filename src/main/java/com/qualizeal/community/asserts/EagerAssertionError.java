package com.qualizeal.community.asserts;

import java.util.List;

public class EagerAssertionError extends AssertionError {
    public EagerAssertionError(List<String> message) {
        super(message);
    }
}
