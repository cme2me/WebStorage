package com.example.storage.exceptions;

public class FileException extends RuntimeException {
    private String message;

    public FileException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
