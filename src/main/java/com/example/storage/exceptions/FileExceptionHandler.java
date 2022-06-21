package com.example.storage.exceptions;

import com.example.storage.dto.ResponseMessage;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class FileExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ResponseMessage> uploadException(Throwable t) {
        return ResponseEntity.badRequest().body(new ResponseMessage("Файл не был загружен, " + t.getMessage()));
    }

    //todo Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> Exception(Throwable t) {
        return ResponseEntity.internalServerError().body(new ResponseMessage("Server | ERROR: " + t.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ResponseMessage> missingUploadPartsException(Throwable t) {
        return ResponseEntity.badRequest().body(new ResponseMessage(t.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseMessage> noSuchIdException() {
        return ResponseEntity.badRequest().body(new ResponseMessage("File id doesn't exist!"));
    }

}