package com.qualizeal.community.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AssertStatementTest {
    @Test
    void testStatement() {
        assertEquals("Not all who wander are lost",
                AssertStatement.statement("Not all who wander are lost", mock(Executable.class)).getMessage());
    }
}

