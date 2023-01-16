package com.example.storage.repository;

import com.example.storage.model.FileEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface FileCustomRepository {
    List<FileEntity> findByName(String name);

    List<FileEntity> findByFromDateAndToDate(LocalDateTime from, LocalDateTime to);
}
