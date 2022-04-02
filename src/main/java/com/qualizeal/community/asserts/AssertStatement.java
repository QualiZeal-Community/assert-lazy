package com.qualizeal.community.asserts;

import lombok.Data;
import org.junit.jupiter.api.function.Executable;

@Data
public class AssertStatement {
    private String message;
    private Executable executable;

    private AssertStatement(String message, Executable executable) {
        this.message = message;
        this.executable = executable;
    }

    public static AssertStatement statement(String message, Executable executable) {
        return new AssertStatement(message, executable);
    }
}
