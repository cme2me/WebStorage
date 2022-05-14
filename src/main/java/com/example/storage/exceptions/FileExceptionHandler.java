package com.example.storage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class FileExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //сделать ответ в JSON
    public ResponseEntity uploadException(Throwable t) {
        return ResponseEntity.badRequest().body("Файл не был загружен" + t.getCause());
    }
}
