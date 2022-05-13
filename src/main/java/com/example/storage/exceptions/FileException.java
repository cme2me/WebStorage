package com.example.storage.exceptions;

public class FileException extends RuntimeException {
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(String message) {
        super(message);
    }
}
