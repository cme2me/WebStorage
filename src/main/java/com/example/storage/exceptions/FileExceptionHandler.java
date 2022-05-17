package com.example.storage.exceptions;


import com.example.storage.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class FileExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ResponseMessage> uploadException(Throwable t) {
        return ResponseEntity.badRequest().body(new ResponseMessage("Файл не был загружен, максимальный размер: 15МБ. " + t.getCause()));
    }
}