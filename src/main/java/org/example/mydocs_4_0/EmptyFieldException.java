package org.example.mydocs_4_0;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException() {
        super("Field is empty");
    }
}

