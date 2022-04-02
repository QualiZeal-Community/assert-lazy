package com.qualizeal.community.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class EagerAssertionErrorTest {
    @Test
    void testConstructor() {
        ArrayList<String> stringList = new ArrayList<>();
        EagerAssertionError actualEagerAssertionError = new EagerAssertionError(stringList);
        assertNull(actualEagerAssertionError.getCause());
        assertEquals("com.qualizeal.community.asserts.EagerAssertionError: []", actualEagerAssertionError.toString());
        assertEquals(0, actualEagerAssertionError.getSuppressed().length);
        assertEquals("[]", actualEagerAssertionError.getMessage());
        assertEquals("[]", actualEagerAssertionError.getLocalizedMessage());
        assertTrue(stringList.isEmpty());
    }
}

