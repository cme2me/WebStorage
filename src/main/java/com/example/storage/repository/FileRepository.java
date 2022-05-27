package com.example.storage.repository;

import com.example.storage.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileModel, String>, CustomFileRepository {
    List<FileModel> findByDateBefore(LocalDateTime dateTime);
}
