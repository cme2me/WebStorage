package com.example.storage.repository;

import com.example.storage.model.FileModel;

import java.util.List;

public interface CustomFileRepository {
    List<FileModel> filterByName(String name);
}
