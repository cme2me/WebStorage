package com.example.storage.repository;

import com.example.storage.model.FileModel;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface FileCustomRepository {
    List<FileModel> findByName(String name);
}
