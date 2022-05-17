package com.example.storage.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class FileExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity uploadException(Throwable t) {
        return ResponseEntity.badRequest().body("Файл не был загружен, максимальный размер: 15МБ. " + "ERROR: " + t.getCause());
    }
}