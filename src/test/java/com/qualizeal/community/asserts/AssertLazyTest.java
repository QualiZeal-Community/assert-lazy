package com.qualizeal.community.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AssertLazyTest {
    @Test
    void testConstructor() {
        AssertLazy actualAssertLazy = new AssertLazy();
        assertTrue(actualAssertLazy.getErrors().isEmpty());
        assertNull(actualAssertLazy.getScopedErrors());
    }

    @Test
    void testLazyAssert() throws Throwable {
        AssertLazy assertLazy = new AssertLazy();
        Executable executable = mock(Executable.class);
        doNothing().when(executable).execute();
        AssertLazy actualLazyAssertResult = assertLazy
                .lazyAssert(AssertStatement.statement("Not all who wander are lost", executable));
        assertSame(assertLazy, actualLazyAssertResult);
        List<String> expectedScopedErrors = actualLazyAssertResult.getErrors();
        assertEquals(expectedScopedErrors, actualLazyAssertResult.getScopedErrors());
        verify(executable).execute();
    }

    @Test
    void testLazyAssert2() throws Throwable {
        AssertLazy assertLazy = new AssertLazy();
        Executable executable = mock(Executable.class);
        doNothing().when(executable).execute();
        AssertStatement.statement("Not all who wander are lost", executable);
        AssertLazy actualLazyAssertResult = assertLazy.lazyAssert();
        assertSame(assertLazy, actualLazyAssertResult);
        List<String> expectedScopedErrors = actualLazyAssertResult.getErrors();
        assertEquals(expectedScopedErrors, actualLazyAssertResult.getScopedErrors());
    }

    @Test
    void testLazyAssert3() throws Throwable {
        AssertLazy assertLazy = new AssertLazy();
        Executable executable = mock(Executable.class);
        doNothing().when(executable).execute();
        AssertStatement.statement("Not all who wander are lost", executable);
        Executable executable1 = mock(Executable.class);
        doNothing().when(executable1).execute();
        AssertStatement statementResult = AssertStatement.statement("Not all who wander are lost", executable1);
        Executable executable2 = mock(Executable.class);
        doNothing().when(executable2).execute();
        AssertLazy actualLazyAssertResult = assertLazy.lazyAssert(statementResult,
                AssertStatement.statement("Not all who wander are lost", executable2));
        assertSame(assertLazy, actualLazyAssertResult);
        List<String> expectedScopedErrors = actualLazyAssertResult.getErrors();
        assertEquals(expectedScopedErrors, actualLazyAssertResult.getScopedErrors());
        verify(executable1).execute();
        verify(executable2).execute();
    }

    @Test
    void testLazyAssert4() throws Throwable {
        AssertLazy assertLazy = new AssertLazy();
        Executable executable = mock(Executable.class);
        doNothing().when(executable).execute();
        AssertStatement.statement("Not all who wander are lost", executable);
        Executable executable1 = mock(Executable.class);
        doNothing().when(executable1).execute();
        AssertStatement statementResult = AssertStatement.statement("Not all who wander are lost", executable1);
        Executable executable2 = mock(Executable.class);
        doThrow(new EagerAssertionError(new ArrayList<>())).when(executable2).execute();
        AssertLazy actualLazyAssertResult = assertLazy.lazyAssert(statementResult,
                AssertStatement.statement("Not all who wander are lost", executable2));
        assertSame(assertLazy, actualLazyAssertResult);
        List<String> errors = actualLazyAssertResult.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Not all who wander are lost []", errors.get(0));
        List<String> scopedErrors = actualLazyAssertResult.getScopedErrors();
        assertEquals(errors, scopedErrors);
        assertEquals(1, scopedErrors.size());
        assertEquals("Not all who wander are lost []", scopedErrors.get(0));
        verify(executable1).execute();
        verify(executable2).execute();
    }

    @Test
    void testOnFailure() {
        AssertLazy assertLazy = new AssertLazy();
        PostProcessor postProcessor = mock(PostProcessor.class);
        doNothing().when(postProcessor).setErrors((java.util.List<Object>) any());
        assertLazy.onFailure(postProcessor);
        verify(postProcessor).setErrors((java.util.List<Object>) any());
    }

    @Test
    void testEagerAssert() throws Throwable {
        AssertLazy assertLazy = new AssertLazy();
        Executable executable = mock(Executable.class);
        doNothing().when(executable).execute();
        assertLazy.eagerAssert(AssertStatement.statement("Not all who wander are lost", executable));
        verify(executable).execute();
        List<String> expectedScopedErrors = assertLazy.getErrors();
        assertEquals(expectedScopedErrors, assertLazy.getScopedErrors());
    }

    @Test
    void testEagerAssert2() throws Throwable {
        AssertLazy assertLazy = new AssertLazy();
        Executable executable = mock(Executable.class);
        doNothing().when(executable).execute();
        AssertStatement.statement("Not all who wander are lost", executable);
        assertLazy.eagerAssert();
        List<String> expectedScopedErrors = assertLazy.getErrors();
        assertEquals(expectedScopedErrors, assertLazy.getScopedErrors());
    }

    @Test
    void testEagerAssert3() throws Throwable {
        AssertLazy assertLazy = new AssertLazy();
        Executable executable = mock(Executable.class);
        doNothing().when(executable).execute();
        AssertStatement.statement("Not all who wander are lost", executable);
        Executable executable1 = mock(Executable.class);
        doNothing().when(executable1).execute();
        AssertStatement statementResult = AssertStatement.statement("Not all who wander are lost", executable1);
        Executable executable2 = mock(Executable.class);
        doNothing().when(executable2).execute();
        assertLazy.eagerAssert(statementResult, AssertStatement.statement("Not all who wander are lost", executable2));
        verify(executable1).execute();
        verify(executable2).execute();
        List<String> expectedScopedErrors = assertLazy.getErrors();
        assertEquals(expectedScopedErrors, assertLazy.getScopedErrors());
    }

    @Test
    void testAssertAll() {
        AssertLazy assertLazy = new AssertLazy();
        assertLazy.assertAll();
        assertTrue(assertLazy.getErrors().isEmpty());
    }
}

